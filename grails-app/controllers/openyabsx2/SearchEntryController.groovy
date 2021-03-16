package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class SearchEntryController {

    SearchEntryService searchEntryService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.searchentry.name", sortPropertyName: "name"]
    ]]

    def index() {
        render view: "index", model: [tableConfig: dataTableConfig]
    }

    def indexData() {

        def offset = params.iDisplayStart ? Integer.parseInt(params.iDisplayStart) : 0
        def max = params.iDisplayLength ? Integer.parseInt(params.iDisplayLength) : 10
        def sortOrder = params.sSortDir_0 ? params.sSortDir_0 : "desc"
        def sortBy = dataService.getPropertyNameByIndex(dataTableConfig, params.iSortCol_0 as Integer)
        def searchString = params.sSearch
        def returnList = searchEntryService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "searchentry-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond searchEntryService.get(id)
    }

    def create() {
        respond new SearchEntry(params)
    }

    def save(SearchEntry searchentry) {
        if (searchentry == null) {
            notFound()
            return
        }

        try {
            searchentryService.save(searchentry)
        } catch (ValidationException e) {
            respond searchentry.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'searchentry.label', default: 'Searchentry'), searchentry.id])
                redirect searchentry
            }
            '*' { respond searchentry, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond searchentryService.get(id)
    }

    def update(SearchEntry searchentry) {
        if (searchentry == null) {
            notFound()
            return
        }

        try {
            searchEntryService.save(searchentry)
        } catch (ValidationException e) {
            respond searchentry.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'searchentry.label', default: 'Searchentry'), searchentry.id])
                redirect searchentry
            }
            '*'{ respond searchentry, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        searchEntryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'searchentry.label', default: 'Searchentry'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'searchentry.label', default: 'Searchentry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
