package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReceiptServiceSpec extends Specification {

    ReceiptService receiptService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Receipt(...).save(flush: true, failOnError: true)
        //new Receipt(...).save(flush: true, failOnError: true)
        //Receipt receipt = new Receipt(...).save(flush: true, failOnError: true)
        //new Receipt(...).save(flush: true, failOnError: true)
        //new Receipt(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //receipt.id
    }

    void "test get"() {
        setupData()

        expect:
        receiptService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Receipt> receiptList = receiptService.list(max: 2, offset: 2)

        then:
        receiptList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        receiptService.count() == 5
    }

    void "test delete"() {
        Long receiptId = setupData()

        expect:
        receiptService.count() == 5

        when:
        receiptService.delete(receiptId)
        sessionFactory.currentSession.flush()

        then:
        receiptService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Receipt receipt = new Receipt()
        receiptService.save(receipt)

        then:
        receipt.id != null
    }
}
