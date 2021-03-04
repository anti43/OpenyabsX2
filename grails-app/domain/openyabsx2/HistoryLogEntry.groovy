package openyabsx2

class HistoryLogEntry {

    Date dateCreated
    String name
    User user
    Long objectId
    String objectClass

    static mapping = {
        name type: "text"
    }
}
