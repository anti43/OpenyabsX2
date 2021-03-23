package openyabsx2

import org.grails.datastore.gorm.GormEntity

class DeletedEntry {

    Date dateCreated
    Date lastUpdated
    Long objectId
    String objectClass
    String name
    User user

    static constraints = {
    }

    static mapping = {
        autowire true
        autoTimestamp true
    }

    static void createFor(GormEntity g, User user) {
        DeletedEntry.withNewSession {
            new DeletedEntry(name: g.toString(), objectClass: g.getClass().getName(), objectId: g["id"] as Long, user: user).save()
        }
    }
}
