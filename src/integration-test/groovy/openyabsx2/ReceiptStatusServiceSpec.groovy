package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReceiptStatusServiceSpec extends Specification {

    ReceiptStatusService receiptStatusService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReceiptStatus(...).save(flush: true, failOnError: true)
        //new ReceiptStatus(...).save(flush: true, failOnError: true)
        //ReceiptStatus receiptStatus = new ReceiptStatus(...).save(flush: true, failOnError: true)
        //new ReceiptStatus(...).save(flush: true, failOnError: true)
        //new ReceiptStatus(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //receiptStatus.id
    }

    void "test get"() {
        setupData()

        expect:
        receiptStatusService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReceiptStatus> receiptStatusList = receiptStatusService.list(max: 2, offset: 2)

        then:
        receiptStatusList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        receiptStatusService.count() == 5
    }

    void "test delete"() {
        Long receiptStatusId = setupData()

        expect:
        receiptStatusService.count() == 5

        when:
        receiptStatusService.delete(receiptStatusId)
        sessionFactory.currentSession.flush()

        then:
        receiptStatusService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReceiptStatus receiptStatus = new ReceiptStatus()
        receiptStatusService.save(receiptStatus)

        then:
        receiptStatus.id != null
    }
}
