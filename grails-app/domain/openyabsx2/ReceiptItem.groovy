package openyabsx2


class ReceiptItem {

    Date dateCreated  // auto filled
    Date lastUpdated  // auto filled

    //Receipt receipt
    Receipt fromReceipt
    Product product

    int index = 0
    BigDecimal quantity = BigDecimal.ZERO;
    String measurement
    String description
    String linkUrl
    String name

    BigDecimal singleNetValue = BigDecimal.ZERO;
    BigDecimal taxPercentage = BigDecimal.ZERO;
    BigDecimal totalTaxValue = BigDecimal.ZERO;
    BigDecimal totalNetValue = BigDecimal.ZERO;
    BigDecimal totalGrosValue = BigDecimal.ZERO;
    BigDecimal discountPercentage = BigDecimal.ZERO;
    BigDecimal discountTotalNetValue = BigDecimal.ZERO;

    Date deliveryDate;

    static belongsTo = [receipt: Receipt]

    static constraints = {
        receipt nullable: false
        product nullable: true
        fromReceipt nullable: true
        measurement nullable: true
        description nullable: true
        linkUrl nullable: true
    }

    static mapping = {
        description type: "text"
        autoTimestamp true
    }

    /**
     * Executed after an object is persisted to the database*/
    def afterInsert() {
        SearchEntry.createFor(this)
    }

    /**
     * Executed after an object has been updated*/
    def afterUpdate() {
        SearchEntry.updateFor(this)
    }

    /**
     * Executed after an object has been deleted*/
    def afterDelete() {
        SearchEntry.removeFor(this)
    }
}
