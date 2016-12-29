/**
 *
 * Created by songshuang on 16/4/17.
 */
define(function(require) {
    "use strict";
    require("jqueryWidget");
    require("jqueryIFrameTransport");
    require("jquery");
    require("jqueryFileUpload");

     $(document).on('click', '#fileupload',function () {
         if ($('#imgUrl') != void 0) {
             $('#imgUrl').val("");
         }
    });

    $('#fileupload').fileupload({
        url: '/common/uploadImage.json',
        dataType: 'json',
        done: function (e, data) {
            if (data.success) {
                alert("长传成功");
            }
            var result = data.result;
            if (result.hasOwnProperty('url')) {
                var url = result.url;
                if ($(this).prev()[0].localName == "img") {
                    $(this).prev().remove();
                }
                $(this).prev('input').val(url);
                $('<img id="showImage" style="height: 30px; width:30px" src="'+url+'"/>').insertBefore($(this));
            }
        },
        error: function (error) {
            alert("图片上传失败");
        }
    });
});