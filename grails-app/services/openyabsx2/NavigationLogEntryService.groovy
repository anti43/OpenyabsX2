package openyabsx2

import grails.gorm.services.Service
import openyabsx2.SearchEntry

@Service(NavigationLogEntry)
abstract class NavigationLogEntryService {

    protected abstract NavigationLogEntry get(Serializable id)

    protected abstract List<NavigationLogEntry> list(Map args)

    protected abstract Long count()

    protected abstract void delete(Serializable id)

    protected abstract NavigationLogEntry save(NavigationLogEntry navigationLogEntry)

    List<NavigationLogEntry> listLimited(User user){
        NavigationLogEntry.findAllByUser(user, [max: 10])
    }
}