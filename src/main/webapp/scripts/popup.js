jQuery(document).ready(function(){
    jQuery('td').mouseover(function(){
        var th = jQuery(this).closest('table').find('th').eq( this.cellIndex );
        if (th.attr('id') == "datatable:imageCol"){
            dlg1.show();
        }
        else{
            dlg1.hide();
        }
    });

});