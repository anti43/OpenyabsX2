package openyabsx2

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired

import javax.inject.Inject

class HistoryLogEntry {

    Date dateCreated
    String name
    User user
    Long objectId
    String objectClass

    static mapping = {
        name type: "text"

        autowire true
        autoTimestamp true
    }

    static void createFor(Map<String, Serializable> map) {
        HistoryLogEntry.withNewSession {
            new HistoryLogEntry(name: map.name, objectId: map.objectId,
                    objectClass: map.objectClass, user: map.user).save()        }
    }
}
