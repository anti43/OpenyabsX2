package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ConfigEntryServiceSpec extends Specification {

    ConfigEntryService configEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ConfigEntry(...).save(flush: true, failOnError: true)
        //new ConfigEntry(...).save(flush: true, failOnError: true)
        //ConfigEntry configEntry = new ConfigEntry(...).save(flush: true, failOnError: true)
        //new ConfigEntry(...).save(flush: true, failOnError: true)
        //new ConfigEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //configEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        configEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ConfigEntry> configEntryList = configEntryService.list(max: 2, offset: 2)

        then:
        configEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        configEntryService.count() == 5
    }

    void "test delete"() {
        Long configEntryId = setupData()

        expect:
        configEntryService.count() == 5

        when:
        configEntryService.delete(configEntryId)
        sessionFactory.currentSession.flush()

        then:
        configEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ConfigEntry configEntry = new ConfigEntry()
        configEntryService.save(configEntry)

        then:
        configEntry.id != null
    }
}
