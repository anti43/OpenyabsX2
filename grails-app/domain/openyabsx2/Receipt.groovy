package openyabsx2


class Receipt {

    transient def receiptTypeService
    transient def springSecurityService
    transient def yabsNumberGeneratorService

    Date dateCreated  // auto filled
    Date lastUpdated  // auto filled
    ReceiptType receiptType
    Receipt previousReceipt
    String cnumber // auto filled

    static constraints = {
        receiptType nullable: false
        previousReceipt nullable: true
        cnumber unique: true, blank: false
    }

    static mapping = {
        autowire true
    }

    /**
     * Executed before an object is initially persisted to the database. If you return false, the insert will be cancelled.*/
    boolean beforeInsert() {
        receiptTypeService.execute("beforeInsert", this)
    }

    /**
     * Executed before an object is updated. If you return false, the update will be cancelled.*/
    boolean beforeUpdate() {
        receiptTypeService.execute("beforeUpdate", this)
    }

    /**
     * Executed before an object is deleted. If you return false, the delete will be cancelled.*/
    boolean beforeDelete() {
        receiptTypeService.execute("beforeDelete", this)
    }

    /**
     * Executed before an object is validated*/
    def beforeValidate() {
        cnumber = cnumber?:yabsNumberGeneratorService.nextVal(getClass().getName())
        receiptTypeService.execute("beforeValidate", this)
    }

    /**
     * Executed after an object is persisted to the database*/
    def afterInsert() {
        new HistoryLogEntry(name: "$receiptType created: $this", objectId: getId(), objectClass: getClass().name, user: springSecurityService.currentUser ).save()
        receiptTypeService.execute("afterInsert", this)
    }

    /**
     * Executed after an object has been updated*/
    def afterUpdate() {
        receiptTypeService.execute("afterUpdate", this)
    }

    /**
     * Executed after an object has been deleted*/
    def afterDelete() {
        receiptTypeService.execute("afterDelete", this)
    }

    /**
     * Executed when an object is loaded from the database*/
    def onLoad() {
        log.info("Loaded $this")
        //receiptTypeService.execute("onLoad", this)
    }

}
