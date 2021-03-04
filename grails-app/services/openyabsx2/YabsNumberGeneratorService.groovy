package openyabsx2

import grails.gorm.transactions.Transactional

@Transactional
class YabsNumberGeneratorService {

    def nextVal(String forClass) {
        return "12345678"
    }
}
