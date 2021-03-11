package openyabsx2

import grails.gorm.transactions.Transactional
import groovy.sql.Sql
import org.springframework.context.annotation.DeferredImportSelector

import javax.swing.GroupLayout

class BootStrap {
    //https://github.com/monetschemist/grails-datatables
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
        if (!new File(derbyPath as String).exists()) {
            log.warn("Path ${derbyPath} does not exist")
            return
        }

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
                "BANKACCOUNT, BANKID, BANKNAME, BANKCURRENCY, BANKCOUNTRY, PAYTERM from contacts a order by IDS", {
            it.eachRow { row ->

                new Contact(
                        importid: row['IDS'] as String,
                        name: row["CNAME"] as String,
                        cnumber: row["CNUMBER"] as String,
                        taxnumber: row["TAXNUMBER"] as String,
                        title: row["TITLE"] as String,
                        group: Group.findByImportid(row["GROUPSIDS"] as String)?: Group.findByName(Group.ROOT),
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
        new Contact(cnumber: "missing", name: "missing", group: Group.findByName(Group.ROOT)).save()
        Contact.withSession {
            it.flush()
        }

        sql.eachRow("select a.IDS," +
                " CNAME, CNUMBER, DESCRIPTION, GROUPSIDS, ACCOUNTSIDS, CONTACTSIDS, NETVALUE, TAXVALUE, " +
                "DISCOUNTVALUE, SHIPPINGVALUE, DATETODO, DATEEND, INTREMINDERS, INTTYPE, DATEADDED, INTADDEDBY, " +
                "INVISIBLE, INTSTATUS, HIERARCHYPATH, RESERVE1, RESERVE2, DISCOUNTGROSVALUE, REFORDERIDS" +
                " from items a where ids<11", { row ->

            new Receipt(cnumber: row['CNUMBER'] as String,
                    receiptStatus: ReceiptStatus.findByName(getStatusString(row['INTSTATUS'] as int)),
                    receiptType: ReceiptType.findByName(getTypeString(row['INTTYPE'] as int)),
                    group: Group.findByImportid(row["GROUPSIDS"] as String)?: Group.findByName(Group.ROOT),
                    description: row["DESCRIPTION"] as String,
                    contact: Contact.findByImportid(row["CONTACTSIDS"] as String)?:Contact.findByName("missing"),
                    netValue: row['NETVALUE'] as BigDecimal,
                    taxValue: row['TAXVALUE'] as BigDecimal,
                    discountValue: row['DISCOUNTVALUE'] as BigDecimal,
                    shippingValue: row['SHIPPINGVALUE'] as BigDecimal,
                    todoDate: row['DATETODO'] as Date,
                    endDate: row['DATEEND'] as Date,
                    reminders: row['INTREMINDERS'] as int
            ).save(failOnError: false)
        })
        Receipt.withSession {
            it.flush()
        }

    }

    public static final int TYPE_INVOICE = 0;
    public static final int TYPE_ORDER = 1;
    public static final int TYPE_OFFER = 2;
    public static final int TYPE_DELIVERY_NOTE = 3;
    public static final int TYPE_ORDER_CONFIRMATION = 4;
    public static final int STATUS_QUEUED = 0;
    public static final int STATUS_IN_PROGRESS = 1;
    public static final int STATUS_PAUSED = 2;
    public static final int STATUS_FINISHED = 3;
    public static final int STATUS_PAID = 4;
    public static final int STATUS_CANCELLED = 5;


    static String getStatusString(int status) {
        switch (status) {
            case (STATUS_QUEUED):
                return "Queued".toString();
            case (STATUS_IN_PROGRESS):
                return "In Progress".toString();
            case (STATUS_PAUSED):
                return "Paused".toString();
            case (STATUS_FINISHED):
                return "Finished".toString();
            case (STATUS_PAID):
                return "Paid".toString();
            case (STATUS_CANCELLED):
                return "Cancelled".toString();
        }
        return "NA".toString();
    }

    static String getTypeString(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case (TYPE_INVOICE):
                return "Invoice".toString();
            case (TYPE_OFFER):
                return "Offer".toString();
            case (TYPE_ORDER):
                return "Order".toString();
            case (TYPE_ORDER_CONFIRMATION):
                return "Order Confirmation".toString();
            case (TYPE_DELIVERY_NOTE):
                return "Delivery Note".toString();
        }
        return "";
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
        new ReceiptType(name: "Order Confirmation").save(flush: true)

        new ReceiptStatus(name: "Queued").save(flush: true)
        new ReceiptStatus(name: "In Progress").save(flush: true)
        new ReceiptStatus(name: "Paused").save(flush: true)
        new ReceiptStatus(name: "Finished").save(flush: true)
        new ReceiptStatus(name: "Paid").save(flush: true)
        new ReceiptStatus(name: "Cancelled").save(flush: true)
    }
}
