package openyabsx2

import grails.gorm.services.Service
import openyabsx2.SearchEntry

@Service(DeletedEntry)
interface DeletedEntryService {

    DeletedEntry get(Serializable id)

    List<DeletedEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    DeletedEntry save(DeletedEntry deletedEntry)

}