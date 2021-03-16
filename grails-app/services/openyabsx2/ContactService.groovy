package openyabsx2

import grails.gorm.services.Service

@Service(Contact)
interface ContactService {

    Contact get(Serializable id)

    List<Contact> list(Map args)

    Long count()

    void delete(Serializable id)

    Contact save(Contact contact)
}