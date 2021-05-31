package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import javax.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class NavigationLogEntryController implements OpenyabsController {

    NavigationLogEntryService navigationLogEntryService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig = [headerList: [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.navigationLogEntry.name", sortPropertyName: "name"],
            [name: "url", messageBundleKey: "openyabsx2.navigationLogEntry.url", sortPropertyName: "url"],
            [name: "count", messageBundleKey: "openyabsx2.navigationLogEntry.count", sortPropertyName: "count"]
    ]]



    synchronized log(String name, String url) {
        NavigationLogEntry.withNewTransaction {
            def a = NavigationLogEntry.findOrCreateWhere(url: url, user: springSecurityService.currentUser as User, name: name)
            a.setCount(a.getCount() + 1)
            a.save(flush: true)
            render ""
        }
    }

    def index() {
        render view: "index", model: [tableConfig: dataTableConfig]
    }

    def indexData() {

        def offset = params.iDisplayStart ? Integer.parseInt(params.iDisplayStart) : 0
        def max = params.iDisplayLength ? Integer.parseInt(params.iDisplayLength) : 10
        def sortOrder = params.sSortDir_0 ? params.sSortDir_0 : "desc"
        def sortBy = dataService.getPropertyNameByIndex(dataTableConfig, params.iSortCol_0 as Integer)
        def searchString = params.sSearch
        def returnList = navigationLogEntryService.list([offset: offset, max: max, order: sortOrder, sort: sortBy])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "navigationLogEntry-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond navigationLogEntryService.get(id)
    }

    def create() {
        respond new NavigationLogEntry(params)
    }

    def save(NavigationLogEntry navigationLogEntry) {
        if (navigationLogEntry == null) {
            notFound()
            return
        }

        try {
            navigationLogEntryService.save(navigationLogEntry)
        } catch (ValidationException e) {
            respond navigationLogEntry.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'navigationLogEntry.label', default: 'NavigationLogEntry'), navigationLogEntry.id])
                redirect navigationLogEntry
            }
            '*' { respond navigationLogEntry, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond navigationLogEntryService.get(id)
    }

    def update(NavigationLogEntry navigationLogEntry) {
        if (navigationLogEntry == null) {
            notFound()
            return
        }

        try {
            navigationLogEntryService.save(navigationLogEntry)
        } catch (ValidationException e) {
            respond navigationLogEntry.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'navigationLogEntry.label', default: 'NavigationLogEntry'), navigationLogEntry.id])
                redirect navigationLogEntry
            }
            '*' { respond navigationLogEntry, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        navigationLogEntryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'navigationLogEntry.label', default: 'NavigationLogEntry'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }


}
