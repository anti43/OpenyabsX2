package openyabsx2

class Contact {

    transient def springSecurityService
    transient def yabsNumberGeneratorService

    Group group

    String name = ""
    String cnumber = ""
    String taxnumber = ""
    String title = ""
    String prename = ""
    String street = ""
    String zip = ""
    String city = ""
    String country = ""
    String mainPhone = ""
    String workPhone = ""
    String fax = ""
    String mobilePhone = ""
    String mailAddress = ""
    String website = ""
    String notes = ""
    String company = ""
    String department = ""
    String bankAccount = ""
    String bankId = ""
    String bankName = ""
    String bankCurrency = ""
    String bankCountry = ""
    String companyName = ""
    boolean isEnabled = true
    boolean isCustomer = false
    boolean isManufacturer = false
    boolean isSupplier = false

    String importid

    def beforeValidate() {
        cnumber = cnumber?:yabsNumberGeneratorService.nextVal(Contact.class.name)
        if (!group) group = Group.findByName(Group.ROOT)
    }

    static mapping = {
        autowire true
        autoTimestamp true
    }

    static constraints = {
        name blank: false, unique: false
        cnumber blank: false, unique: false
        group nullable: false
        importid nullable: true

        taxnumber nullable: true
        title nullable: true
        prename nullable: true
        street nullable: true
        zip nullable: true
        city nullable: true
        country nullable: true
        mainPhone nullable: true
        workPhone nullable: true
        fax nullable: true
        mobilePhone nullable: true
        mailAddress nullable: true
        website nullable: true
        notes nullable: true, type: "text"
        company nullable: true
        department nullable: true
        bankAccount nullable: true
        bankId nullable: true
        bankName nullable: true
        bankCurrency nullable: true
        bankCountry nullable: true
        companyName nullable: true
    }

    String toString(){
        name
    }
}
