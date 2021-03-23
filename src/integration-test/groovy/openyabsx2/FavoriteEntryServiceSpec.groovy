package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class FavoriteEntryServiceSpec extends Specification {

    FavoriteEntryService favoriteEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new FavoriteEntry(...).save(flush: true, failOnError: true)
        //new FavoriteEntry(...).save(flush: true, failOnError: true)
        //FavoriteEntry favoriteEntry = new FavoriteEntry(...).save(flush: true, failOnError: true)
        //new FavoriteEntry(...).save(flush: true, failOnError: true)
        //new FavoriteEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //favoriteEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        favoriteEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<FavoriteEntry> favoriteEntryList = favoriteEntryService.list(max: 2, offset: 2)

        then:
        favoriteEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        favoriteEntryService.count() == 5
    }

    void "test delete"() {
        Long favoriteEntryId = setupData()

        expect:
        favoriteEntryService.count() == 5

        when:
        favoriteEntryService.delete(favoriteEntryId)
        sessionFactory.currentSession.flush()

        then:
        favoriteEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        FavoriteEntry favoriteEntry = new FavoriteEntry()
        favoriteEntryService.save(favoriteEntry)

        then:
        favoriteEntry.id != null
    }
}
