package openyabsx2


import grails.gorm.services.Service

@Service(ReceiptStatus)
abstract class ReceiptStatusService {

    protected abstract ReceiptStatus get(Serializable id)

    protected abstract List<ReceiptStatus> list(Map args)

    protected abstract Long count()

    abstract void delete(Serializable id)

    abstract ReceiptStatus save(ReceiptStatus receiptType)

    boolean execute(String event, Receipt receipt){
        String script = receipt.getReceiptStatus()?.getProperty("${event}Script")
        if(script == null){
            log.info("No '$event' script for $receipt found")
            return true
        }
        //fixme cache compiled script
        def res = Eval.me("receipt", receipt, script)
        log.info("Event '$event' script result: $res")
        return res
    }
}