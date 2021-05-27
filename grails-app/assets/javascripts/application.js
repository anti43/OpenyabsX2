// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require fa.all
//= require jquery-3.6.0
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
//= require tinymce
//= require_self

jQuery(document).ready(function () {
    jQuery("select").chosen();


    tinymce.init({
        selector: 'textarea',
        height: 250,
        theme: 'modern',
        plugins: 'print preview fullpage searchreplace autolink directionality visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount imagetools  contextmenu colorpicker textpattern help',
        toolbar1: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
        image_advtab: true,
        templates: "/template/list",
        content_css: [
            '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
            '//www.tinymce.com/css/codepen.min.css'
        ],
        setup: function (editor) {
            editor.getElement().removeAttribute('required');
        }
    });

    $('a').click(function (e) {

        console.log(e);
        e.preventDefault();
        window.location = e.target.href;
        }
    )
});