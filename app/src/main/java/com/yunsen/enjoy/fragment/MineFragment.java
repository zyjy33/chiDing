package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.tauth.Tencent;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.dealer.ApplyServiceActivity;
import com.yunsen.enjoy.activity.dealer.MyFacilitatorActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.common.wsmanager.WsManager;
import com.yunsen.enjoy.http.DataException;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.UserInfo;
import com.yunsen.enjoy.model.event.EventConstants;
import com.yunsen.enjoy.model.event.PullImageEvent;
import com.yunsen.enjoy.model.event.UpUiEvent;
import com.yunsen.enjoy.ui.DialogUtils;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;
import com.yunsen.enjoy.utils.GetImgUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class MineFragment extends BaseFragment {
    private static final String TAG = "MineFragment";
    @Bind(R.id.messageImg)
    ImageView messageImg;
    @Bind(R.id.setting_img)
    ImageView settingImg;
    @Bind(R.id.user_img)
    ImageView userImg;
    @Bind(R.id.balance_tv)
    TextView balanceTv;
    @Bind(R.id.invite_friends)
    TextView inviteFriends;
    @Bind(R.id.gift_other)
    TextView giftOther;

    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.member_id_tv)
    TextView memberIdTv;
    @Bind(R.id.login_layout)
    LinearLayout loginLayout;
    @Bind(R.id.recharge_layout)
    LinearLayout rechargeLayout;
    @Bind(R.id.my_account_layout)
    LinearLayout myAccountLayout;
    @Bind(R.id.my_complaint_layout)
    LinearLayout myComplaintLayout;
    @Bind(R.id.my_collect_layout)
    LinearLayout myCollectLayout;
    @Bind(R.id.my_address_layout)
    LinearLayout myAddressLayout;
    @Bind(R.id.my_order_layout)
    LinearLayout myOrderLayout;
    @Bind(R.id.spread_layout)
    LinearLayout spreadLayout;
    @Bind(R.id.help_layout)
    LinearLayout helpLayout;
    @Bind(R.id.seller_layout)
    LinearLayout sellerLayout;
    @Bind(R.id.proxy_layout)
    LinearLayout proxyLayout;
    @Bind(R.id.about_layout)
    LinearLayout aboutLayout;
    private FragmentActivity context;
    private SharedPreferences mSp;
    private String mGroupId;
    private boolean mIsFacilitator;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        Glide.with(MineFragment.this)
                .load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(getActivity()))
                .into(userImg);
//        Glide.with(MineFragment.this)
//                .load(R.mipmap.login_icon)
//                .transform(new GlideCircleTransform(getActivity()))
//                .into(userIconImg);
    }


    @Override
    protected void initData() {
        mSp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                getUserInfo();
            } else {
                // TODO: 2018/8/1/001
            }
        } else {
            memberIdTv.setVisibility(View.GONE);
            userNameTv.setVisibility(View.GONE);
            loginTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void initListener() {

    }

    /**
     * 判断是否登录和绑定跳转订单页面
     *
     * @param type
     */
    private void orderClick(String type) {
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showOrderActivity(getActivity(), type);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                UIHelper.showOrderActivity(getActivity(), type);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }


    //账户管理
    public void onAccountManagerLayoutClicked() {
        goLoginOrOtherActivity(PersonCenterActivity.class);
    }

    /**
     * 我的界面二级界面跳转
     *
     * @param cls
     */
    private void goLoginOrOtherActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }

    /**
     * 我是服务商的判断跳转
     */
    private void goLoginOrIsFacilitator() {
        Intent intent = null;
        if (!mIsFacilitator) {
            intent = new Intent(getActivity(), ApplyServiceActivity.class);
        } else {
            intent = new Intent(getActivity(), MyFacilitatorActivity.class);
        }
        if (AccountUtils.hasLogin()) {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showBundPhoneActivity(getActivity());
            }
        } else {
            if (AccountUtils.hasBoundPhone()) {
                getActivity().startActivity(intent);
            } else {
                UIHelper.showUserLoginActivity(getActivity());
            }
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpProxy.getUserInfo(AccountUtils.getUserName(), new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                balanceTv.setText(String.valueOf(data.getAmount()));
                memberIdTv.setText("会员号:" + data.getUser_code());
                String nickName = data.getNick_name();
                if (TextUtils.isEmpty(nickName)) {
                    nickName = data.getUser_name();
                }
                userNameTv.setText(nickName);
                memberIdTv.setVisibility(View.VISIBLE);
                userNameTv.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.GONE);
            }

            @Override
            public void onError(Request request, Exception e) {
                if (e instanceof DataException) {
                    ToastUtils.makeTextShort(e.getMessage());
                }
            }
        });


//        String nickname = mSp.getString(SpConstants.NICK_NAME, "");
//        headimgurl = mSp.getString(SpConstants.HEAD_IMG_URL, "");
//        headimgurl2 = mSp.getString(SpConstants.HEAD_IMG_URL_2, "");
//        user_name_phone = mSp.getString(SpConstants.USER_NAME, "");
//        user_id = mSp.getString(SpConstants.USER_ID, "");
//
//        String loginFlag = mSp.getString(SpConstants.LOGIN_FLAG, "");
//        mUserName = mSp.getString(SpConstants.USER_NAME, "");
//        if (TextUtils.isEmpty(mUserName)) {
//            mUserName = nickname;
//        }
//        String userId = mSp.getString(SpConstants.USER_CODE, "");
//        if (!TextUtils.isEmpty(userId)) {
//            userIdTv.setText("ID: " + userId);
//        }
//        String groupName = mSp.getString(SpConstants.GROUP_NAME, "");
//        if (!TextUtils.isEmpty(groupName)) {
//            gradeTv.setText(groupName);
//        }
//        String sex = mSp.getString(SpConstants.SEX, "");
//        if (Constants.GIRL.equals(sex)) {
//            userSexImg.setImageResource(R.mipmap.girl_img);
//        } else if (Constants.BOY.equals(sex)) {
//            userSexImg.setImageResource(R.mipmap.boy_img);
//        } else {
//            userSexImg.setImageResource(R.mipmap.secrecy);
//        }
//
//        if (SpConstants.WEI_XIN.equals(loginFlag) || SpConstants.QQ_LOGIN.equals(loginFlag)) {//微信登录 QQ登录
//            if (AccountUtils.hasBoundPhone()) {
//                getLeXiangUserInfo();//获取乐享用户信息
//                requestIsFacilitator();//判断是否是服务商
//            } else {
//                setUserIconAndName(mUserName, headimgurl2, headimgurl);
//            }
//        } else {
//            if (!TextUtils.isEmpty(user_name_phone)) {//手机登录
//                getLeXiangUserInfo();//获取乐享用户信息
//                requestIsFacilitator();//判断是否是服务商
//            } else {
//                hasLoginLayout.setVisibility(View.GONE);
//                loginIcon.setVisibility(View.VISIBLE);
//                loginTv.setVisibility(View.VISIBLE);
//                noLoginLayout.setVisibility(View.VISIBLE);
//            }
//        }
    }

    /**
     * 是否是服务商
     */
    private void requestIsFacilitator() {
        //facilitator
        HttpProxy.getIsFacilitator(AccountUtils.getUser_id(), new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isFacilitator) {
                mIsFacilitator = isFacilitator;
                SharedPreferences.Editor edit = mSp.edit();
                edit.putBoolean(SpConstants.HAS_SERVICE_SHOP, isFacilitator);
                edit.commit();
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });
    }


    /**
     * 设置用户图标 和 名字
     *
     * @param name
     * @param imgString
     * @param imgUrl
     */
    private void setUserIconAndName(String name, String imgString, String imgUrl) {
//        userNameTv.setText(name);
//        if (!TextUtils.isEmpty(imgString)) {
//            Glide.with(MineFragment.this)
//                    .load(imgString)
//                    .error(R.mipmap.login_icon)
//                    .transform(new GlideCircleTransform(getActivity()))
//                    .into(userIconImg);
//        } else {
//            Glide.with(MineFragment.this)
//                    .load(imgUrl)
//                    .error(R.mipmap.login_icon)
//                    .transform(new GlideCircleTransform(getActivity()))
//                    .into(userIconImg);
//        }

    }

    /**
     * 获取乐享用户信息
     */
    public void getLeXiangUserInfo() {
        HttpProxy.getUserInfo("user_name_phone", new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {

            }

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ORDER_ACT_REQUEST) {

        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpUiEvent event) {
        switch (event.getEventId()) {
            case EventConstants.APP_LOGIN:
//                hasLoginLayout.setVisibility(View.VISIBLE);
//                loginIcon.setVisibility(View.GONE);
//                loginTv.setVisibility(View.GONE);
//                noLoginLayout.setVisibility(View.GONE);
                Log.e(TAG, "onEvent: 登录更新");
                WsManager.getInstance().init();
                getUserInfo();
                break;
            case EventConstants.APP_LOGOUT:
//                hasLoginLayout.setVisibility(View.GONE);
//                loginIcon.setVisibility(View.VISIBLE);
//                loginTv.setVisibility(View.VISIBLE);
//                noLoginLayout.setVisibility(View.VISIBLE);
                mIsFacilitator = false;
                Log.e(TAG, "onEvent: 注销更新");
//                allIncomeMoneyTv.setText("0");
//                allOrderCountTv.setText("0");
//                allIncomeTv.setText("0");
//                balanceTv.setText("0.00");
//                storedIcCardTv.setText("0.00");
//                achiRootLayout2.setVisibility(View.GONE);
//                achiRootLayout3.setVisibility(View.GONE);
//                gradeTv.setVisibility(View.GONE);
                AccountUtils.clearData();
                SharedPreferences sp = getActivity().getSharedPreferences(SpConstants.SP_LONG_USER_SET_USER, Context.MODE_PRIVATE);
                sp.edit().clear().commit();
                //                Constants.QQauth = QQAuth.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
                Tencent tencent = Tencent.createInstance(Constants.APP_QQ_ID, AppContext.getInstance());
                tencent.logout(getActivity());
                break;
            case EventConstants.UP_MINE_ORDER:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserIconEvent(PullImageEvent event) {
        if (event.getEvenId() == EventConstants.USER_ICON) {
            final String imgUrl = event.getImgUrl();
            HttpProxy.putUserIcon(getActivity(), imgUrl, new HttpCallBack<String>() {
                @Override
                public void onSuccess(String responseData) {
                    SharedPreferences.Editor edit = mSp.edit();
                    edit.putString(SpConstants.USER_IMG, imgUrl);
                    edit.commit();
                }

                @Override
                public void onError(Request request, Exception e) {
                    ToastUtils.makeTextShort("上传失败");
                }
            });
//            Glide.with(this)
//                    .load(URLConstants.REALM_URL + imgUrl)
//                    .placeholder(R.mipmap.login_icon)
//                    .transform(new GlideCircleTransform(getActivity()))
//                    .into(userIconImg);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    public void loadUserIcon(Uri selectedImage) {
        //上传图片
        GetImgUtil.pullImageBase4(getActivity(), selectedImage, EventConstants.USER_ICON);
    }


    @OnClick({R.id.messageImg, R.id.setting_img, R.id.invite_friends, R.id.gift_other, R.id.login_layout, R.id.recharge_layout, R.id.my_account_layout, R.id.my_complaint_layout, R.id.my_collect_layout, R.id.my_address_layout, R.id.my_order_layout, R.id.spread_layout, R.id.help_layout, R.id.seller_layout, R.id.proxy_layout, R.id.about_layout})
    public void onViewClicked(View view) {
        if (!AccountUtils.hasLogin()) {
            UIHelper.showUserLoginActivity(getActivity());
        } else if (!AccountUtils.hasBoundPhone()) {
            UIHelper.showBundPhoneActivity(getActivity());
        } else {
            switch (view.getId()) {
                case R.id.messageImg:
                    UIHelper.showNoticeActivity(getActivity());
                    break;
                case R.id.setting_img:
                    UIHelper.showPersonCenterActivity(getActivity());
                    break;
                case R.id.invite_friends:
                    break;
                case R.id.gift_other:
                    break;
                case R.id.login_layout:
                    UIHelper.showPersonCenterActivity(getActivity());
                    break;
                case R.id.recharge_layout:
                    UIHelper.showMonneyChongZhiActivity(getActivity());
                    break;
                case R.id.my_account_layout:
                    break;
                case R.id.my_complaint_layout:
                    break;
                case R.id.my_collect_layout:
                    UIHelper.showCollectionActivity(getActivity());
                    break;
                case R.id.my_address_layout:
                    UIHelper.showAddressManagerGlActivity(getActivity());
                    break;
                case R.id.my_order_layout:
                    break;
                case R.id.spread_layout:
                    break;
                case R.id.help_layout:
                    break;
                case R.id.seller_layout:
                    break;
                case R.id.proxy_layout:
                    break;
                case R.id.about_layout:
                    UIHelper.showWebActivity(getActivity(), "http://www.baidu.com");
                    break;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        DialogUtils.closeLoginDialog();
    }

}