package openyabsx2

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured('ROLE_ADMIN')
class ReceiptItemController {

    ReceiptItemService receiptItemService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond receiptItemService.list(params), model:[receiptItemCount: receiptItemService.count()]
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

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'receiptItem.label', default: 'ReceiptItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
