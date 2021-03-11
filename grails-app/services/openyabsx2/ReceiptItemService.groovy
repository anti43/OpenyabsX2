package openyabsx2

import grails.gorm.services.Service

@Service(ReceiptItem)
interface ReceiptItemService {

    ReceiptItem get(Serializable id)

    List<ReceiptItem> list(Map args)

    Long count()

    void delete(Serializable id)

    ReceiptItem save(ReceiptItem receiptItem)

}