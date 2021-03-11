package openyabsx2

/**
 * Based on https://dzone.com/articles/using-datatablesnet-grails
 */
class DataTableTagLib {
    static defaultEncodeAs = [taglib:'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def springSecurityService
    static namespace = "dataTable"

    def table = { attrs, body ->

        def dataTableHeaderListConfig = attrs.config.headerList
        def removeSorting = false
        def serverURL = attrs.serverURL
        def fixedClass=attrs.fixedTableClass?:'noClass'

        println "fixedClass=="+fixedClass

        out << """
            <table id="${attrs.id}" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered ${fixedClass}">
            <thead>
            <tr>"""

        dataTableHeaderListConfig.each {
            out << """  <th style="cursor: pointer;" sortPropertyName="${it.sortPropertyName}" sortable="${it.sortable}" """
            out << """>"""
            out << g.message(code: it.messageBundleKey, default: it.name)
            out << """</th> """
        }


        out << """</tr>
            </thead>
            <tbody>

            </tbody>
            </table>
                <script type="text/javascript">
                var dataTableDefaultSorting = [];
                var hideSorting = [];

                """
        dataTableHeaderListConfig.eachWithIndex {obj, i ->
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
                       var ${attrs.id}oTable =  jQuery('#${attrs.id}').dataTable({
                            "aaSorting":dataTableDefaultSorting,
                            "bProcessing": true,
                            "bServerSide": true,"""
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

//drawLabelElementId => This variable is set from the dashboard  where we need to set count after the table is full loaded,we can implement this in other details page as well.
        println("Removing Sort Status : ${removeSorting}")
        if (removeSorting) {
            out << """
                        "aoColumnDefs": [{
                                "bSortable": false,
                                "aTargets": hideSorting
                                }],
                    """
        }

        out <<
                """
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
                            "sDom": "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-6'i><'col-md-6'p>>",
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
                            "oTableTools": {
                                "aButtons": [
                                    "copy",
                                    "print",
                                    {
                                        "sExtends":    "collection",
                                        "sButtonText": 'Save <span class="caret" />',
                                        "aButtons":    [ "csv", "xls", "pdf" ]
                                    }
                                ]
                            }

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
                    });
                </script>
            """
    }
}
