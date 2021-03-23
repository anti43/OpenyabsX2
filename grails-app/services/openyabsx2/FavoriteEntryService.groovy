package openyabsx2

import grails.gorm.services.Service
import openyabsx2.SearchEntry

@Service(FavoriteEntry)
interface FavoriteEntryService {

    FavoriteEntry get(Serializable id)

    List<FavoriteEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    FavoriteEntry save(FavoriteEntry favoriteEntry)

}