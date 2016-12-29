/**
 * Created by songshuang on 16/3/21.
 */
/**
 * js 前端公共工具对象
 */
define(function (require, exports, module) {

    "use strict";

    var $ = require('jquery');

    function showPage() {
        var total = $('#pageDiv').data().total; // 总共多少条数据
        var pageNum = $('#pageResultNo').val();

        pageNum = parseInt(pageNum);
        if (pageNum == "" || pageNum == 0 || pageNum == void 0) {
            pageNum = 0;
        }
        var totalPages = Math.floor(total / 20);
        //if ((total % 20 > 0) && (total % 20 < 5)) {
        if ((total % 20 > 0)) {
            totalPages += 1;
        }
        var str = '<span style="color: #008A03; font-size: 20px">' + pageNum + '</span>&nbsp;';
        if (total == "" || total == 0 || total == void 0) {
            str = "暂无数据"
        }
        /**
         * 首页链接
         * @type {string}
         */
        var firstPage = '';
        if (totalPages > 2 && pageNum != 1) {
            firstPage = '<a class="pageNo" style="font-size: 20px" data-id="1">首页<a/>&nbsp;&nbsp;';
        }
        /**
         * 尾页链接
         */
        var lastPage = '';
        if (totalPages > 2 && pageNum != totalPages) {
            lastPage = '&nbsp;&nbsp;<a class="pageNo" style="font-size: 20px" data-id="'+totalPages+'">尾页<a/>';
        }

        for (var i=1; i<3; i++) {
            if (pageNum-i > 1) {
                str = '<a class="pageNo" style="font-size: 20px" data-id="'+(pageNum - i) +'"><span>' + (pageNum - i) + '</span>&nbsp;</a>' + str;
            }
            if (pageNum + i < totalPages) {
                str = str + '<a class="pageNo" style="font-size: 20px" data-id="'+(pageNum + i) +'"><span>' + (pageNum + i) + '</span>&nbsp;</a>';
            }
        }
        if (pageNum > 1) {
            str = firstPage + '&nbsp;&nbsp;&nbsp;<a id="prevPage" style="font-size: 20px">&lt;&nbsp;<span>上一页</span></a>&nbsp;' + ' ' + str;
        }

        if (pageNum + 3 < totalPages) {
            str = str + ' ...';
        }

        if (pageNum < totalPages) {
            str = str + ' <a class="pageNo" style="font-size: 20px" data-id="'+totalPages+'"><span>' + totalPages + '</span></a>&nbsp;<a id="nextPage" style="font-size: 20px">下一页&nbsp;&gt;</a>' + lastPage;
        }

        $('#pageDiv').append(str);

        /**
         * 用于控制是否是表单按钮所触发还是链接按钮触发
         * @type {boolean}
         */
        var TRIGGER_FLAG = false;

        /**
         * 跳转到指定页面
         */
        $(document).on('click', '.pageNo', function () {
            $('#pageResultNo').val();
            var pageNo = $(this).data().id;
            $('#pageResultNo').val(pageNo);
            TRIGGER_FLAG = true;
            $('#J_Search').click();
        });
        /**
         * 跳转到下一页
         */
        $(document).on('click', '#nextPage', function () {
            var pageNo = $('#pageResultNo').val();
            pageNo = parseInt(pageNo) + 1;
            $('#pageResultNo').val(pageNo);
            TRIGGER_FLAG = true;
            $('#J_Search').click();
        });
        /**
         * 跳转到上一页
         */
        $(document).on('click', '#prevPage', function () {
            var pageNo = $('#pageResultNo').val();
            pageNo = parseInt(pageNo) - 1;
            $('#pageResultNo').val(pageNo);$('#pageResultNo').val();
            TRIGGER_FLAG = true;
            $('#J_Search').click();
        });

        $('#J_Search').on('click', function () {
            var pageNo = $('#pageResultNo').val();
            /**
             * 当时表单按钮触发的时候,需要将页码设置为第一页
             */
            if (pageNo == "" || pageNo == 0 || pageNo == void 0 || !TRIGGER_FLAG) {
                $('#pageResultNo').val(1);
            }
        });
    }

    function dateTool(date) {

    }

    function upload(name) {

    }

    function ajaxGet(url, data, cb) {
        var ret = $.ajax({
            url: url,
            type: 'GET',
            data: data,
            timeout: 60003000
        })
            .done(function (data) {
            if (data.success) {
                if (typeof cb === "function") {
                    if (data.hasOwnProperty("result")) {
                        cb(data.result);
                    } else {
                        cb(data);
                    }
                }
            } else {
                if (data.hasOwnProperty('msg') && data.msg.length > 0) {
                    alert(data.msg);
                    window.location.reload();
                } else if (data.hasOwnProperty('message') && data.message.length > 0) {
                    alert(data.message);
                    window.location.reload();
                } else {
                    alert("请求失败!");
                    window.location.reload();
                }
            }
        })
            .error(function (error) {
                if (error.status == 401) {
                    alert("对不起,您没有权限操作");
                } else {
                    alert("网络错误");
                }

        });
    }

    function ajaxPost(url, data, cb) {
        event.preventDefault();
        var ret = $.ajax({
            url: url,
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(data)
        }).
        done(function (data) {
            if (data.success) {
                if (typeof cb == 'function') {
                    cb(data.result);
                }
            } else {
                if (data.hasOwnProperty('msg') && data.msg.length > 0) {
                    alert(data.msg);
                    window.location.reload();
                } else if (data.hasOwnProperty('message') && data.message.length > 0) {
                    alert(data.message);
                    window.location.reload();
                } else {
                    alert("请求失败!");
                    window.location.reload();
                }
            }
        })
        .error(function (error) {
            if (error.status == 401) {
                alert("对不起,您没有权限操作");
            } else {
                alert("网络错误");
                window.location.reload();
            }
        });
    }


    ;function iframeDialog(classArr){
        $.each($(classArr),function (i,className) {
            var id=$(className).data('aid'),
                newClass=id+'Mask';
            if(!$("[data-id='" + id + "']").prev().hasClass('maskWrap')){
                $("[data-id='" + id + "']").before('<div class="maskWrap ' + newClass + '"></div>');
            }
        });
        $(classArr).on('click',function (e) {
            $('body').css('overflow','hidden');
            e.preventDefault();
            $('.loadGif').remove();
            var load="<img src='/images/loading-0.gif' class='loadGif'/>";
            var did=$(this).data('aid'),
                dnewClass=did+'Mask',
                frameSrc=$(this).data('src'),
                iframeObj=$("[data-id='"+did+"']").find('iframe');
            $("[data-id='"+did+"']").find('.modalTitle').after(load);
            iframeObj[0].src=frameSrc;
            iframeObj[0].onload=function(){
                $('.loadGif').remove();
            };
            iframeObj.show();
            $("[data-id='"+did+"']").show().prev('.maskWrap.'+dnewClass+'').show();
        });
        $('.xubox_close').on('click',function(){
            $('body').css('overflow','auto');
            var currentNode=$(this).closest('.iframeModal'),
                maskClass=currentNode.attr('data-id')+'Mask';
            currentNode.hide().prev('.maskWrap.'+maskClass).hide();
            currentNode.find('iframe')[0].src='';
            $('.loadGif').remove();
        });
        $('.maskWrap').on('click',function () {
            $('body').css('overflow','auto');
            var currentMask=$(this);
            currentMask.hide().next('.iframeModal').hide().find('iframe')[0].src='';
            $('.loadGif').remove();
        });
    };

    function getQueryString(name) {
        if (window.location.href.indexOf("?") != window.location.href.lastIndexOf("?"))
            var urls = window.location.href.replace(/\?/g, "&").replace(/^.*?&/, "")
        else
            var urls = window.location.href.replace(/^.*\?/, "");
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = ("?" + urls).substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    var MaiHaoCheTools = {
        dateTool: dateTool,
        showPage: showPage,
        ajaxGet: ajaxGet,
        ajaxPost: ajaxPost,
        iframeDialog:iframeDialog,
        getQueryString:getQueryString
    };

    module.exports = MaiHaoCheTools;
});
