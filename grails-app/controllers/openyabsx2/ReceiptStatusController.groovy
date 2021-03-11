package openyabsx2

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured('ROLE_ADMIN')
class ReceiptStatusController {

    ReceiptStatusService receiptStatusService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond receiptStatusService.list(params), model:[receiptStatusCount: receiptStatusService.count()]
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
