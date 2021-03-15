package openyabsx2


import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import openyabsx2.*

import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class GroupController {

    GroupService groupService
    DataService dataService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static dataTableConfig =  [headerList : [
            [name: "id", messageBundleKey: "id", sortPropertyName: "id", hidden: true],
            [name: "name", messageBundleKey: "openyabsx2.group.name", sortPropertyName: "name"]
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
        def returnList = groupService.list([offset:offset, max:max, order: sortOrder, sort: sortBy ])
        def returnMap = dataService.createResponseForTable(dataTableConfig, returnList, "group-data", params.sEcho)
        render returnMap as JSON
    }

    def show(Long id) {
        respond groupService.get(id)
    }

    def create() {
        respond new Group(params)
    }

    def save(Group group) {
        if (group == null) {
            notFound()
            return
        }

        try {
            groupService.save(group)
        } catch (ValidationException e) {
            respond group.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'group.label', default: 'Group'), group.id])
                redirect group
            }
            '*' { respond group, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond groupService.get(id)
    }

    def update(Group group) {
        if (group == null) {
            notFound()
            return
        }

        try {
            groupService.save(group)
        } catch (ValidationException e) {
            respond group.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'group.label', default: 'Group'), group.id])
                redirect group
            }
            '*'{ respond group, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        groupService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'group.label', default: 'Group'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'group.label', default: 'Group'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
