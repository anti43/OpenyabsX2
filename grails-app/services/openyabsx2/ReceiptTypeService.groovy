package openyabsx2

import ch.qos.logback.classic.Logger
import grails.gorm.services.Service

import javax.inject.Inject

@Service(ReceiptType)
abstract class ReceiptTypeService {

    protected abstract ReceiptType get(Serializable id)

    protected abstract List<ReceiptType> list(Map args)

    protected abstract Long count()

    abstract void delete(Serializable id)

    abstract ReceiptType save(ReceiptType receiptType)

    boolean execute(String event, Receipt receipt){
        String script = receipt.getReceiptType()?.getProperty("${event}Script")
        if(script == null){
            //log.info("No '$event' script for $receipt found")
            return true
        }
        //fixme cache compiled script
        def res = Eval.me("receipt", receipt, script)
        log.info("Event '$event' script result: $res")
        return res
    }
}