package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ReceiptTypeController {

    ReceiptTypeService receiptTypeService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.receiptType.name", sortPropertyName: "name"]
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
        def returnList = receiptTypeService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "receiptType-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond receiptTypeService.get(id)
    }

    def create() {
        respond new ReceiptType(params)
    }

    def save(ReceiptType receiptType) {
        if (receiptType == null) {
            notFound()
            return
        }

        try {
            receiptTypeService.save(receiptType)
        } catch (ValidationException e) {
            respond receiptType.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'receiptType.label', default: 'ReceiptType'), receiptType.id])
                redirect receiptType
            }
            '*' { respond receiptType, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond receiptTypeService.get(id)
    }

    def update(ReceiptType receiptType) {
        if (receiptType == null) {
            notFound()
            return
        }

        try {
            receiptTypeService.save(receiptType)
        } catch (ValidationException e) {
            respond receiptType.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'receiptType.label', default: 'ReceiptType'), receiptType.id])
                redirect receiptType
            }
            '*'{ respond receiptType, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        receiptTypeService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'receiptType.label', default: 'ReceiptType'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'receiptType.label', default: 'ReceiptType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
