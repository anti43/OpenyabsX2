package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ReceiptStatusController {

    ReceiptStatusService receiptStatusService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.receiptStatus.name", sortPropertyName: "name"]
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
        def returnList = receiptStatusService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "receiptStatus-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond receiptStatusService.get(id)
    }

    def create() {
        respond new ReceiptStatus(params)
    }

    def save(ReceiptStatus receiptStatus) {
        if (receiptStatus == null) {
            notFound()
            return
        }

        try {
            receiptStatusService.save(receiptStatus)
        } catch (ValidationException e) {
            respond receiptStatus.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'receiptStatus.label', default: 'ReceiptStatus'), receiptStatus.id])
                redirect receiptStatus
            }
            '*' { respond receiptStatus, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond receiptStatusService.get(id)
    }

    def update(ReceiptStatus receiptStatus) {
        if (receiptStatus == null) {
            notFound()
            return
        }

        try {
            receiptStatusService.save(receiptStatus)
        } catch (ValidationException e) {
            respond receiptStatus.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'receiptStatus.label', default: 'ReceiptStatus'), receiptStatus.id])
                redirect receiptStatus
            }
            '*'{ respond receiptStatus, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        receiptStatusService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'receiptStatus.label', default: 'ReceiptStatus'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'receiptStatus.label', default: 'ReceiptStatus'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
