package openyabsx2

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured('ROLE_ADMIN')
class HistoryLogEntryController {

    HistoryLogEntryService historyLogEntryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historyLogEntryService.list(params), model:[historyLogEntryCount: historyLogEntryService.count()]
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
