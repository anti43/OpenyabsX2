package openyabsx2

import grails.gorm.transactions.Transactional
import org.grails.datastore.gorm.GormEntity

/**
 * Based on https://dzone.com/articles/using-datatablesnet-grails
 * */
class DataService {

    /*
    headerList = [
                [name: "ID", messageBundleKey: "id", returnValuePropertyOrCode: "id", sortPropertyName: "id", hidden: true],
                [name: "PhoneNumber", messageBundleKey: "com.wowlabz.adminconsole.phoneNumber", returnValuePropertyOrCode: "ownerPhoneNumber", sortPropertyName: "ownerPhoneNumber"],
                [name: "Count", messageBundleKey: "com.wowlabz.adminconsole.count", returnValuePropertyOrCode: "count",sortPropertyName: "count"]
        ]

    * */


    def createResponseForTable = { config, returnList, id, sEcho ->
        def returnMap = [:]
        try {
            returnMap.iTotalRecords = returnList.totalCount
            returnMap.iTotalDisplayRecords = returnList.totalCount
        }catch(exp){
            returnMap.iTotalRecords = 10000
            returnMap.iTotalDisplayRecords = 10000
        }
        returnMap.sEcho = sEcho
        def dataReturnMap = []
        if(returnList instanceof Iterator){
            while(returnList.hasNext()){
                def eachData = returnList.next()
                def eachDataArr = []
                config.headerList.each { eachConfig ->
                    if (eachConfig.returnValuePropertyOrCode instanceof String) {
                        eachDataArr << evaluateExpressionOnBean(eachData, "${eachConfig.returnValuePropertyOrCode}")
                    } else if (eachConfig.returnValuePropertyOrCode instanceof Closure) {
                        eachDataArr << eachConfig.returnValuePropertyOrCode(eachData)
                    }
                }
                dataReturnMap << eachDataArr
            }
        }else {
            returnList.each { eachData ->
                def eachDataArr = []
                config.headerList.each { eachConfig ->
                    if (eachConfig.returnValuePropertyOrCode instanceof String) {
                        eachDataArr << evaluateExpressionOnBean(eachData, "${eachConfig.returnValuePropertyOrCode}")
                    } else if (eachConfig.returnValuePropertyOrCode instanceof Closure) {
                        eachDataArr << eachConfig.returnValuePropertyOrCode(eachData)
                    }
                }
                dataReturnMap << eachDataArr
            }
        }
        returnMap.aaData = dataReturnMap
        return returnMap
    }

    def evaluateExpressionOnBean(beanValue, expression) {
        def cellValue
        if (expression.contains(".")) {
            expression.split("\\.").each {
                if (cellValue) {
                    if (cellValue?.metaClass?.hasProperty(cellValue, it))
                        cellValue = cellValue."$it"
                } else {
                    if (beanValue?.metaClass?.hasProperty(beanValue, it))
                        cellValue = beanValue."$it"
                }
            }
        } else {
            if(beanValue instanceof GormEntity){
                try {
                    cellValue = beanValue?."$expression"
                }catch(exp){
                    cellValue = exp.getMessage()
                }
            }
            if (beanValue?.metaClass?.hasProperty(beanValue, expression))
                cellValue = beanValue?."$expression"
        }
        return cellValue
    }

    def getPropertyNameByIndex(config, int index) {
        return config.headerList[index].sortPropertyName
    }
}
