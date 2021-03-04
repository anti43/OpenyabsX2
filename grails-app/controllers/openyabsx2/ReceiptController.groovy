package openyabsx2

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ReceiptController {

    ReceiptService receiptService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond receiptService.list(params), model:[receiptCount: receiptService.count()]
    }

    def show(Long id) {
        respond receiptService.get(id)
    }

    def create() {
        respond new Receipt(params)
    }

    def save(Receipt receipt) {
        if (receipt == null) {
            notFound()
            return
        }

        try {
            receiptService.save(receipt)
        } catch (ValidationException e) {
            respond receipt.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'receipt.label', default: 'Receipt'), receipt.id])
                redirect receipt
            }
            '*' { respond receipt, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond receiptService.get(id)
    }

    def update(Receipt receipt) {
        if (receipt == null) {
            notFound()
            return
        }

        try {
            receiptService.save(receipt)
        } catch (ValidationException e) {
            respond receipt.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'receipt.label', default: 'Receipt'), receipt.id])
                redirect receipt
            }
            '*'{ respond receipt, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        receiptService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'receipt.label', default: 'Receipt'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'receipt.label', default: 'Receipt'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
