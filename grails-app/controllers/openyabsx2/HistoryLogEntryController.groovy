package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class HistoryLogEntryController {

    HistoryLogEntryService historyLogEntryService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.historyLogEntry.name", sortPropertyName: "name"]
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
        def returnList = historyLogEntryService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "historyLogEntry-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond historyLogEntryService.get(id)
    }

    def create() {
        respond new HistoryLogEntry(params)
    }

    def save(HistoryLogEntry historyLogEntry) {
        if (historyLogEntry == null) {
            notFound()
            return
        }

        try {
            historyLogEntryService.save(historyLogEntry)
        } catch (ValidationException e) {
            respond historyLogEntry.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'historyLogEntry.label', default: 'HistoryLogEntry'), historyLogEntry.id])
                redirect historyLogEntry
            }
            '*' { respond historyLogEntry, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond historyLogEntryService.get(id)
    }

    def update(HistoryLogEntry historyLogEntry) {
        if (historyLogEntry == null) {
            notFound()
            return
        }

        try {
            historyLogEntryService.save(historyLogEntry)
        } catch (ValidationException e) {
            respond historyLogEntry.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'historyLogEntry.label', default: 'HistoryLogEntry'), historyLogEntry.id])
                redirect historyLogEntry
            }
            '*'{ respond historyLogEntry, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        historyLogEntryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'historyLogEntry.label', default: 'HistoryLogEntry'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'historyLogEntry.label', default: 'HistoryLogEntry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
