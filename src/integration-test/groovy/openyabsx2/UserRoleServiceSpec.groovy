package openyabsx2

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UserRoleServiceSpec extends Specification {

    UserRoleService userRoleService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new UserRole(...).save(flush: true, failOnError: true)
        //new UserRole(...).save(flush: true, failOnError: true)
        //UserRole userRole = new UserRole(...).save(flush: true, failOnError: true)
        //new UserRole(...).save(flush: true, failOnError: true)
        //new UserRole(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //userRole.id
    }

    void "test get"() {
        setupData()

        expect:
        userRoleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<UserRole> userRoleList = userRoleService.list(max: 2, offset: 2)

        then:
        userRoleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        userRoleService.count() == 5
    }

    void "test delete"() {
        Long userRoleId = setupData()

        expect:
        userRoleService.count() == 5

        when:
        userRoleService.delete(userRoleId)
        sessionFactory.currentSession.flush()

        then:
        userRoleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        UserRole userRole = new UserRole()
        userRoleService.save(userRole)

        then:
        userRole.id != null
    }
}
