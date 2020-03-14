/**
 * エンターキータブ移動
 *
 * 指定Formタグ内の入力タグをエンターキーでタブ移動できるようにする。
 *
 * 【使い方】
 *   // html
 *   <s:form method="POST" action="/employee/addNewCreate/" styleId="modal-addNew-form">
 *       :
 *
 *   // js
 *   <script type="text/javascript">
 *   $(function() {
 *       $('#modal-addNew-form').enterNext();
 *   });
 *   </script>
 *
 */
(function(jQuery) {
    jQuery.fn.extend({
        enterNext: function() {
            var startTabIndex = 1;
            jQuery(this).find('*').children().each(function() {
                var tag = jQuery(this).get(0);
                var tagName = tag.tagName.toLowerCase();
                if ((tagName=='input' && tag.type!='hidden') || tagName=='select' || tagName=='textarea') {

                    var ti = jQuery(this).attr('tabindex');

                    if (ti === undefined || ti.length == 0) {
                        jQuery(this).attr('tabindex', startTabIndex);
                    }
                    startTabIndex++;
                }
            });

            jQuery(this).find('input[type=text],input[type=password],input[type=radio],input[type=checkbox],select').keydown(function(event) {
                var keyCode = event.keyCode? event.keyCode: event.which? event.whitch: event.charCode;
                if (event.shiftKey) {
                    if (keyCode == 13) {
                        //ieで、テキストボックス → プルダウンの順になると、テキストボックスの選択状態が無くならないため対応
                        var c = eval(jQuery(this).attr('tabindex'));
                        var current = jQuery('input[tabindex='+c+']');
                        var cval = current.val();
                        current.val('');
                        current.val(cval);

                        var prev = eval(jQuery(this).attr('tabindex'))-1;
                        jQuery('input[tabindex='+prev+'],select[tabindex='+prev+'],textarea[tabindex='+prev+']').focus();
                        jQuery('input[tabindex='+prev+'],select[tabindex='+prev+']').select();
                        return false;
                    }
                }
                if (keyCode == 13) {
                    //ieで、テキストボックス → プルダウンの順になると、テキストボックスの選択状態が無くならないため対応
                    var c = eval(jQuery(this).attr('tabindex'));
                    var current = jQuery('input[tabindex='+c+']');
                    var cval = current.val();
                    current.val('');
                    current.val(cval);

                    var next = eval(jQuery(this).attr('tabindex'))+1;
                    jQuery('input[tabindex='+next+'],select[tabindex='+next+'],textarea[tabindex='+next+']').focus();
                    jQuery('input[tabindex='+next+'],select[tabindex='+next+']').select();
                    return false;
                }
            });
        }
    });
})(jQuery);