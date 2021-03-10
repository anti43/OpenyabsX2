package openyabsx2

class Product {

    String cnumber
    String name
    String description

    static constraints = {
    }

    static mapping = {
        description type: "text"
    }
}
