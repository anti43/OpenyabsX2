<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="yabs" />
        <g:set var="entityName" value="${message(code: 'receiptItem.label', default: 'ReceiptItem')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-receiptItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="btn btn-primary" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="btn btn-primary" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="btn btn-success" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-receiptItem" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.receiptItem}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.receiptItem}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.receiptItem}" method="PUT">
                <g:hiddenField name="version" value="${this.receiptItem?.version}" />
                <fieldset class="form">
                    <f:all bean="receiptItem"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
