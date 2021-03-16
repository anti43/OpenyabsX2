package openyabsx2

import org.grails.datastore.gorm.GormEntity

import javax.inject.Inject

class SearchEntry {

    Date dateCreated
    Date lastUpdated
    Long objectId
    String objectClass
    String name
    String data

    SearchEntry(GormEntity d) {
        this.objectId = d."id" as Long
        this.objectClass = d.getClass().getName()
        this.name = "$objectClass: $objectId"
        update(d)
    }

    static constraints = {
    }

    static mapping = {
        data type: "text"
        autowire true
        autoTimestamp true
    }

    static void createFor(GormEntity g) {
        SearchEntry.withNewSession {
            new SearchEntry(g).save()
        }
    }

    static void updateFor(GormEntity g) {
        SearchEntry.withNewSession {
            def search = SearchEntry.findByObjectIdAndObjectClass(g.id as Long, g.getClass().getName())
            if (search) {
                search.update(g)
                search.save()
            } else {
                new SearchEntry(g).save()
            }
        }
    }

    static removeFor(GormEntity g) {
        SearchEntry.withNewSession {
            def search = SearchEntry.findByObjectIdAndObjectClass(g.id as Long, g.getClass().getName())
            if (search) search.delete()
        }
    }

    void update(GormEntity object) {
        StringBuilder stringBuilder = new StringBuilder()
        object.getProperties().forEach({ k, v ->
            stringBuilder.append(v as String).append(" ")
        })
        data = stringBuilder.toString()
    }
}
