/**
 * コンボボックス
 *
 * 備考：
 *   ・jquery.reddog.enterNext.jsを使用する場合は、jquery.reddog.enterNext.jsの後にこのファイルを読み込ませること。
 *   ・jquery.reddog.enterNext.jsが読み込まれている場合は、comboboxにもエンター移動処理を付与する。
 */
$(function() {
    // See http://jqueryui.com/autocomplete/#combobox for more info.
    $.widget('custom.combobox', {

        options: {
            invalid: false,    // 入力された値に合致するデータが見つからなかった場合に表示するメッセージ
            showName: true,    // 名称を表示する
            height: '500px',   // ドロップダウンリストの高さ
            inputAddClass: '', // テキストボックスに追加するクラス名
            inputStyle: {}     // テキストボックスに指定するスタイル
        },

        _create: function() {
            this.wrapper = $('<div>')
                .addClass('custom-combobox')
                .css('white-space', 'nowrap')
                .insertAfter(this.element);
            this.element.hide();
            this._createAutocomplete();
            this._createShowAllButton();
        },

        _createAutocomplete: function() {
            var showName = this.options.showName;
            var height = this.options.height;
            var inputAddClass = this.options.inputAddClass;
            var inputStyle = this.options.inputStyle;
            var select = this.element;
            var selected = select.children(':selected'),
                value = selected.val() ? selected.val() + ' ' + selected.text() : '';

            this.input = $('<input>')
                .appendTo(this.wrapper)
                .val(value)
                .attr('title', '')
                .attr('id', select.attr('id')+ '-input')
                .attr('tabindex', select.attr('tabindex'))
                .addClass('custom-combobox-input')
                .addClass(inputAddClass)
                // Combobox の内容をオートコンプリートのリストする
                .autocomplete({
                    delay: 0,
                    minLength: 0,
                    source: $.proxy(this, '_source'),
                    autoFocus: true,
                    open: function(event, x, y) {
                        // 縦スクロールを表示
                        $('.ui-autocomplete').css({'overflow': 'scroll', 'overflow-x': 'hidden', 'max-height': height});
                    },
                    change: function(event, selected) {

                        // 一度選択した後、値を削除してタブ移動したときに、値が空のままで移動できるようにする
                        if ($(event.target).val() == '') {
                            $(this).data('id', '')
                            $(this).val('');
                            return false;
                        }

                        // 入力された値に該当するoptionを検索
                        var value = select.val();
                        var match = $(select).children('option').toArray().filter(function(element) {
                            return value && value == $(element).val();
                        });
                        // 見つかった場合
                        if (match.length) {
                            // inputへ反映(data-id属性へIDを保持)
                            $(this).data('id', $(match).val());
                            $(this).val($(match).val() + (showName ? ' ' + $(match).text() : ''));
                        } else {
                            $(this).data('id', '');
                            $(this).val('');
                        }
                    },
                    select: function(e, ui) {
                        // jquery.reddog.enterNext.jsが読み込まれている場合は、comboboxにもエンター移動処理を付与する
                        if ( typeof $.fn.enterNext == "function") {
                            if (e.shiftKey) {
                                if (e.keyCode == 9 || e.keyCode == 13) {
                                    var prev = eval(jQuery(this).attr('tabindex'))-1;
                                    jQuery('input[tabindex='+prev+'],select[tabindex='+prev+'],textarea[tabindex='+prev+']').focus();
                                    jQuery('input[tabindex='+prev+'],select[tabindex='+prev+']').select();
                                    return false;
                                }
                            }
                            if (e.keyCode == 9 || e.keyCode == 13) {
                                var next = eval(jQuery(this).attr('tabindex'))+1;
                                jQuery('input[tabindex='+next+'],select[tabindex='+next+'],textarea[tabindex='+next+']').focus();
                                jQuery('input[tabindex='+next+'],select[tabindex='+next+']').select();
                                return false;
                            }
                        }
                    }
                })
                .keydown(function(e) {
                    // jquery.reddog.enterNext.jsが読み込まれている場合は、comboboxのselectでtab移動するため、本来のtab移動はキャンセルする
                    if ( typeof $.fn.enterNext == "function") {
                        if (e.keyCode == 9) {
                            return false;
                        }
                    }
                })
                .tooltip()
                .css('text-overflow', 'ellipsis')
                .css(inputStyle)
                .on('focus', function() {
                    var input = $(this);

                    if (input.data('id')) {
                        input.val(input.data('id'));
                    }

                    input.autocomplete('search', input.data('id') ? input.data('id') : input.val());
                    input.select();
                });

            if (selected.val()) {
                this.input.data('id', selected.val());
            }

            this._on(this.input, {
                autocompleteselect: function(event, ui) {
                    ui.item.option.selected = true;
                    this._trigger('select', event, {
                        item: ui.item.option
                    });
                },
                autocompletechange: '_removeIfInvalid'
            });
        },

        // request に一致するものを返す
        _source: function(request, response) {
            var input = this.input;
            var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), 'i');
            response(this.element.children('option').map(function() {
                var text = $(this).text();
                var value = $(this).val();
                if (value && (!request.term || matcher.test(value) || matcher.test(text)))
                    return {
                        label: text,
                        value: value,
                        option: this
                    };
            }));
            if (!request.term) {
                // 空入力なのでコンボボックスの選択をクリア
                this.element.val('');
            }
        },

        _removeIfInvalid: function(event, ui) {
            // 一覧の項目を選択している場合 ui.item は選択値が入っている
            // その場合は何もしないでリターン
            if (ui.item) {
                return;
            }

            // リスト内を検索(大文字小文字区別しない)
            var value = this.input.val(),
                valueLowerCase = value.toLowerCase(),
                valid = false;
            this.element.children('option').each(function() {
                if ($(this).val().toLowerCase() === valueLowerCase) {
                    this.selected = valid = true;
                    return false;
                }
            });

            // 見つかった場合、何もしないでリターン
            if (valid) {
                return;
            }

            // 以降、リスト内に見つからなかった場合の処理
            if (value.length > 0) {
                if (this.options.invalid) {
                    var message = '見つかりません';
                    this.input.attr('title', message).tooltip('open');
                    this._delay(function() {
                        this.input.tooltip('close').attr('title', '');
                    }, 1500);
                }
            } else {
                this.input.val('');
                // 空入力なのでコンボボックスの選択をクリア
                this.element.val('');
            }
        },

        _destroy: function() {
            this.wrapper.remove();
            this.element.show();
        },

        _createShowAllButton: function() {
            var input = this.input;
            var wasOpen = false;

            $('<a>')
                .attr('tabIndex', -1)
                .appendTo(this.wrapper)
                .mousedown(function() {
                    wasOpen = input.autocomplete('widget').is(':visible');
                })
                .removeClass('ui-widget ui-widget-header ui-widget-content ui-corner-all')
                .addClass('custom-combobox-toggle glyphicon glyphicon-triangle-bottom rd-combobox-arrow')
                .click(function() {
                    input.focus();
                    // すでに一覧表示されていたら閉じる
                    if (wasOpen) {
                        return;
                    }
                });
        }

    });

    // See http://stackoverflow.com/questions/2435964/jqueryui-how-can-i-custom-format-the-autocomplete-plug-in-results for more info.
    // 入力文字列とコンボボックスのリスト内の項目が一致する部分をハイライト
    $.extend($.ui.autocomplete.prototype, {
        _init: function() {
            // ドロップダウンの選択イベントをオーバーライド
            this._on( this.menu.element, {
                menuselect: function( event, ui ) {
                    var item = ui.item.data('ui-autocomplete-item'),
                        previous = this.previous;

                    // only trigger when focus was lost (click on menu)
                    if ( this.element[ 0 ] !== this.document[ 0 ].activeElement ) {
                        this.element.focus();
                        this.previous = previous;
                        // #6109 - ID triggers two focus events and the second
                        // is asynchronous, so we need to reset the previous
                        // term synchronously and asynchronously :-(
                        this._delay(function() {
                            this.previous = previous;
                            this.selectedItem = item;
                        });
                    }

                    if ( false !== this._trigger( 'select', event, { item: item } ) ) {
                        this._value( item.value + ' ' + item.label );
                    }
                    // reset the term after the select event
                    // this allows custom select handling to work properly
                    this.term = item.value;

                    this.close( event );
                    this.selectedItem = item;

                    this.element.blur();
                }
            });
        },
        _renderItem: function(ul, item) {
            // 入力値に該当する箇所をハイライト
            var re = new RegExp(this.term);
            var value = item.value.replace(re, '<span style="font-weight: bold; color: #00008b;">' + this.term + '</span>');
            var text = item.label.replace(re, '<span style="font-weight: bold; color: #00008b;">' + this.term + '</span>');
            $(ul).css('width', '100%');
            // ハイライト後の要素を返す
            var li = $('<li></li>')
                .data('item.autocomplete', item)
                .css('width', '100%');
            li.append($('<a>').append(value).append($('<label>').append('&nbsp;'+ text)));
            return li.appendTo(ul);
        }
    });

});