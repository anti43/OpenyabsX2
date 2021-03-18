// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require fa.all
//= require jquery-3.3.1.min
//= require bootstrap
//= require jquery.dataTables
//= require dataTables.bootstrap4
//= require dataTables.select
//= require dataTables.buttons
//= require buttons.print
//= require buttons.html5
//= require select.bootstrap4
//= require buttons.bootstrap4
//= require chosen.jquery
//= require_self

jQuery(document).ready(function () {
    jQuery("select").chosen();
});