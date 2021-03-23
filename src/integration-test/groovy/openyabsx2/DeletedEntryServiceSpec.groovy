package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DeletedEntryServiceSpec extends Specification {

    DeletedEntryService deletedEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DeletedEntry(...).save(flush: true, failOnError: true)
        //new DeletedEntry(...).save(flush: true, failOnError: true)
        //DeletedEntry deletedEntry = new DeletedEntry(...).save(flush: true, failOnError: true)
        //new DeletedEntry(...).save(flush: true, failOnError: true)
        //new DeletedEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //deletedEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        deletedEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DeletedEntry> deletedEntryList = deletedEntryService.list(max: 2, offset: 2)

        then:
        deletedEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        deletedEntryService.count() == 5
    }

    void "test delete"() {
        Long deletedEntryId = setupData()

        expect:
        deletedEntryService.count() == 5

        when:
        deletedEntryService.delete(deletedEntryId)
        sessionFactory.currentSession.flush()

        then:
        deletedEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DeletedEntry deletedEntry = new DeletedEntry()
        deletedEntryService.save(deletedEntry)

        then:
        deletedEntry.id != null
    }
}
