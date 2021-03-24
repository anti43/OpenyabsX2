package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import groovy.json.JsonSlurper
import openyabsx2.*
import org.springframework.beans.factory.InitializingBean

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ContactController implements InitializingBean, OpenyabsController {

    static scope = "session"

    ContactService contactService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    String contactDataTableKey= "contact-data"

    static dataTableConfig = [headerList: [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "cnumber", messageBundleKey: "openyabsx2.contact.cnumber", sortPropertyName: "cnumber"],
            [name: "name"],
            [name: "group"],
            [name: "prename"],
            [name: "street"],
            [name: "city"],
            [name: "country"],
            [name: "mainPhone"],
            [name: "mailAddress"],
            [name: "company"]
    ]]

    def index() {
        render view: "index", model: [tableConfig: getUserTableConfig(contactDataTableKey)]
    }

    def indexData() {

        def offset = params.iDisplayStart ? Integer.parseInt(params.iDisplayStart) : 0
        def max = params.iDisplayLength ? Integer.parseInt(params.iDisplayLength) : 10
        def sortOrder = params.sSortDir_0 ? params.sSortDir_0 : "desc"
        def sortBy = dataService.getPropertyNameByIndex(getUserTableConfig(contactDataTableKey), params.iSortCol_0 as Integer)
        def searchString = params.sSearch

        def args = [offset: offset, max: max, order: sortOrder, sort: sortBy]

        def returnList = searchString?.trim() ?
                dataService.createFulltextHql(Contact.class, searchString, args) :
                contactService.list(args)
        def returnMap = dataService.createResponseForTable(getUserTableConfig(contactDataTableKey), returnList, contactDataTableKey, params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond contactService.get(id)
    }

    def create() {
        respond new Contact(params)
    }

    def save(Contact contact) {
        if (contact == null) {
            notFound()
            return
        }

        try {
            contactService.save(contact)
        } catch (ValidationException e) {
            respond contact.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect contact
            }
            '*' { respond contact, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond contactService.get(id)
    }

    def update(Contact contact) {
        if (contact == null) {
            notFound()
            return
        }

        try {
            contactService.save(contact)
        } catch (ValidationException e) {
            respond contact.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect contact
            }
            '*' { respond contact, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        contactService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }


    @Override
    void afterPropertiesSet() throws Exception {
        createUserTableConfig("tableconfig.$contactDataTableKey", dataTableConfig)
    }


}
