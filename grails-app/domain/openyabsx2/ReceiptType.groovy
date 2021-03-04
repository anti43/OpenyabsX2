package openyabsx2

class ReceiptType {

    String name
    String beforeInsertScript
    String beforeUpdateScript
    String beforeDeleteScript
    String beforeValidateScript
    String afterInsertScript
    String afterUpdateScript
    String afterDeleteScript

    static constraints = {
        name unique: true, blank: false
        beforeInsertScript nullable: true
        beforeUpdateScript nullable: true
        beforeDeleteScript nullable: true
        beforeValidateScript nullable: true
        afterInsertScript nullable: true
        afterUpdateScript nullable: true
        afterDeleteScript nullable: true
    }

    String toString(){
        name
    }
}
