package com.zyq.constants;

/**
 * Created by TK on 2016/12/5.
 */
public abstract class WebConstants {
    public static final String REDIRECT_URL_KEY="redirectUrl" ; //重定向跳转链接

    public static final String COMMON_RESULT_PAGE="result";
    public static final String LOGIN_URL = "login";  // 登陆页面
    public static final String INDEX_URL = "center"; // 首页

    public static final String REDIRECT_PREFIX ="redirect:" ; // 外部重定向前缀

    public static final String REDIRECT_FORWARD ="forward:" ; //  内部重定向前缀


    public static final String RESULT_CODE_KEY ="code"; // 结果类型

    public static final String ORDER_APPROVE_CONTRACT = "contract"; //订单宝合同审批

    public static final String ORDER_APPROVE_DAIGOU = "daigou"; //订单宝代购协议审批

    public static final String ADMIN_NO_ROLE = "noRole"; // 用户没有相应的权限角色

    public static final String PUSH_RESULT_PAGE="pushResult";


    public static final String RESULT_MSG_KEY = "retMsg";

    public static final String SUCCESS = "success";

    public static final String UNAUTHORIZED = "unauthorized";   // 没有权限的页面
}
