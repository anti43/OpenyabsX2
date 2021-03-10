package openyabsx2


class Receipt {

    transient def receiptTypeService
    transient def receiptStatusService
    transient def springSecurityService
    transient def yabsNumberGeneratorService

    Date dateCreated  // auto filled
    Date lastUpdated  // auto filled
    ReceiptType receiptType
    ReceiptStatus receiptStatus
    Receipt previousReceipt
    String cnumber // auto filled
    Group group
    String description
    Contact contact
    BigDecimal netValue
    BigDecimal taxValue
    BigDecimal discountValue
    BigDecimal shippingValue
    Date todoDate
    Date endDate
    int reminders = 0


    static constraints = {
        cnumber unique: true, blank: false
        receiptType nullable: false
        receiptStatus nullable: false
        previousReceipt nullable: true
        description nullable: true
        discountValue nullable: true
        shippingValue nullable: true
        todoDate nullable: true
        endDate nullable: true
    }

    static mapping = {
        autowire true
        autoTimestamp true
    }

    /**
     * Executed before an object is initially persisted to the database. If you return false, the insert will be cancelled.*/
    boolean beforeInsert() {
        receiptTypeService.execute("beforeInsert", this)
        receiptStatusService.execute("beforeInsert", this)
    }

    /**
     * Executed before an object is updated. If you return false, the update will be cancelled.*/
    boolean beforeUpdate() {
        receiptTypeService.execute("beforeUpdate", this)
        receiptStatusService.execute("beforeUpdate", this)
    }

    /**
     * Executed before an object is deleted. If you return false, the delete will be cancelled.*/
    boolean beforeDelete() {
        receiptTypeService.execute("beforeDelete", this)
        receiptStatusService.execute("beforeDelete", this)
    }

    /**
     * Executed before an object is validated*/
    def beforeValidate() {
        cnumber = cnumber ?: yabsNumberGeneratorService.nextVal(Receipt.class.name)
        receiptTypeService.execute("beforeValidate", this)
        receiptStatusService.execute("beforeValidate", this)
    }

    /**
     * Executed after an object is persisted to the database*/
    def afterInsert() {
        new HistoryLogEntry(name: "$receiptType created: $this", objectId: getId(), objectClass: getClass().name, user: springSecurityService.currentUser?:User.list().find()).save()
        receiptTypeService.execute("afterInsert", this)
        receiptStatusService.execute("afterInsert", this)
    }

    /**
     * Executed after an object has been updated*/
    def afterUpdate() {
        receiptTypeService.execute("afterUpdate", this)
        receiptStatusService.execute("afterUpdate", this)
    }

    /**
     * Executed after an object has been deleted*/
    def afterDelete() {
        receiptTypeService.execute("afterDelete", this)
        receiptStatusService.execute("afterDelete", this)
    }

    /**
     * Executed when an object is loaded from the database*/
    def onLoad() {
        log.info("Loaded $this")
        //receiptTypeService.execute("onLoad", this)
    }

}
