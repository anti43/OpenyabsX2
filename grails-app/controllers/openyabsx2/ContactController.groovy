package openyabsx2

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ContactController {

    ContactService contactService
    DataService dataService


    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "cnumber", messageBundleKey: "openyabsx2.contact.cnumber", sortPropertyName: "cnumber"],
            [name: "name"],
            [name: "group"],
            [name: "prename"],
            [name: "street"],
            [name: "city"],
            [name: "country"],
            [name: "mainphone"],
            [name: "mailaddress"],
            [name: "company"]
    ]]

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        render view: "index"
    }

    def indexData() {
        println params as String
        //respond contactService.list(params), model:[contactCount: contactService.count()]

        def offset = params.iDisplayStart ? Integer.parseInt(params.iDisplayStart) : 0
        def max = params.iDisplayLength ? Integer.parseInt(params.iDisplayLength) : 10
        def sortOrder = params.sSortDir_0 ? params.sSortDir_0 : "desc"
        def sortBy = dataService.getPropertyNameByIndex(dataTableConfig, params.iSortCol_0 as Integer)
        def searchString = params.sSearch
        def returnList = contactService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "contacts", params.sEcho)
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
            respond contact.errors, view:'create'
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
            respond contact.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect contact
            }
            '*'{ respond contact, [status: OK] }
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
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
