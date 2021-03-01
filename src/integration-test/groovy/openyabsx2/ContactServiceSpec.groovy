package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ContactServiceSpec extends Specification {

    ContactService contactService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Contact(...).save(flush: true, failOnError: true)
        //new Contact(...).save(flush: true, failOnError: true)
        //Contact contact = new Contact(...).save(flush: true, failOnError: true)
        //new Contact(...).save(flush: true, failOnError: true)
        //new Contact(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //contact.id
    }

    void "test get"() {
        setupData()

        expect:
        contactService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Contact> contactList = contactService.list(max: 2, offset: 2)

        then:
        contactList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        contactService.count() == 5
    }

    void "test delete"() {
        Long contactId = setupData()

        expect:
        contactService.count() == 5

        when:
        contactService.delete(contactId)
        sessionFactory.currentSession.flush()

        then:
        contactService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Contact contact = new Contact()
        contactService.save(contact)

        then:
        contact.id != null
    }
}
