package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HistoryLogEntryServiceSpec extends Specification {

    HistoryLogEntryService historyLogEntryService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HistoryLogEntry(...).save(flush: true, failOnError: true)
        //new HistoryLogEntry(...).save(flush: true, failOnError: true)
        //HistoryLogEntry historyLogEntry = new HistoryLogEntry(...).save(flush: true, failOnError: true)
        //new HistoryLogEntry(...).save(flush: true, failOnError: true)
        //new HistoryLogEntry(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //historyLogEntry.id
    }

    void "test get"() {
        setupData()

        expect:
        historyLogEntryService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HistoryLogEntry> historyLogEntryList = historyLogEntryService.list(max: 2, offset: 2)

        then:
        historyLogEntryList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        historyLogEntryService.count() == 5
    }

    void "test delete"() {
        Long historyLogEntryId = setupData()

        expect:
        historyLogEntryService.count() == 5

        when:
        historyLogEntryService.delete(historyLogEntryId)
        sessionFactory.currentSession.flush()

        then:
        historyLogEntryService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HistoryLogEntry historyLogEntry = new HistoryLogEntry()
        historyLogEntryService.save(historyLogEntry)

        then:
        historyLogEntry.id != null
    }
}
