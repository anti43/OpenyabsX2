package openyabsx2

import grails.gorm.services.Service

@Service(Group)
interface GroupService {

    Group get(Serializable id)

    List<Group> list(Map args)

    Long count()

    void delete(Serializable id)

    Group save(Group group)
}