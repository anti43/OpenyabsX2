package openyabsx2

/**
 * Based on https://dzone.com/articles/using-datatablesnet-grails
 */
class DataTableTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def springSecurityService
    static namespace = "dataTable"

    def table = { attrs, body ->

        def dataTableHeaderListConfig = attrs.config?.headerList?:[]
        def removeSorting = false
        def serverURL = attrs.serverURL
        def fixedClass = attrs.fixedTableClass ?: 'noClass'
        def controller = attrs.controller?:"?"

        out << """ 
            <div class="yabs-data-inner">
            <table id="${attrs.id}" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered ${fixedClass} float-left">
            <thead>
            <tr>"""

        dataTableHeaderListConfig.each {
            out << """  <th style="cursor: pointer;" sortPropertyName="${it.sortPropertyName?:it.name}" sortable="${it.sortable?:true}" """
            out << """>"""
            out << g.message(code: it.messageBundleKey?:("openyabsx2.${controller}.${it.name}"), default: it.name)
            out << """</th> """
        }

        out << """</tr>
            </thead>
            <tbody>

            </tbody>
            </table> 
            </div>
                <script type="text/javascript">
                var dataTableDefaultSorting = [];
                var hideSorting = [];

                """
        dataTableHeaderListConfig.eachWithIndex { obj, i ->
            if (obj.defaultSorting) {
                out << """ dataTableDefaultSorting[dataTableDefaultSorting.length]=[${i},'${obj.defaultSortOrder}'];
                     """
            }
            if (obj.disableSorting == "true") {
                removeSorting = true
                out << """ hideSorting[hideSorting.length] = ${i};
                     """
            }
        }
        out << """ if(dataTableDefaultSorting.length==0){
                            dataTableDefaultSorting = [[0,"asc"]];
                           } """
        out << """
                        jQuery.extend( jQuery.fn.dataTableExt.oStdClasses, {
                            "sWrapper": "dataTables_wrapper form-inline"
                        } );

                   jQuery(document).ready( function() {
                       var ${attrs.id}oTableCurrentData;
                       var ${attrs.id}oTable = jQuery('#${attrs.id}').dataTable({
                               "aaSorting":dataTableDefaultSorting,
                               "processing": true,
                               "serverSide": true, 
                               "select": true,
                               "deferRender": true,
                               "responsive": true, 
                               "autoWidth": true,
                               "colReorder": true, """
        if (attrs.serverParamsFunction) {
            out << """
                                "fnServerParams":  function(aoData){
                             var hideSearch='${attrs.hideSearch}'
                          if(hideSearch!='null'){
                               jQuery("#${attrs.id}_filter").hide();
                              }
                                     ${attrs.id}ServerParamsFunction(aoData)
                                },
                                  """
        }
        if (removeSorting) {
            out << """
                        "aoColumnDefs": [{
                                "bSortable": false,
                                "aTargets": hideSorting
                                }],
                    """
        }

        out << """
            "fnDrawCallback": function(oSettings) {
                  var drawLabel='${attrs.drawLabelElementId}';
                             if(drawLabel!='null'){
                        jQuery('${attrs.drawLabelElementId}').html("["+oSettings._iRecordsTotal+"]");
                       }
                   var callBackFunction='${attrs.callBackFunction}';
                    if(callBackFunction!='null'){
                    ${attrs.id}CallBackFunction(oSettings._iRecordsTotal);
                }
            },
        """
        out << """
                "sAjaxSource": "${serverURL}",
                "sDom": "<'container-fluid'<'row'<'col-sm-2'<'float-left'l>><'col'B><'col'<'float-right'f>>r>'<'row''<'col't>><'row'<'col-md-6'i><'col-md-6'p>>>",
                "fnCreatedRow":function( nRow, aData, iDataIndex ) {
                    jQuery(nRow).attr("mphrxRowIndex",iDataIndex);
                    jQuery(nRow).attr("mphrxRowID",aData[0]);
                                """
        if (attrs.contextMenuTarget) {
            out << """
                    jQuery(nRow).contextmenu({
                          target:'#${attrs.contextMenuTarget}',
                          before: function(e, element) {
                            ${attrs.id}oTableCurrentData = ${attrs.id}oTable.fnGetData( jQuery(element).attr("mphrxRowIndex") );
                            return true;
                          },
                          onItem: function(e, element) {
                            if(${attrs.id}ContextMenuHandler){
                                ${attrs.id}ContextMenuHandler(e,element);
                            }
                          }
                    })
"""
        }

        out << """
                    },
                    buttons: [ 'print',
                                {
                                    extend: 'copyHtml5',
                                    exportOptions: { orthogonal: 'export' }
                                } 
                            ] 
                });
                jQuery('#${attrs.id}_filter input').unbind();
                jQuery('#${attrs.id}_filter input').bind('keyup', function(e) {
                   if(e.keyCode == 13) {
                    ${attrs.id}oTable.fnFilter(this.value);
                }
              
               });
                       """
        dataTableHeaderListConfig.eachWithIndex { obj, i ->
            if (obj.hidden) {
                out << """ ${attrs.id}oTable.fnSetColumnVis(${i}, false); """
            }

        }
        out << """
            jQuery('#${attrs.id} tbody').on('dblclick', 'tr', function () { 
                 
                    var data = ${attrs.id}oTable.DataTable().row( this ).data();
                    window.location = "/${controller}/show/" + data[0];
                } );
      
        });
        \$.fn.dataTable.ext.errMode = 'throw';
        
      
</script>"""
    }
}
