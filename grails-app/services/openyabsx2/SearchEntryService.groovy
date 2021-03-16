package openyabsx2

import grails.gorm.services.Service

@Service(SearchEntry)
interface SearchEntryService {

    SearchEntry get(Serializable id)

    List<SearchEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    SearchEntry save(SearchEntry searchentry)
}