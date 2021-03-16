package openyabsx2

import grails.gorm.services.Service

@Service(Receipt)
interface ReceiptService {

    Receipt get(Serializable id)

    List<Receipt> list(Map args)

    Long count()

    void delete(Serializable id)

    Receipt save(Receipt receipt)
}