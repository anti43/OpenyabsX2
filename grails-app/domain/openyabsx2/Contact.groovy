package openyabsx2

class Contact {

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
    String mainphone = ""
    String workphone = ""
    String fax = ""
    String mobilephone = ""
    String mailaddress = ""
    String website = ""
    String notes = ""
    String company = ""
    String department = ""
    String bankAccount = ""
    String bankid = ""
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
        if (!group) group = Group.findByName(Group.ROOT)
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
        mainphone nullable: true
        workphone nullable: true
        fax nullable: true
        mobilephone nullable: true
        mailaddress nullable: true
        website nullable: true
        notes nullable: true, type: "text"
        company nullable: true
        department nullable: true
        bankAccount nullable: true
        bankid nullable: true
        bankName nullable: true
        bankCurrency nullable: true
        bankCountry nullable: true
        companyName nullable: true
    }

    String toString(){
        name
    }
}
