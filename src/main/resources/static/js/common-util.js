/**
 * Created by wenan<wenan@maihaoche.com>
 *  创建者: 文安
 *  说明: 本文件包含一些常用的js功能
 */


/**
 * 表单提交的时候，防止重复提交。
 * 使用方法:
 * 1. 确保页面引入了 common-util.js
 * 2. 为<form> 标签添加class, class="J_MhcForm"
 * 3. 为提交按钮添加class, class="J_MhcSubmit"
 */
function bindSubmit(){
  $(document).ready(function(e){

    $('.J_MhcForm').on('submit', function(e){
      var self = $(this),
        submit_btn = self.find('.J_MhcSubmit');

      if(submit_btn.attr('data-lock')){
        return false;
      }

      submit_btn.attr('data-lock', true).attr('data-original', submit_btn.html()).html('正在提交...');

    })

  })
}

bindSubmit();



function renderUploadDom(){
  // J_UploadWrap
  window.upload_count = window.upload_count || 0;

  $('.J_UploadWrap').each(function(index, item){

    window.upload_count++;
    var attr_name = $(item).attr('data-attr-name');

    var html = '';
    html = '<input type="hidden" name="'+ attr_name +'" value="">' +
      '<div class="J_Preview">';

    var imgs = $(item).attr('data-imgs').split(',');


    for(var i=0; i<imgs.length; i++){
      if(imgs[i].replace(/\s/ig, '') != ''){
        html += '<div class="preview">' +
          '<a target="_blank" href="'+imgs[i]+'"> <img width="200" height="200" src="'+imgs[i]+'" alt=""/></a>' +
          '<input type="hidden" name="'+attr_name+'" value="'+imgs[i]+'">' +
          '<a class="del">删除</a>' +
          '</div>'
      }
    }

    html +='</div>' +
      '<div id="uploader_'+ window.upload_count +'">' +
      '<p>Your browser doesn\'t have Flash, Silverlight or HTML5 support.</p>' +
      '</div>' +
      '<p>每张图片大小不超过5M，支持JPG、PNG格式</p>' +
      '<span class="Form-error"></span>';

    $(item).html(html).removeClass('J_UploadWrap');
    initUpload(attr_name);

  })
}

function initUpload(upload_name){
  (function(upload_name){

    var base_config = {
      runtimes: 'html5,flash,silverlight,html4',
      url: "/common/uploadImage.json",

      max_file_size: '10mb',

      chunk_size: '5mb',

      filters: [
        {title: "Image files", extensions: "jpg,png,jpeg,gif"}
        /*{title : "Zip files", extensions : "zip,avi"}*/
      ],

      rename: false,

      sortable: true,

      dragdrop: true,

      views: {
        list: true,
        thumbs: true, // Show thumbs
        active: 'thumbs'
      },

      flash_swf_url: '/assets/Moxie.swf',

      silverlight_xap_url: '/assets/Moxie.xap'
    }

    var core_node = $("#uploader_" + window.upload_count);

    var config = $.extend(true, {}, base_config, {
      init: {
        FileUploaded: function (uploader, file, result) {
          /*JSON.parse(result.response).url;*/
          var a = result.response;
          /*alert("aa"+${name});*/
          var h = '<div class="preview">' +
            '<a target="_blank" href="'+  JSON.parse(a).url +'" >' +
            '<img width="200" height="200" alt="" src="' + JSON.parse(a).url + '" /> </a>' +
            '<input type="hidden" name="'+upload_name+'" value="'+ JSON.parse(a).url +'">' +
            '<a class="del">删除</a>' +
            '</div>';

          core_node.siblings('.J_Preview').append(h);
          core_node.siblings('.J_Preview').find('.del').on('click',function (e) {

            e.preventDefault();
            var self = $(this);
            self.parents('.preview').remove();
          });

        }
      }
    });

    core_node.siblings('.J_Preview').find('.del').on('click',function (e) {

      e.preventDefault();
      var self = $(this);
      self.parents('.preview').remove();
    });

    core_node.plupload(config);
  })(upload_name);
}