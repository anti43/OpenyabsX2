<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="yabs" />
        <g:set var="entityName" value="${message(code: 'receiptStatus.label', default: 'ReceiptStatus')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="btn btn-primary" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="btn btn-success" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-receiptStatus" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="alert alert-info" role="status">${flash.message}</div>
            </g:if>
            <div class="yabs-data container">
                <dataTable:table id="receiptStatusData"
                                 serverURL="${createLink(controller: 'receiptStatus', action: 'indexData')}"
                                 config="${tableConfig}"/>
            </div>
        </div>
    </body>
</html>