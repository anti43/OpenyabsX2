package openyabsx2

class NavigationLogEntry {

    Date dateCreated
    String name
    String url
    User user
    long count = 0

    static mapping = {
        name type: "text"
        url type: "text"

        autowire true
        autoTimestamp true
    }

}
