package openyabsx2

import grails.gorm.services.Service
import openyabsx2.SearchEntry

@Service(ConfigEntry)
interface ConfigEntryService {

    ConfigEntry get(Serializable id)

    List<ConfigEntry> list(Map args)

    Long count()

    void delete(Serializable id)

    ConfigEntry save(ConfigEntry configEntry)

}