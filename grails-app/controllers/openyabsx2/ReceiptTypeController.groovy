package openyabsx2

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class ReceiptTypeController {

    ReceiptTypeService receiptTypeService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond receiptTypeService.list(params), model:[receiptTypeCount: receiptTypeService.count()]
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
