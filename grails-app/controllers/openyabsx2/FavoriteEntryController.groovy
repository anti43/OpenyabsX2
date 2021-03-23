package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class FavoriteEntryController {

    FavoriteEntryService favoriteEntryService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.favoriteEntry.name", sortPropertyName: "name"]
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
        def returnList = favoriteEntryService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "favoriteEntry-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond favoriteEntryService.get(id)
    }

    def create() {
        respond new FavoriteEntry(params)
    }

    def save(FavoriteEntry favoriteEntry) {
        if (favoriteEntry == null) {
            notFound()
            return
        }

        try {
            favoriteEntryService.save(favoriteEntry)
        } catch (ValidationException e) {
            respond favoriteEntry.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'favoriteEntry.label', default: 'FavoriteEntry'), favoriteEntry.id])
                redirect favoriteEntry
            }
            '*' { respond favoriteEntry, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond favoriteEntryService.get(id)
    }

    def update(FavoriteEntry favoriteEntry) {
        if (favoriteEntry == null) {
            notFound()
            return
        }

        try {
            favoriteEntryService.save(favoriteEntry)
        } catch (ValidationException e) {
            respond favoriteEntry.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'favoriteEntry.label', default: 'FavoriteEntry'), favoriteEntry.id])
                redirect favoriteEntry
            }
            '*'{ respond favoriteEntry, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        favoriteEntryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'favoriteEntry.label', default: 'FavoriteEntry'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'favoriteEntry.label', default: 'FavoriteEntry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
