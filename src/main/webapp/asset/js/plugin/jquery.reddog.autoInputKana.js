/**
 * reddog jQuery autoInputKanaプラグイン
 *
 * ◆概要
 *   ひらがな入力をすると同時に、指定したフィールドへ半角ｶﾅ入力する機能。
 *   半角ｶﾅは全銀協フォーマットにしたがって小文字「ャュョ」などは「ﾔﾕﾖ」に変換する
 *
 * @selector textbox要素
 * @param kanaId
 *        半角ｶﾅを入力するtextboxのId。必須
 * @param nameToggleFlag
 *        かな自動補完チェックボックスの表示。省略可 [ 初期値: true ]
 *        true:表示する、false:表示しない
 *
 * 使用例）
 *   // html ------------------------------------------
 *      氏名： <html:text property="fullName" styleId="txtFullName" /><br />
 *
 *      氏名(ｶﾅ)： <html:text property="kana" styleId="txtKana" />
 *
 *   // Js --------------------------------------------
 *     // 検索リンク設定
 *     $('#txtFullName').autoInputKana({
 *         kanaId: 'txtKana'
 *     });
 */
;(function($) {

    $.fn.autoInputKana = function(config) {

        // クリックされた要素のId
        var p_selector_id = $(this).attr('id');

        // ------------------------------------------------
        // 必須引数チェック
        // ------------------------------------------------
        if (config == null) {
            alert('【開発者向けエラー】\n・kanaIdを設定してください\n\n対象の要素ID：'+ p_selector_id);
            return;
        }
        var msg = '';
        if (config.kanaId == null) {
            msg += '・kanaIdを設定してください\n';
        }
        if (msg != '') {
            alert('【開発者向けエラー】\n' + msg + '\n対象の要素ID：' + p_selector_id);
            return;
        }

        // ------------------------------------------------
        // 省略可能引数の初期値
        // ------------------------------------------------
        var defaults={
                nameToggleFlag: true
            }
        var options = $.extend(defaults, config);

        // ------------------------------------------------
        // 設定
        // ------------------------------------------------
        var nameField = '#'+ p_selector_id;  //名前のID
        var kanaField = '#'+ options.kanaId; //カナのID

        var nameToggleFlag = options.nameToggleFlag; //かな自動補完フラグ
        var nameToggleField = nameField+ '_toggle';  //かな自動補完のID
        var nameToggleLable = 'かな自動補完';

        var baseVal = "";
        var beforeVal = "";
        var timer = false;

        // ------------------------------------------------
        // かな自動補完 処理
        // ------------------------------------------------

        //かな自動補完チェックボックス作成
        if (nameToggleFlag) {
            var label = $('<label />');
            var checkbox = $('<input />').attr({ type: 'checkbox', id: nameToggleField.replace('#', ''), checked: true });
            label.append(checkbox)
                 .append(nameToggleLable);
            $(nameField).after(label);
        }

        _loopTimer();

        $(kanaField).keyup(function () {
            _setKana();
        });

        $(nameToggleField).change(function () {

            if ($(this).prop('checked')) {
                timer = setTimeout(function() { _loopTimer(); }, 30);

            } else {
                clearTimeout(timer);
            }
        });

        // ▽ private -------------------------------------------------------------------------------------

        function _loopTimer() {

            _setKana();

            if ($(nameToggleField).prop('checked')) {
                timer = setTimeout(function() { _loopTimer(); }, 30);

            } else {
                clearTimeout(timer);
            }
        }

        /**
         * 文字変換処理
         */
        function _setKana() {
            var newVal = $(nameField).val();
            if (baseVal == newVal || newVal === undefined) {
                return;
            }
            if (newVal == '') {
                $(kanaField).val('');
                baseVal = '';
                return;
            }
            var addVal = newVal;
            for (var i = baseVal.length; i >= 0; i--) {
                if (newVal.substr(0, i) == baseVal.substr(0, i)) {
                    addVal = newVal.substr(i);
                    break;
                }
            }
            baseVal = newVal;
            if (addVal.match(/[^ 　ぁあ-んァー]/)) {
                return;
            }
            var flg = 0;
            if (addVal.match(/^[あ-ん]$/)) {
                if (beforeVal == addVal) flg = 1;
                beforeVal = addVal;
            }

            addVal = _convHankakuKana(addVal);

            var q = $(kanaField).val().slice(-1 * addVal.length);
            if (flg == 0 && q == addVal) return;
            $(kanaField).val($(kanaField).val() + addVal);
        }

        /**
         * 半角ｶﾅ変換
         */
        function _convHankakuKana(val) {
            var retStr = '';
            for (i = 0; i < val.length; i++) {
                var oneChar = val.charAt(i);
                oneChar = KATAKANA_FULL2HALF[oneChar] || oneChar;
                retStr += oneChar;
            }
            return retStr;
        }

        return this;
    }

    /**
     * 半角ｶﾅ定義
     */
    var KATAKANA_FULL2HALF = {
        'あ': 'ｱ',
        'い': 'ｲ',
        'う': 'ｳ',
        'え': 'ｴ',
        'お': 'ｵ',

        'か': 'ｶ',
        'き': 'ｷ',
        'く': 'ｸ',
        'け': 'ｹ',
        'こ': 'ｺ',

        'さ': 'ｻ',
        'し': 'ｼ',
        'す': 'ｽ',
        'せ': 'ｾ',
        'そ': 'ｿ',

        'た': 'ﾀ',
        'ち': 'ﾁ',
        'つ': 'ﾂ',
        'て': 'ﾃ',
        'と': 'ﾄ',

        'な': 'ﾅ',
        'に': 'ﾆ',
        'ぬ': 'ﾇ',
        'ね': 'ﾈ',
        'の': 'ﾉ',

        'は': 'ﾊ',
        'ひ': 'ﾋ',
        'ふ': 'ﾌ',
        'へ': 'ﾍ',
        'ほ': 'ﾎ',

        'ま': 'ﾏ',
        'み': 'ﾐ',
        'む': 'ﾑ',
        'め': 'ﾒ',
        'も': 'ﾓ',

        'や': 'ﾔ',
        'ゆ': 'ﾕ',
        'よ': 'ﾖ',

        'ら': 'ﾗ',
        'り': 'ﾘ',
        'る': 'ﾙ',
        'れ': 'ﾚ',
        'ろ': 'ﾛ',

        'わ': 'ﾜ',
        'を': 'ｦ',

        'ん': 'ﾝ',

        'ぁ': 'ｱ',
        'ぃ': 'ｲ',
        'ぅ': 'ｳ',
        'ぇ': 'ｴ',
        'ぉ': 'ｵ',

        'っ': 'ﾂ',
        'ッ': 'ﾂ',

        'ゃ': 'ﾔ',
        'ゅ': 'ﾕ',
        'ょ': 'ﾖ',

        'ヴ': 'ｳﾞ',

        'が': 'ｶﾞ',
        'ぎ': 'ｷﾞ',
        'ぐ': 'ｸﾞ',
        'げ': 'ｹﾞ',
        'ご': 'ｺﾞ',

        'ざ': 'ｻﾞ',
        'じ': 'ｼﾞ',
        'ず': 'ｽﾞ',
        'ぜ': 'ｾﾞ',
        'ぞ': 'ｿﾞ',

        'だ': 'ﾀﾞ',
        'ぢ': 'ﾁﾞ',
        'づ': 'ﾂﾞ',
        'で': 'ﾃﾞ',
        'ど': 'ﾄﾞ',

        'ば': 'ﾊﾞ',
        'び': 'ﾋﾞ',
        'ぶ': 'ﾌﾞ',
        'べ': 'ﾍﾞ',
        'ぼ': 'ﾎﾞ',

        'ぱ': 'ﾊﾟ',
        'ぴ': 'ﾋﾟ',
        'ぷ': 'ﾌﾟ',
        'ぺ': 'ﾍﾟ',
        'ぽ': 'ﾎﾟ',

        'ー': '-',
        '　': ' '
    };

})(jQuery);
