package com.yunsen.enjoy.http;


import com.yunsen.enjoy.BuildConfig;

/**
 * Created by Administrator on 2018/4/20.
 */

public class URLConstants {
    /**
     * 域名 http://mobile.ddek3.com
     */
    public static final String REALM_URL = BuildConfig.ROOT_URL;

    public static final String SHEAR_URL = BuildConfig.ROOT_URL;

    public static final String REALM_ACCOUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx";

    /**
     * 头部广告
     */
    public static final String HOME_ADV_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=";
    /**
     * 小汽车广告
     */
//    public static final String CAR_ADV_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=13";
    public static final String CAR_ADV_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_top_list_2017";
    /**
     * 帮助中心
     */
//   http://mobile.ddek3.com/tools/mobile_ajax.asmx/
    public static final String HELP_CENTOR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_category_list_2017";

    /**
     * 首页广告
     */
    public static final String HOME_GOODS_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_category_list_2017";
    /**
     * 推广信息
     */
    public static final String SPREAD_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";

    /**
     * 推荐汽车,品牌筛选，高级筛选
     */
    public static final String CAR_BRAND_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_brand";
    /**
     * 其他分类的商家
     */
    public static final String SERVICE_PROVIDE = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany";
    /**
     * 首页的商家
     */
    public static final String GET_DDEK_USER_COMMPANY = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_ddek_user_commpany";

    /* ---买车--**/
    /**
     * 买车
     */
    public static final String BUY_CAR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";

    /*----发现------**/
    /**
     * 头条
     */
    public static final String DISCOVER_FIRST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list";
    /**
     * y用户登录
     */
    public static final String USER_LOGIN_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_login";

    /**
     * 绑定手机号码
     */
    public static final String BOUDLE_PHONE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_oauth_register_0217";
    public static final String HEIGHT_FILTER_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_category_list";
    /**
     * 关于大道网 http://mobile.ddek3.com/goods/conent-54.html
     */
    public static final String ABOUT_URL = "http://mobile.ddek3.com/goods/conent-54.html";

    //    http://mobile.zams.cn/tools/mobile_ajax.asmx/user_oauth_register_0217
    /**
     * 消息通知
     */
    private static String NOTICE_HTML_URL = BuildConfig.ROOT_URL + "/doc/show-15933.html";

    public static String getNoticeHtmlUrl(String id) {
        return NOTICE_HTML_URL.replace("15933", id);
    }

    /**
     * 删除物品
     */
    public static final String DELETE_SHOPPING_CART_GOODS = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/cart_goods_delete";
    /**
     * 汽车详情
     */
    public static String CAR_DETAILS_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_model?id=";

    /**
     * 预约看车https://szlxkg.com/tools/mobile_ajax.asmx/get_order_page_size_list
     */
    public static String APPOINTMENT_MANAGER = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_order_page_size_list";
    /***
     * 用户信息
     */
    public static String PHONE_USER_INFO_URL = URLConstants.REALM_NAME_LL + "/get_user_model?username=";
    /**
     * 保存用户头像
     */
    public static final String SAVE_USER_ICON_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_avatar_save";

    /**
     * 添加收藏
     */
    public static final String ADD_COLLECT_URL = URLConstants.REALM_NAME_LL + "/user_favorite";
    /**
     * favorite_company_exists 是否已关注商家
     */
    public static final String FAVORITE_COMPANY_EXISTS = URLConstants.REALM_NAME_LL + "/favorite_company_exists";
    /**
     * 关注商家
     */
    public static final String FAVORITE_COMPANY_ADD = URLConstants.REALM_NAME_LL + "/favorite_company_add";
    /**
     * 取消关注商家
     */
    public static final String FAVORITE_COMPANY_CHANNEL = URLConstants.REALM_NAME_LL + "/favorite_company_channel";
    /**
     * 删除用户关注
     */
    public static final String DELECT_COLLECT_URL = URLConstants.REALM_NAME_LL + "/user_favorite_delete";
    /**
     * 我的收藏
     */
    public static final String COLLECT_LIST_URL = URLConstants.REALM_NAME_LL + "/get_user_favorite_list";
    /**
     * 商店收藏https://szlxkg.com
     */
    public static final String SHOP_COLLECT_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_company_collection";
    /**
     * 详情商家
     */
    public static final String SERVICE_SHOP_INFO_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany_content?id=";
    /**
     * id: 用户id
     * 是否是商家facilitator
     */
    public static final String IS_FACILITATOR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany_content?id=";

    /**
     * 用户余额
     */
    public static final String ACCOUNT_BALANCE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_payrecord_expenses_sum";
    /**
     * 提现接口withdraw cash
     */
    public static final String WITH_DRAW_CASH_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_expense_list";
    /**
     * 铁杆圈gavelock ring
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_hardcore_list
     */
    public static final String GAVELOCK_RING_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_hardcore_list";
    /**
     * 朋友圈friend
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_friend_list
     */
    public static final String FRIEND_RING_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_friend_list";
    /**
     * 粉丝圈Vermicelli
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_fans_list
     */
    public static final String VERMICELLI_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_fans_list";
    /**
     * 申请商家-
     * 商家订单统计数量
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_commpany_orders_total
     */
    public static final String SERVICE_ODER_NUM_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_commpany_orders_total";
    /**
     * 商家申请提交表单数据
     * https://szlxkg.com/tools/mobile_ajax.asmx/add_user_commpany_2017 user_upgrade_sales
     */
    public static final String APPLY_SERVICE_FORM_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_ddek_user_commpany";
    /**
     * 申请代理商
     */
    public static final String APPLY_PROXY_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_ddek_application_agent";

    /**
     * 预约管理Booking Management
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_order_page_size_list
     */
    public static final String MEET_MANAGEMENT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_order_page_size_list";
    /**
     * 预约看车
     * https://szlxkg.com/tools/mobile_ajax.asmx/add_order_bespeak
     */
    public static final String MEET_CAR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_order_bespeak";
    /**
     * 获取企业信息
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_commpany_content
     * id: 用户id Enterprise information
     */
    public static final String ENTERPISE_INFO_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_commpany_content";
    /**
     * 取得行业类别Obtain industry
     * https://szlxkg.com/tools/mobile_ajax.asmx/getTrade
     */
    public static final String OBTAIN_INDUSTRY_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/getTrade";
    /**
     * 订单统计
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_order_count
     */
    public static final String USER_ORDER_COUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_order_count";
    /**
     * RealmName.REALM_NAME_LL
     * + "/get_order_page_size_list?user_id=" + user_id + ""
     * + "&page_size=1000&page_index=1&strwhere=datatype=1&orderby="
     * 订单列表
     */
    public static final String ORDER_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_order_page_size_list";
    /**
     * 上传图片 post方法 base64=**
     */
    public static final String PULL_IMG_URL = BuildConfig.ROOT_URL + "/tools/upload_ajax.ashx?action=Base64File";
    /**
     * 服务项目列表
     */
    public static final String SERVICE_PROJECT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_service_child_list?parent_id=0&strwhere=";
    /**
     * 行业列表
     */
    public static final String TRADE_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_trade_list?parent_id=0";
    /**
     * 分类的商业列表 ?parent_id=0
     */
    public static final String TRADE_TYPE_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_trade_list";
    /**
     * 申请买车
     */
    public static final String APPLY_BUY_CAR_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/submit_user_apply_purchase";
    /**
     * 搜索
     */
    public static final String SEARCH_KEY_WORK_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_search_list";
    /**
     * 发现页面的广告图
     */
//    public static final String DISCOVER_BANNER_URL = BuildConfig.ROOT_URL + "/toolS/mobile_ajax.asmx/get_article_top_list";
    public static final String DISCOVER_BANNER_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_adbanner_list?advert_id=12";

    /**
     * 积分兑换 数据接口
     */
//    public static final String INTEGRAL_CHANGE_URL = BuildConfig.ROOT_URL + "/toolS/mobile_ajax.asmx/get_article_top_list_2017?channel_name=point&top=5&strwhere=";
    public static final String INTEGRAL_CHANGE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_top_list_2017";
    /**
     * 商品部件
     */
    public static final String GOODS_PARTS_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_top_list_2017?channel_name=mall&top=4&strwhere=";

    /**
     * 获取分类列表
     */
    public static final String GOODS_CLASSIFY_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_category_child_list";
    /**
     * 获取更多的商品 带分页
     */
    public static final String GOODS_MORE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_article_page_size_list_2018";
    /**
     * 商品加入购物单
     */
    public static final String ADD_SHOPPING_BUY = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_shopping_buy ";
    /**
     * 获取用户默认地址
     */
    public static final String DEFAULT_ADDRESS_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_shopping_address_default";
    /**
     * 绑定银行卡
     * https://szlxkg.com/tools/mobile_ajax.asmx/add_user_bank
     */
    public static final String BIND_BANK_CARD_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_user_bank";
    /**
     * 获取绑定银行卡的列表
     * https://szlxkg.com/tools/mobile_ajax.asmx/get_user_bank
     */
    public static final String GET_BIND_BACK_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_bank";
    /**
     * 申请提现
     * https://szlxkg.com/tools/mobile_ajax.asmx/user_apply_withdraw
     */
    public static final String APPLY_WALLET_CASH_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_apply_withdraw";
    /**
     * 是否已经收藏
     * http://szlxkg.com/tools/mobile_ajax.asmx/user_is_favorite
     */
    public static final String GOODS_HAS_COLLECT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_is_favorite";
    /**
     * http://szlxkg.com/tools/mobile_ajax.asmx/user_favorite_cancel
     * 取消收藏
     */
    public static final String CANCEL_GOODS_COLLECT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_favorite_cancel";
    /**
     * 获取token
     */
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取apk版本信息
     */
    public static final String GET_APK_VERSION = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_apk_version?browser=android";

    /**
     * 微信登录
     */
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 消息通知
     */
    public static final String NOTICE_GET_TOKEN_URL = "https://ju918.com/tools/client_ajax.asmx/get_token";
    /**
     * 更新信息
     */
    public static final String NOTICE_UP_USER_URL = "https://ju918.com/tools/client_ajax.asmx/user_refresh?access_token=";
    /**
     * 修改邮箱
     */
    public static final String CHANGE_EMIL_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_update_field";
    /**
     * 修改QQ
     */
    public static final String CHANGE_ONLINE_QQ_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_update_field";
    /**
     * 支付宝支付 签名
     * http://szlxkg.com/tools/mobile_ajax.asmx/payment_sign?
     */
    public static final String ALIPAY_SIGN_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/payment_sign";
    /**
     * 微信支付 签名
     * http://szlxkg.com/tools/mobile_ajax.asmx/payment_sign
     */
    public static final String WX_PAY_SIGN_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/payment_sign";
    /**
     * http://mobile.gaiachn.com/tools/mobile_ajax.asmx/user_info_authentication
     * 实名认证
     */
    public static final String USER_CERTIFICATION = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_info_authentication";
    /**
     * 直推数量
     */
    public static final String ACHIEVEMENT_ACCOUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_straight_push_count";
    /**
     * get_straight_push_list
     * 直推明细
     */
    public static final String ACHIEVEMENT_CONTENT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_straight_push_list";
    /**
     * 4、盖亚我的业绩团队统计：get_team_count
     */
    public static final String TEAM_ACHI_COUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_team_count";
    /**
     * 5、盖亚我的业绩团队明细：get_team_list
     */
    public static final String TEAM_ACHI_CONTENT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_team_list";
    /**
     * 6、盖亚我的业绩代理统计：get_agent_count
     */
    public static final String AGENT_ACHI_COUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_agent_count";
    /**
     * 7、盖亚我的业绩代理明细：get_agent_list
     */
    public static final String AGENT_ACHI_CONTENT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_agent_list";
    /**
     * 盖亚我的业绩直推/团队/代理（累计收益）统计：get_profit_count
     */
    public static final String PROFIT_COUNT_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_profit_count";
    /**
     * 统计本月盈收和累计收益：get_payment_amount_sum
     */
    public static final String PAYMENT_AMOUNT_SUM_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_payment_amount_sum";
    /**
     * 本月盈收明细：get_user_amount_list
     */
    public static final String USER_AMOUNT_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_amount_list";
    /**
     * 发送手机短信验证码（第三方授权）
     */
    public static final String USER_OAUTH_SMSCODE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_oauth_smscode";
    /**
     * http://mobile.gaiachn.com/tools/mobile_ajax.asmx/post_user_bankcard
     * 添加银行卡
     */
    public static final String POST_USER_BANKCARD_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/post_user_bankcard";
    /**
     * http://mobile.gaiachn.com/tools/mobile_ajax.asmx/add_amount_recharge
     * 充值
     */
    public static final String ADD_AMOUNT_RECHARGE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_amount_recharge";
    /**
     * http://mobile.gaiachn.com/tools/mobile_ajax.asmx/payment_balance
     * 消费券支付
     */
    public static final String PAYMENT_BALANCE_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/payment_balance";
    /**
     * 消费券支付http://mobile.gaiachn.com/api/payment/balance/index.aspx
     */
    public static final String PAYMENT_BALANCE_URL_2 = BuildConfig.ROOT_URL + "/api/payment/balance/index.aspx";


    /**
     * user_update_sales 升级会员
     */
    public static final String USER_UPDATE_SALES_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_update_sales";
    /**
     * get_user_message_list 获取消息列表
     */
    public static final String USER_MESSAGE_LIST_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_message_list";

    /**
     * 更改支付方式
     */
    public static final String EDIT_ORDERS_INFO_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/edit_orders_info";
    /**
     * user_apply_withdraw_log 提现记录
     */
    public static final String USER_APPLY_WITHDRAW_LOG_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/user_apply_withdraw_log";
    /**
     * 盖亚获取提现信息
     */
    public static final String GET_PAYRECORD_URL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_payrecord_list";

    /**
     * 交换产品的接口
     */
    public static final String CHANGE_GOODS_LIST = BuildConfig.ROOT_URL + "/toolS/mobile_ajax.asmx/get_article_page_size_list_2018";
    /**
     * 到店支付
     */
    public static final String ADD_SHOPPING_BESPEAK = BuildConfig.ROOT_URL + "/toolS/mobile_ajax.asmx/add_shopping_bespeak";
    /**
     * 获取购物车列表
     */
    public static final String MY_SHOPPING_CART_LIST = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_shopping_cart";
    /**
     * http://mobile.zams.cn/tools/mobile_ajax.asmx/cart_goods_update
     * 更新物品数量
     */
    public static final String UP_SHOPPING_CART_GOODS = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/cart_goods_update";
    /**
     * 多个商品加入购物单
     */
    public static final String ADD_SHOPPING_BUYS = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_shopping_buys";
    /**
     * 收藏的商家列表
     */
    public static final String GET_FAVORITE_COMPANY_LIST = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_favorite_company_list";
    /**
     * 提交商家投诉
     */
    public static final String ADD_USER_COMPLAINT = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/add_user_complaint";
    /**
     * 获取用户投诉列表
     */
    public static final String GET_USER_COMPLAINT_LIST = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_user_complaint_list";
    /**
     * 撤销投诉
     */
    public static final String EDIT_USER_COMPLAINT = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/edit_user_complaint";
    /**
     * 查看我的账单
     */
    public static final String GET_DDEK_USER_PAYRECORD_LIST = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx/get_ddek_user_payrecord_list";


    public static final String REALM_NAME_WEB = BuildConfig.ROOT_URL;
    public static final String REALM_NAME_HTTP = BuildConfig.ROOT_URL;
    public static final String REALM_NAME_LL = BuildConfig.ROOT_URL + "/tools/mobile_ajax.asmx";
}
