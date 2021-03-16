package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SearchentryServiceSpec extends Specification {

    SearchEntryService searchentryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Searchentry(...).save(flush: true, failOnError: true)
        //new Searchentry(...).save(flush: true, failOnError: true)
        //Searchentry searchentry = new Searchentry(...).save(flush: true, failOnError: true)
        //new Searchentry(...).save(flush: true, failOnError: true)
        //new Searchentry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //searchentry.id
    }

    void "test get"() {
        setupData()

        expect:
        searchentryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SearchEntry> searchentryList = searchentryService.list(max: 2, offset: 2)

        then:
        searchentryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        searchentryService.count() == 5
    }

    void "test delete"() {
        Long searchentryId = setupData()

        expect:
        searchentryService.count() == 5

        when:
        searchentryService.delete(searchentryId)
        sessionFactory.currentSession.flush()

        then:
        searchentryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SearchEntry searchentry = new SearchEntry()
        searchentryService.save(searchentry)

        then:
        searchentry.id != null
    }
}
