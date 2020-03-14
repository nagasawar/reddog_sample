/**
 * reddog init jQueryプラグイン 初回ロード時、自動的に処理をセット
 *
 * ◆先に取り込んでおく外部ファイル
 *   ・ /asset/package/jquery-ui/css/jquery-ui/jquery-ui.css
 *   ・ /asset/css/reddog.css
 *   ・ /asset/js/jquery.js
 *   ・ /asset/package/jquery-ui/js/jquery-ui.js
 *
 */
$(function() {

    /**
     * jQuery存在チェック関数
     */
    jQuery.fn.exists = function(){
        return Boolean(this.length > 0);
    }

    /**
     * 初回ロード時にフォーカスを当てる
     *   最初にフォーカスを当てたいタグのclassに「rd-initFocus」を指定する
     *   dtAjaxEditor.js内で呼ばれる場合を考慮して関数化しておく
     *     jQuery.fn.initFocus(); で呼び出す
     */
    jQuery.fn.initFocus = function(){
        var target = $('.rd-initFocus');
        if (target.exists()) {
            target.focus();
            if (target.get(0).tagName == 'INPUT') {
                // テキストの中身を一度空にすることで、テキストボックス等のカーソルを末尾にする
                var val = target.val();
                target.val('');
                target.val( val );
            }
        }
    }

    // 初回ロード時の実行
    jQuery.fn.initFocus();

    /**
     * テキストボックスにフォーカスしたとき選択状態にする
     *   dtAjaxEditor.js内で呼ばれる場合を考慮して関数化しておく
     *     jQuery.fn.initSelectText(); で呼び出す
     */
    jQuery.fn.initSelectText = function(){
        var target = $('input[type=text]');
        if (target.exists()) {
            target.focus(function() {
                $(this).select();
            });
        }
    }

    // 初回ロード時の実行
    jQuery.fn.initSelectText();

    /**
     * 文字バイト数を取得する
     * 全角文字は2バイトとする
     */
    String.prototype.bytes2 = function () {
        var r = 0;
        for (var i = 0; i < this.length; i++) {
            var c = this.charCodeAt(i);
            // Shift_JIS: 0x0 ～ 0x80, 0xa0  , 0xa1   ～ 0xdf  , 0xfd   ～ 0xff
            // Unicode  : 0x0 ～ 0x80, 0xf8f0, 0xff61 ～ 0xff9f, 0xf8f1 ～ 0xf8f3
            if ( (c >= 0x0 && c < 0x81) || (c == 0xf8f0) || (c >= 0xff61 && c < 0xffa0) || (c >= 0xf8f1 && c < 0xf8f4)) {
                r += 1;
            } else {
                r += 2;
            }
        }
        return r;
    }

    /**
     * 日付妥当性をチェックする
     * @return エラーなし/あり true/false
     */
    String.prototype.validDate = function () {

        // 日付形式チェック
        if (!this.match(/^[1-9]{1}[0-9]{3}\/[0-9]{1,2}\/[0-9]{1,2}$/)) {
            return false;
        }

        var tmp = this.split('/');
        var y = parseInt(tmp[0], 10);
        var m = parseInt(tmp[1], 10);
        var d = parseInt(tmp[2], 10);

        // 年範囲チェック
        // SQL SEVERで扱える範囲は1753/01/01 ～ 9999/12/31年の間のため、
        // 年が範囲内であるかチェックする。
        if (y < 1753 || 9999 < y) {
            return false;
        }

        // 日付妥当性チェック
        dt = new Date(y, m-1, d);
        return (dt.getFullYear()==y && dt.getMonth()==m-1 && dt.getDate()==d);
    }

    /**
     * 引数で渡された判定式で判定し、エラー時は引数のエラーメッセージを返す
     * エラー時は直近の親タグに対して、TwitterBootstrapの「has-error」クラス属性を追加する
     *
     * @param value     入力値
     * @param fieldName 項目名称
     * @param errMsg    エラーメッセージ
     * @param judgement 判定式
     */
    jQuery.fn.reddogValid = function(config) {
        var defaults={
                value:     '',
                fieldName: '',
                errMsg:    '',
                judgement: ''
            }
        var opt = $.extend(defaults, config);
        var target = $(this);

        // 判定式がない場合は終了
        if (opt.judgement.length == 0) {
            return true;
        }

        // ---------------------------------
        var value = opt.value;
        var cl = target.closest('div, th, td, li, dd');

        $('#'+ opt.fieldName+ '-error-msg').html('');

        if (cl.hasClass('custom-combobox')) {
            // comboboxの場合、もう一つ上の要素を対象とする
            cl.parent('div, th, td, li, dd')
              .removeClass('has-error');
        } else {
            cl.removeClass('has-error');
        }

        // 渡された判定式で判定する
        if ( eval(opt.judgement) ) {
            $('#'+ opt.fieldName+ '-error-msg').html(opt.errMsg);

            if (cl.hasClass('custom-combobox')) {
                // comboboxの場合、もう一つ上の要素を対象とする
                cl.parent('div, th, td, li, dd')
                  .addClass('has-error');
            } else {
                cl.addClass('has-error');
            }

            return false;
        }

        return true;
    }

});
