/**
 * reddog jQuery functionKeyEventプラグイン
 *
 * ◆概要
 *   ファンクションキーイベントを作成する。処理内容は、各画面で定義する。
 *
 * @selector select要素
 * @param fkname
 *        ファンクションキー定数を指定する。必須
 * @param event
 *        キーを押下したときの実行内容を書く。必須
 *
 * 使用例）
 *     // F9キーを押すと「F9を押しました。」とダイアログを表示する
 *     $(document).functionKeyEvent({
 *         fkname: RD_FK9,
 *         event: function() {
 *             alert('F9を押しました。');
 *         }
 *     });
 */

// ファンクションキー定数
var RD_FK1 = 'F1';
var RD_FK2 = 'F2';
var RD_FK3 = 'F3';
var RD_FK4 = 'F4';
var RD_FK5 = 'F5';
var RD_FK6 = 'F6';
var RD_FK7 = 'F7';
var RD_FK8 = 'F8';
var RD_FK9 = 'F9';
var RD_FK10 = 'F10';
var RD_FK11 = 'F11';
var RD_FK12 = 'F12';

;(function($) {

    $.fn.functionKeyEvent = function(config) {

        // ------------------------------------------------
        // 必須引数チェック
        // ------------------------------------------------
        if (config == null) {
            alert('【開発者向けエラー】\n・fkname, eventを設定してください\n\n対象の要素ID：'+ p_selector_id);
            return;
        }
        var msg = '';
        if (config.fkname == null) {
            msg += '・fknameを設定してください\n';
        }
        if (config.event == null) {
            msg += '・eventを設定してください\n';
        }
        if (msg != '') {
            alert('【開発者向けエラー】\n' + msg + '\n対象の要素ID：' + p_selector_id);
            return;
        }

        // ------------------------------------------------
        // 省略可能引数の初期値
        // ------------------------------------------------
        var defaults={
                // 無し
            }
        var options = $.extend(defaults, config);

        // firefox対応
        $('body').focus();

        // ------------------------------------------------
        // イベント定義
        // ------------------------------------------------
        $(document).keydown(function(e) {

            var cd = parseInt(e.keyCode, 10);

            if (!isNaN(cd) && cd >= 112 && cd <= 123) {

                // F1
                if (options.fkname == RD_FK1 && cd == 112) {
                    options.event.call();
                    return false;
                }
                // F2
                else if (options.fkname == RD_FK2 && cd == 113) {
                    options.event.call();
                    return false;
                }
                // F3
                else if (options.fkname == RD_FK3 && cd == 114) {
                    options.event.call();
                    return false;
                }
                // F4
                else if (options.fkname == RD_FK4 && cd == 115) {
                    options.event.call();
                    return false;
                }
                // F5
                else if (options.fkname == RD_FK5 && cd == 116) {
                    options.event.call();
                    return false;
                }
                // F6
                else if (options.fkname == RD_FK6 && cd == 117) {
                    options.event.call();
                    return false;
                }
                // F7
                else if (options.fkname == RD_FK7 && cd == 118) {
                    options.event.call();
                    return false;
                }
                // F8
                else if (options.fkname == RD_FK8 && cd == 119) {
                    options.event.call();
                    return false;
                }
                // F9
                else if (options.fkname == RD_FK9 && cd == 120) {
                    options.event.call();
                    return false;
                }
                // F10
                else if (options.fkname == RD_FK10 && cd == 121) {
                    options.event.call();
                    return false;
                }
                // F11
                else if (options.fkname == RD_FK11 && cd == 122) {
                    options.event.call();
                    return false;
                }
                // F12
                else if (options.fkname == RD_FK12 && cd == 123) {
                    options.event.call();
                    return false;
                }
            }
        });

        return this;
    }

})(jQuery);
