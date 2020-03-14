/**
 * reddog func jQueryプラグイン 共通関数
 *
 * 基本的な使用例）
 *   $('#target').reddog([関数名], [引数]);
 *   $('#target').reddog('foo', { width: 300, height: 500 });
 */
;(function($) {

    // 全メソッドをここに定義
    var methods = {
        /**
         * 初回ロード時に対象のアンカーまでスクロールする
         *
         * @selector スクロール先の要素
         *
         * 使用例）
         *   $('.target-anchor').reddog('initMoveAnchor');
         */
        initMoveAnchor : function() {
            var target = $(this);
            if (target.length == 0) {
                return;
            }
            var targetOffset = target.offset().top;
            $('html,body').delay(200).animate({scrollTop: targetOffset}, "slow");
        },
        /**
         * 初回ロード時にフォーカスを当てる
         *
         * @selector 入力フォーム系の要素
         *
         * 使用例）
         *   $('.target-focus').reddog('initTargetFocus');
         */
        /* initへ移動
        initTargetFocus : function() {
            var target = $(this);
            target.focus();
            if (target.get(0).tagName == 'INPUT') {
                // テキストの中身を入れ替えることによって、テキストボックス等のカーソルを末尾にする
                target.val( target.val() );
            }
        },
        */
        /**
         * 初回ロード時に、要素リスト内の該当行を色づけする
         * 指定要素は必ず比較対象をhiddenで持っていることが前提
         *
         * @selector tr要素
         * @param listItemHidden 指定要素内にあるhiddenの指定名称
         * @param itemHidden 比較対象のhiddenの指定名称
         *
         * 使用例）
         *   $('tr.select-row').reddog('initListSelected', {
         *     listItemHidden: '.hid-list-num',
         *     itemHidden: '.hid-num'
         *   });
         */
        initListSelected : function(config) {
            var defaults={
                    listItemHidden: '',
                    itemHidden: ''
                }

            var options = $.extend(defaults, config);

            $(this).each(function() {
                if ($(options.itemHidden) == null) {
                    return this;
                }
                if ($(this).find(options.listItemHidden).val() == $(options.itemHidden).val()) {
                    $(this).addClass('reddog-layout-list-selected');
                }
            });

            return this;
        },
        /**
         * 初回ロード時に、対象の要素をソートできるようにする
         * 該当要素の子要素をドラッグ＆ドロップでソートさせる
         * 子要素が持っているhiddenを1から連番で振り直してくれる
         *
         * @selector tbody要素
         *
         * 使用例）
         *   $("table.reddog-layout-list tbody").reddog('initSortable');
         */
        initSortable : function() {

            if ($(this).length == 0 || $(this).get(0).tagName != 'TBODY') {
                return;
            }

            // テーブルの幅を維持する
            var _fixHelper = function(e, ui) {
                ui.children().each(function() {
                    $(this).width($(this).width());
                });
                return ui;
            };

            // sortable本体
            $(this).sortable({
                helper: _fixHelper,
                opacity: 0.5,
                placeholder: 'reddog-sort-list-hilight',
                stop: function( event, ui ) {
                    ui.item.css('background-color', '#afeeee');
                    ui.item.animate({ backgroundColor: '#ffffff'}, 800);

                    var i = 1;
                    $(this).find('tr').each(function() {
                       $(this).find('input:hidden').val(i);
                       i++;
                    });
                },
                create: function(event, ui) {
                    $(this).find('tr').each(function() {
                        // クリック感を付ける
                        _feelPressForTR($(this));
                    });
                }
            }).disableSelection();

            return this;
        },
        /**
         * 初回ロード時に、対象の要素内を非表示にし、「エラーが○件あります」とリンクを表示する
         * リンクをクリックすると、中身が表示される
         *
         * ※あらかじめstrutsから返される文字列をdivタグで囲っておくこと。
         * ※javaのHandsonTableBuilderクラス内で使用しているので、変更する際は注意。
         *
         * @selector div要素
         * @patten エラー件数にカウントする文字列パターン(constファイルに定義) [初期値: RD_ERRSUM_PATTERN_BR]
         *
         * 使用例）
         *   // jsp (jQueryが読み込まれる前に表示しないようdisplay:noneを入れておく)
         *   <div id="error-summary" style="display: none;">
         *       <html:errors property="dummy" />
         *   </div>
         *
         *   // js
         *   $('#error-summary').reddog('initErrorSummary');
         */
        initErrorSummary : function (config) {
            var defaults={
                    pattern: RD_ERRSUM_PATTERN_BR
                }

            var options = $.extend(defaults, config);

            var self = $(this);
            self.css('display', 'none');

            if (self.html() != "") {

                // エラー件数を取得
                var brMatch = self.html().match(options.pattern);
                var brCount = brMatch.length;

                // 「エラー詳細を表示する」リンクの作成
                self.before('<a href="#" id="error-display-link">エラーが '+ brCount+ '件あります</a>');
                $('#error-display-link').click(function() {
                    if (self.css('display') == 'block') {
                        self.css('display', 'none');
                    } else {
                        self.css('display', '');
                    }
                    return false;
                });
            }
        },
        /**
         * tr要素クリック時に要素の中にある最初のリンクをクリックし、
         * マウスにクリック感を付ける
         * また、画面遷移が完了するまで画面操作を無効にする
         *
         * @selector table tr要素にリンクのある要素
         *
         * 使用例）
         *   $('tr.select-row').reddog('clickFirstLinkForTR');
         */
        clickFirstLinkForTR : function() {

            if ($(this).length == 0 || $(this).get(0).tagName != 'TR') {
                return;
            }

            // 中にある最初のリンクをクリック
            $(this).click(function() {

            	// 画面ロックする
            	_lockScreen();

            	// 画面遷移
                window.location = $(this).find('a:first').attr('href');
            });

            // クリック感を付ける
            _feelPressForTR($(this));

            return this;
        },
        /**
         * tr要素クリック時に要素の中にある最初のチェックボックスをクリックし、値を反転させる
         * また、shiftキーを押しながら操作すると、範囲チェックをする。
         *
         * @selector table tr要素にチェックボックスのある要素
         *
         * 使用例）
         *   $('tr.select-row').reddog('clickFirstCheckboxForTR');
         */
        clickFirstCheckboxForTR : function() {

            if ($(this).length == 0 || $(this).get(0).tagName != 'TR') {
                return;
            }

            // 表中の文字を選択不可にする
            $(this).disableSelection();

            var checkboxList = $(this).find(':checkbox');

            // tr クリックイベント
            $(this).click(function(event) {

                // trの中にある最初のチェックボックス値を反転
            	var checkbox = $(this).find(':checkbox:first');
            	checkbox.prop('checked', function(index, oldValue) { return !oldValue; });

            	// shiftキーを押しながらの範囲選択
                _shiftRangeSelect(event, checkbox, checkboxList);
            });

            // 直接チェックボタンがクリックされたときのイベント
            $(this).find(':checkbox:first').click(function(event) {
                event.stopPropagation();

            	// shiftキーを押しながらの範囲選択
            	var checkbox = $(this);
                _shiftRangeSelect(event, checkbox, checkboxList);
            });

            // 直接ラベルがクリックされたときのイベント
            $(this).find('label').click(function(event) {
                event.preventDefault();
            });

            // クリック感を付ける
            _feelPressForTR($(this));

            return this;
        },
        /**
         * tr要素クリック時に要素の中にある最初のラジオボタンをクリックする
         *
         * @selector table tr要素にラジオボタンのある要素
         *
         * 使用例）
         *   $('tr.select-row').reddog('clickFirstRadioForTR');
         */
        clickFirstRadioForTR : function() {

            if ($(this).length == 0 || $(this).get(0).tagName != 'TR') {
                return;
            }

            // 中にある最初のラジオボタンをクリック
            $(this).click(function() {

            	var radio = $(this).find(':radio:first');
            	radio.prop('checked', true);
            });

            return this;
        },
        /**
         * 該当要素がhoverしたときの色を定義する
         *
         * @selector tr要素
         * @param argClass 背景色を変更したいときに、別のスタイルクラスを割り当てる
         * @param cursor マウスポインタを使用 true:する、false:しない [初期値: true]
         *
         * 使用例）
         *   $('tr.select-row').reddog('hoverList');
         *   $('tr.select-row').reddog('hoverList', { argClass: 'reddog-hover-color-sort' });
         */
        hoverList : function(config) {
            var defaults={
                    argClass: 'reddog-hover-color',
                    cursor: true
                }
            var options = $.extend(defaults, config);

            $(this).hover(function() {
                $(this).css('background-color', '');
                $(this).addClass(options.argClass);

                if (options.cursor) {
                    $(this).addClass('reddog-hover-cursor');
                }

            }, function() {
                $(this).removeClass(options.argClass);
                $(this).removeClass('reddog-hover-cursor');
            });

            return this;
        },
        /**
         * テキストをエンターしたときに、指定した要素をクリックする
         *
         * @selector textbox要素
         * @param clickTarget 指定要素文字 #foo, .foo etc...
         *
         * 使用例）
         *   $('#txtTarget').reddog('enterClick', { clickTarget: '#clickTarget' });
         */
        enterClick : function(config) {
            var defaults={
                    clickTarget: ''
                }

            var options = $.extend(defaults, config);

            if ($(this).length == 0
                || $(this).get(0).tagName != 'INPUT'
                || $(this).attr('type') != 'text'
                || options.clickTarget == '') {
                return;
            }

            $(this).keypress(function(e) {
                if (e.keyCode == 13) {
                    $(options.clickTarget).click();
                    return false;
                }
            });
        },
        /**
         * 該当要素をreadonlyにし、背景色を変更する
         *
         * @selector input要素
         * @param readonlyFlg  true: readonlyにする、false: readonlyと背景色を解除する
         *
         * 使用例）
         *   $('input.target').reddog('textReadonly', true);
         */
        textReadonly : function(readonlyFlg) {
            var self = $(this);
            if (self.length == 0 || self.get(0).tagName != 'INPUT' || self.attr('type') != 'text') {
                return;
            }
            if (readonlyFlg) {
                self.prop('readOnly', true);
                self.addClass('reddog-readonly-style');
            } else {
                self.prop('readOnly', false);
                self.removeClass('reddog-readonly-style');
            }
        },
        /**
         * 指定form内すべてのチェックボックスにhiddenを追加し、submit時にチェックボックス値をhiddenへ移す
         *
         * @selector form要素
         *
         * 使用例）
         *   $('form').reddog('addCheckboxHidden');
         */
        addCheckboxHidden : function() {
            var self = $(this);
            if (self.length == 0 || self.get(0).tagName != 'FORM') {
                return;
            }

            // 初回ロード時 hidden追加
            self.find('input[type=checkbox]').each(function() {
                var checkbox = $(this);
                var hidden = $('<input/>').attr({ type: 'hidden', name: checkbox.attr('name') });

                // labelで囲まれている場合はlabelの前に追加
                if (checkbox.closest('label').size() > 0) {
                    checkbox.closest('label').before(hidden);
                } else {
                    checkbox.before(hidden);
                }
            });

            // submit時、checkbox値 → hidden値へ移す
            self.submit(function() {

                self.find('input[type=checkbox]').each(function() {
                    var hidden = $(':hidden[name="' + $(this).attr('name')+ '"]');
                    hidden.val( $(this).prop('checked') );
                });
            });
        }
    };

    // private function
    // ------------------------------------------------------------------------------
    /**
     * tr要素配下のtdにクリック感を付ける
     *
     * @param obj_tr tr要素
     */
    var _feelPressForTR = function (obj_tr) {

        if (obj_tr.length == 0 || obj_tr.get(0).tagName != 'TR') {
            return;
        }

        obj_tr.mousedown(function() {

            var top    = parseInt($(this).find('td').css('padding-top'), 10);
            var right  = parseInt($(this).find('td').css('padding-right'), 10);
            var bottom = parseInt($(this).find('td').css('padding-bottom'), 10);
            var left   = parseInt($(this).find('td').css('padding-left'), 10);

            $(this).find('td').css({
                paddingTop: (top+1),
                paddingRight: (right-1),
                paddingBottom: (bottom-1),
                paddingLeft: (left+1) });
        }
        ).mouseup(function() {

            var top    = parseInt($(this).find('td').css('padding-top'), 10);
            var right  = parseInt($(this).find('td').css('padding-right'), 10);
            var bottom = parseInt($(this).find('td').css('padding-bottom'), 10);
            var left   = parseInt($(this).find('td').css('padding-left'), 10);

            $(this).find('td').css({
                paddingTop: (top-1),
                paddingRight: (right+1),
                paddingBottom: (bottom+1),
                paddingLeft: (left-1) });
        });
    }

    /**
     * Shiftキーを押しながらクリックで、範囲指定させる
     */
    var beforeIndex = null;
    var beforeChecked = false;
    var _shiftRangeSelect = function (event, checkbox, checkboxList) {

    	var currentIndex = checkboxList.index(checkbox);

        if (beforeIndex !== null && event.shiftKey) {

            if (beforeIndex < currentIndex) {
                for (var i = beforeIndex; i <= currentIndex; i++) {
                    checkboxList.eq(i).prop('checked', beforeChecked);
                }

            } else if (beforeIndex > currentIndex) {
                for (var i = beforeIndex; i >= currentIndex; i--) {
                    checkboxList.eq(i).prop('checked', beforeChecked);
                }
            }

            beforeIndex = null;

        } else {
        	beforeIndex = currentIndex;
        	beforeChecked = checkbox.prop('checked');
        }

    }

    // 画面ロックする
    var _lockScreen = function () {

        // 現在画面を覆い隠すためのdivタグを作成する
        var divTag = $('<div />').attr("id", 'reddog-lockId');

        // スタイルを設定
        divTag.css("z-index", "999")
              .css("position", "fixed")
              .css("top", "0px")
              .css("left", "0px")
              .css("right", "0px")
              .css("bottom", "0px")
              .css("background-color", "gray")
              .css("opacity", "0.2");

        // bodyタグに作成したdivタグを追加
        $('body').append(divTag);
    }

    // 以下変更禁止
    // ------------------------------------------------------------------------------
    var funcname = 'reddog';

    $.fn[funcname] = function(method) {
        if ( methods[method] ) {
            return methods[ method ]
                .apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.' + funcname );
            return this;
        }
    };
})(jQuery);
