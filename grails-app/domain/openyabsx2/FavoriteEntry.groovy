package openyabsx2

import org.grails.datastore.gorm.GormEntity

class FavoriteEntry {
    Long objectId
    String objectClass
    String name
    User user

    static constraints = {
    }

    static void createFor(GormEntity g, User user) {
        FavoriteEntry.withNewSession {
            new FavoriteEntry(name: g.toString(), objectClass: g.getClass().getName(), objectId: g["id"] as Long, user: user).save()
        }
    }
}
