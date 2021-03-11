package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReceiptItemServiceSpec extends Specification {

    ReceiptItemService receiptItemService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReceiptItem(...).save(flush: true, failOnError: true)
        //new ReceiptItem(...).save(flush: true, failOnError: true)
        //ReceiptItem receiptItem = new ReceiptItem(...).save(flush: true, failOnError: true)
        //new ReceiptItem(...).save(flush: true, failOnError: true)
        //new ReceiptItem(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //receiptItem.id
    }

    void "test get"() {
        setupData()

        expect:
        receiptItemService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReceiptItem> receiptItemList = receiptItemService.list(max: 2, offset: 2)

        then:
        receiptItemList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        receiptItemService.count() == 5
    }

    void "test delete"() {
        Long receiptItemId = setupData()

        expect:
        receiptItemService.count() == 5

        when:
        receiptItemService.delete(receiptItemId)
        sessionFactory.currentSession.flush()

        then:
        receiptItemService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReceiptItem receiptItem = new ReceiptItem()
        receiptItemService.save(receiptItem)

        then:
        receiptItem.id != null
    }
}
