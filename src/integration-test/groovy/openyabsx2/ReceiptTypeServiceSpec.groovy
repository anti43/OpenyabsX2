package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReceiptTypeServiceSpec extends Specification {

    ReceiptTypeService receiptTypeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReceiptType(...).save(flush: true, failOnError: true)
        //new ReceiptType(...).save(flush: true, failOnError: true)
        //ReceiptType receiptType = new ReceiptType(...).save(flush: true, failOnError: true)
        //new ReceiptType(...).save(flush: true, failOnError: true)
        //new ReceiptType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //receiptType.id
    }

    void "test get"() {
        setupData()

        expect:
        receiptTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReceiptType> receiptTypeList = receiptTypeService.list(max: 2, offset: 2)

        then:
        receiptTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        receiptTypeService.count() == 5
    }

    void "test delete"() {
        Long receiptTypeId = setupData()

        expect:
        receiptTypeService.count() == 5

        when:
        receiptTypeService.delete(receiptTypeId)
        sessionFactory.currentSession.flush()

        then:
        receiptTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReceiptType receiptType = new ReceiptType()
        receiptTypeService.save(receiptType)

        then:
        receiptType.id != null
    }
}
