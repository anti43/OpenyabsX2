<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="yabs" />
        <g:set var="entityName" value="${message(code: 'historyLogEntry.label', default: 'HistoryLogEntry')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-historyLogEntry" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="btn btn-primary" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="btn btn-primary" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-historyLogEntry" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="alert alert-info" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.historyLogEntry}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.historyLogEntry}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.historyLogEntry}" method="POST">
                <fieldset class="form">
                    <f:all bean="historyLogEntry"/>
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
