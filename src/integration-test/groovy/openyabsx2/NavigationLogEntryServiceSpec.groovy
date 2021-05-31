package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class NavigationLogEntryServiceSpec extends Specification {

    NavigationLogEntryService navigationLogEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new NavigationLogEntry(...).save(flush: true, failOnError: true)
        //new NavigationLogEntry(...).save(flush: true, failOnError: true)
        //NavigationLogEntry navigationLogEntry = new NavigationLogEntry(...).save(flush: true, failOnError: true)
        //new NavigationLogEntry(...).save(flush: true, failOnError: true)
        //new NavigationLogEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //navigationLogEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        navigationLogEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<NavigationLogEntry> navigationLogEntryList = navigationLogEntryService.list(max: 2, offset: 2)

        then:
        navigationLogEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        navigationLogEntryService.count() == 5
    }

    void "test delete"() {
        Long navigationLogEntryId = setupData()

        expect:
        navigationLogEntryService.count() == 5

        when:
        navigationLogEntryService.delete(navigationLogEntryId)
        sessionFactory.currentSession.flush()

        then:
        navigationLogEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        NavigationLogEntry navigationLogEntry = new NavigationLogEntry()
        navigationLogEntryService.save(navigationLogEntry)

        then:
        navigationLogEntry.id != null
    }
}
