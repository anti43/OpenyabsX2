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
class ReceiptItemController implements InitializingBean, OpenyabsController  {

    ReceiptItemService receiptItemService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    String receiptItemDataTableKey= "receiptItem-data"

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.receiptItem.name", sortPropertyName: "name"]
    ]]

    def index() {
        render view: "index", model: [tableConfig: getUserTableConfig(receiptDataTableKey)]
    }

    def indexData() {

        def offset = params.iDisplayStart ? Integer.parseInt(params.iDisplayStart) : 0
        def max = params.iDisplayLength ? Integer.parseInt(params.iDisplayLength) : 10
        def sortOrder = params.sSortDir_0 ? params.sSortDir_0 : "desc"
        def sortBy = dataService.getPropertyNameByIndex(getUserTableConfig(receiptDataTableKey), params.iSortCol_0 as Integer)
        def searchString = params.sSearch

        def args = [offset: offset, max: max, order: sortOrder, sort: sortBy]

        def returnList = searchString?.trim() ?
                dataService.createFulltextHql(ReceiptItem.class, searchString, args) :
                receiptItemService.list(args)
        def returnMap = dataService.createResponseForTable(getUserTableConfig(receiptDataTableKey), returnList, receiptDataTableKey, params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond receiptItemService.get(id)
    }

    def create() {
        respond new ReceiptItem(params)
    }

    def save(ReceiptItem receiptItem) {
        if (receiptItem == null) {
            notFound()
            return
        }

        try {
            receiptItemService.save(receiptItem)
        } catch (ValidationException e) {
            respond receiptItem.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'receiptItem.label', default: 'ReceiptItem'), receiptItem.id])
                redirect receiptItem
            }
            '*' { respond receiptItem, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond receiptItemService.get(id)
    }

    def update(ReceiptItem receiptItem) {
        if (receiptItem == null) {
            notFound()
            return
        }

        try {
            receiptItemService.save(receiptItem)
        } catch (ValidationException e) {
            respond receiptItem.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'receiptItem.label', default: 'ReceiptItem'), receiptItem.id])
                redirect receiptItem
            }
            '*'{ respond receiptItem, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        receiptItemService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'receiptItem.label', default: 'ReceiptItem'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
    @Override
    void afterPropertiesSet() throws Exception {
        createUserTableConfig($receiptItemDataTableKey, dataTableConfig)
    }

}
