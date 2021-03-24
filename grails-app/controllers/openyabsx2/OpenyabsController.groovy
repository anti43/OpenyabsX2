package openyabsx2

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import groovy.json.JsonSlurper

import static org.springframework.http.HttpStatus.NOT_FOUND

trait OpenyabsController {

    SpringSecurityService springSecurityService
    JsonSlurper jsonSlurper = new JsonSlurper()

    void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [params.controller, params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    void createUserTableConfig(key, dataTableConfig) throws Exception {
        ConfigEntry.getValueForUserOrCreateGlobal("tableconfig.$key", springSecurityService.currentUser as User, (dataTableConfig as JSON) as String)
    }

    Map getUserTableConfig(key) {
        jsonSlurper.parseText(ConfigEntry.getValueForUser("tableconfig.$key", springSecurityService.currentUser as User).value) as Map
    }
}