package openyabsx2

import grails.gorm.transactions.Transactional
import groovy.sql.Sql
import org.springframework.context.annotation.DeferredImportSelector

import javax.swing.GroupLayout

class BootStrap {
    def grailsApplication

    def init = { servletContext ->

        createRootGroup()
        createReceiptTypes()
        addTestUser()
        importLegacy()
    }
    def destroy = {
    }

    @Transactional
    void addTestUser() {
        def adminRole = new Role(authority: 'ROLE_ADMIN').save()

        def testUser = new User(username: 'admin', password: 'password').save()

        UserRole.create testUser, adminRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        assert User.count() == 1
        assert Role.count() == 1
        assert UserRole.count() == 1
        assert Group.count() == 1
    }


    @Transactional
    void importLegacy() {

        def derbyPath = grailsApplication.config.getProperty('yabs.legacy.derby.path')
        if(!new File(derbyPath as String).exists())return

        def sql = Sql.newInstance("jdbc:derby:$derbyPath", "org.apache.derby.jdbc.ClientDriver")
        sql.eachRow("select IDS, CNAME, DESCRIPTION, DEFAULTS, GROUPSIDS, DATEADDED, RESERVE1, INTADDEDBY, HIERARCHYPATH, RESERVE2 from groups a", {
            it.eachRow { row ->
                new Group(name: row["CNAME"], importid: row["IDS"]).save()
            }
            Group.withSession {
                it.flush()
            }
        })
        sql.eachRow("select IDS, CNAME, GROUPSIDS from groups a", {
            it.eachRow { row ->
                def g = Group.findByName(row["CNAME"] as String)
                g.setParent(Group.findByImportid(row["GROUPSIDS"] as String) ?: Group.findByName(Group.ROOT))
                g.save()
            }
            Group.withSession {
                it.flush()
            }
        })
        sql.eachRow("select a.IDS, CNUMBER, TAXNUMBER, TITLE, GROUPSIDS, COUNTRY, PRENAME, CNAME, STREET, ZIP, CITY, MAINPHONE, FAX, MOBILEPHONE, WORKPHONE, MAILADDRESS, " +
                "COMPANY, DEPARTMENT, WEBSITE, NOTES, DATEADDED, ISACTIVE, ISCUSTOMER, ISMANUFACTURER, ISSUPPLIER, ISCOMPANY, ISMALE, ISENABLED, INTADDEDBY, INVISIBLE, RESERVE1, RESERVE2, " +
                "BANKACCOUNT, BANKID, BANKNAME, BANKCURRENCY, BANKCOUNTRY, PAYTERM from contacts a", {
            it.eachRow { row ->

                new Contact(name: row["CNAME"] as String,
                        cnumber: row["CNUMBER"] as String,
                        taxnumber: row["TAXNUMBER"] as String,
                        title: row["TITLE"] as String,
                        group: Group.findByImportid(row["GROUPSIDS"] as String),
                        prename: row["PRENAME"] as String,
                        street: row["STREET"] as String,
                        zip: row["ZIP"] as String,
                        city: row["CITY"] as String,
                        mainphone: row["MAINPHONE"] as String,
                        fax: row["FAX"] as String,
                        mobilephone: row["MOBILEPHONE"] as String,
                        workphone: row["WORKPHONE"] as String,
                        mailaddress: row["MAILADDRESS"] as String,
                        company: row["COMPANY"] as String,
                        department: row["DEPARTMENT"] as String,
                        website: row["WEBSITE"] as String,
                        notes: row["NOTES"] as String,
                        bankAccount: row["BANKACCOUNT"] as String,
                        bankid: row["BANKID"] as String,
                        bankName: row["BANKNAME"] as String,
                        bankCurrency: row["BANKCURRENCY"] as String,
                        bankCountry: row["BANKCOUNTRY"] as String,
                        country: row["COUNTRY"] as String,

                        isEnabled: row["ISACTIVE"] as Boolean,
                        isCustomer: row["ISCUSTOMER"] as Boolean,
                        isManufacturer: row["ISMANUFACTURER"] as Boolean,
                        isSupplier: row["ISSUPPLIER"] as Boolean
                ).save()
            }
        })
        Contact.withSession {
            it.flush()
        }
        println Contact.all*.name as String
        println Group.all*.name as String

    }

    @Transactional
    void createRootGroup() {
        new Group(name: Group.ROOT).save(flush: true)
    }

    @Transactional
    void createReceiptTypes() {
        new ReceiptType(name: "Invoice").save(flush: true)
        new ReceiptType(name: "Order").save(flush: true)
        new ReceiptType(name: "Offer").save(flush: true)
        new ReceiptType(name: "Delivery Note").save(flush: true)
    }
}
