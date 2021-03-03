package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class GroupServiceSpec extends Specification {

    GroupService groupService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        //Group group = new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        //new Group(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //group.id
    }

    void "test get"() {
        setupData()

        expect:
        groupService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Group> groupList = groupService.list(max: 2, offset: 2)

        then:
        groupList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        groupService.count() == 5
    }

    void "test delete"() {
        Long groupId = setupData()

        expect:
        groupService.count() == 5

        when:
        groupService.delete(groupId)
        sessionFactory.currentSession.flush()

        then:
        groupService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Group group = new Group()
        groupService.save(group)

        then:
        group.id != null
    }
}
