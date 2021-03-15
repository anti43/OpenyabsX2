// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-3.3.1.min
//= require bootstrap
//= require popper.min
//= require sb-admin-2
//= require jquery.dataTables
//= require dataTables.bootstrap4
//= require dataTables.select
//= require select.bootstrap4
//= require chosen.jquery
//= require_self

jQuery(document).ready(function () {

    /*var tables = jQuery("table");


    tables.addClass("table table-bordered table-striped table-condensed table-hover");
    var table = jQuery(".scaffold-list>table");
    jQuery(".scaffold-list>table>thead>tr").find('th:last').css('min-width', '250px');
//table.addClass("table table-bordered table-striped table-condensed table-hover");

    var pa = jQuery('div.content.scaffold-list');
    if (table.length) {

        table.dataTable({
            "order": [],
            "bPaginate": false,
            "bLengthChange": false,
            "bFilter": true,
            "bInfo": true,
            "bAutoWidth": false,
            fixedColumns: {
                leftColumns: 1,
                rightColumns: 1
            },
            columnDefs: [
                {type: 'natural-nohtml', targets: '_all'}
            ],
            "fnDrawCallback": function () {

            },
            "fnInitComplete": function () {
                pa.css('visibility', 'visible').hide().fadeIn('slow');
            }
        })
    } else {
        pa.css('visibility', 'visible').hide().fadeIn('slow');
    }

    jQuery.fn.dataTableExt.sErrMode = 'throw';

    var x = jQuery('th.sortable a');
    x.css('pointer-events', 'none');
    x.css('cursor', 'default');*/


    jQuery("select").chosen();

});