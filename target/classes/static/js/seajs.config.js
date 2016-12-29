/**
 * Created by songshuang on 16/3/18.
 */
seajs.config({
    alias: {
        "jquery": "/js/jquery.min.js",
        "jqueryModel": "/js/jqueryModel.js",
        "jqueryFileUpload": '/js/jquery.fileupload.min.js',
        "jqueryIFrameTransport": '/js/jquery.iframe-transport.min.js',
        "jqueryWidget": '/js/jquery.ui.widget',
        "jqueryChosen": '/js/chosen.jquery.min.js',
        "uploadFile": '/js/uploadFile',
        "MaiHaoCheTools": "/js/MaiHaoCheTools.js",
        "MaiHaoCheEnums": "/js/MaiHaoCheEnums.js",
        "doT":"/js/doT.js",
        "clipboard": "/js/clipboard-seajs.min.js"
    },
    map: [
    //可配置版本号
        ['.css', '.css?v=' + new Date().getTime()],
        ['.js', '.js?v=' + new Date().getTime()]
    ]

});