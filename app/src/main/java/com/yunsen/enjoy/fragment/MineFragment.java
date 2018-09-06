package com.yunsen.enjoy.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.tauth.Tencent;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.dealer.ApplyServiceActivity;
import com.yunsen.enjoy.activity.dealer.MyFacilitatorActivity;
import com.yunsen.enjoy.activity.mine.PersonCenterActivity;
import com.yunsen.enjoy.common.AppContext;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
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
import com.yunsen.enjoy.utils.DeviceUtil;
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
    @Bind(R.id.logout_layout)
    LinearLayout logoutLayout;
    private FragmentActivity context;
    private SharedPreferences mSp;
    private String mGroupId;
    private boolean mIsFacilitator;
    private Dialog mLoginDialog;


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
                setWeiXinLoginInfo();
            }
            logoutLayout.setVisibility(View.VISIBLE);
        } else {
            memberIdTv.setVisibility(View.GONE);
            userNameTv.setVisibility(View.GONE);
            loginTv.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(View.GONE);
        }
        if (!AccountUtils.isLogin()) {
            showLoginDialog();
        }

    }

    private void setWeiXinLoginInfo() {
        memberIdTv.setVisibility(View.VISIBLE);
        userNameTv.setVisibility(View.VISIBLE);
        loginTv.setVisibility(View.GONE);
        String nickName = mSp.getString(SpConstants.NICK_NAME, Constants.EMPTY);
        String userName = AccountUtils.getUserName();
        if (TextUtils.isEmpty(userName)) {
            userNameTv.setText(nickName);
        } else {
            userNameTv.setText(userName);
        }
        String userCode = mSp.getString(SpConstants.USER_CODE, Constants.EMPTY);
        if (!TextUtils.isEmpty(userCode)) {
            memberIdTv.setText("会员号:" + userCode);
        }

    }

    @Override
    protected void requestData() {
        Boolean sFilter = mSp.getBoolean(SpConstants.HAS_SERVICE_SHOP, false);
        if (sFilter) {
            myOrderLayout.setVisibility(View.VISIBLE);//商家
        } else {
            requestIsFacilitator();
        }
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
     * 我是商家的判断跳转
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
                balanceTv.setText(String.valueOf(data.getCard()));
                memberIdTv.setText("会员号:" + data.getUser_code());
                String nickName = data.getNick_name();
                if (TextUtils.isEmpty(nickName)) {
                    nickName = data.getUser_name();
                }
                userNameTv.setText(nickName);
                memberIdTv.setVisibility(View.VISIBLE);
                userNameTv.setVisibility(View.VISIBLE);
                loginTv.setVisibility(View.GONE);
                Glide.with(getActivity())
                        .load(data.getAvatar())
                        .placeholder(R.mipmap.login_icon)
                        .transform(new GlideCircleTransform(getActivity()))
                        .into(userImg);
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
//                requestIsFacilitator();//判断是否是商家
//            } else {
//                setUserIconAndName(mUserName, headimgurl2, headimgurl);
//            }
//        } else {
//            if (!TextUtils.isEmpty(user_name_phone)) {//手机登录
//                getLeXiangUserInfo();//获取乐享用户信息
//                requestIsFacilitator();//判断是否是商家
//            } else {
//                hasLoginLayout.setVisibility(View.GONE);
//                loginIcon.setVisibility(View.VISIBLE);
//                loginTv.setVisibility(View.VISIBLE);
//                noLoginLayout.setVisibility(View.VISIBLE);
//            }
//        }
    }

    /**
     * 是否是商家
     */
    private void requestIsFacilitator() {
        //facilitator
        HttpProxy.getIsFacilitator(AccountUtils.getUser_id(), new HttpCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean isFacilitator) {
                mIsFacilitator = isFacilitator;
                if(isFacilitator){
                    myOrderLayout.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor edit = mSp.edit();
                    edit.putBoolean(SpConstants.HAS_SERVICE_SHOP, isFacilitator);
                    edit.commit();
                }else {
                    myOrderLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                myOrderLayout.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ORDER_ACT_REQUEST) {
        }
    }

    public void onResumes() {
        if (getActivity() != null) {
            if (!AccountUtils.isLogin()) {
                showLoginDialog();
            } else {
                if (mLoginDialog != null && mLoginDialog.isShowing()) {
                    mLoginDialog.dismiss();
                }
            }

            if (AccountUtils.hasBoundPhone()) {
                getUserInfo();
            } else {
                UIHelper.showBindBankCardActivity(getActivity());
                setWeiXinLoginInfo();
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UpUiEvent event) {
        Log.e(TAG, "onEvent: getEventId=" + event.getEventId());
        switch (event.getEventId()) {
            case EventConstants.APP_LOGIN:
                Log.e(TAG, "onEvent: 登录更新");
                if (AccountUtils.hasBoundPhone()) {
                    getUserInfo();
                } else {
                    setWeiXinLoginInfo();
                }
                logoutLayout.setVisibility(View.VISIBLE);
                break;
            case EventConstants.APP_LOGOUT:
                logoutLayout.setVisibility(View.GONE);
                userNameTv.setText(Constants.EMPTY);
                memberIdTv.setVisibility(View.GONE);
                userNameTv.setVisibility(View.GONE);
                loginTv.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(R.mipmap.login_icon)
                        .transform(new GlideCircleTransform(getActivity()))
                        .into(userImg);
                balanceTv.setText("0.00");
                mIsFacilitator = false;
                Log.e(TAG, "onEvent: 注销更新");
                myOrderLayout.setVisibility(View.GONE);
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
//                    .into(userImg);
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


    @OnClick({R.id.messageImg, R.id.setting_img, R.id.invite_friends, R.id.gift_other, R.id.login_layout, R.id.recharge_layout,
            R.id.my_account_layout, R.id.my_complaint_layout, R.id.my_collect_layout, R.id.my_address_layout, R.id.my_order_layout,
            R.id.spread_layout, R.id.help_layout, R.id.seller_layout, R.id.proxy_layout, R.id.about_layout, R.id.add_shopping_layout,
            R.id.logout_layout})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.about_layout) {
            UIHelper.showWebActivity(getActivity(), URLConstants.ABOUT_URL);
        } else if (view.getId() == R.id.logout_layout) {
            DialogUtils.showLoginDialog(getActivity());
        } else if (!AccountUtils.hasLogin()) {
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
                case R.id.invite_friends://邀请好友
                    UIHelper.showInvitationFriendActivity(getActivity());
                    break;
                case R.id.gift_other: //转赠
                    UIHelper.showTransferOtherActivity(getActivity());
                    break;
                case R.id.login_layout:
                    UIHelper.showPersonCenterActivity(getActivity());
                    break;
                case R.id.recharge_layout:
                    UIHelper.showMonneyChongZhiActivity(getActivity(), "16");
                    break;
                case R.id.my_account_layout:
                    UIHelper.showMyAccountOrderActivity(getActivity());
                    break;
                case R.id.my_complaint_layout:
                    UIHelper.showMyTranslateActivity(getActivity());
                    break;
                case R.id.my_collect_layout:
                    UIHelper.showCollectionActivity(getActivity());
                    break;
                case R.id.my_address_layout:
                    UIHelper.showAddressManagerGlActivity(getActivity());
                    break;
                case R.id.my_order_layout:
                    UIHelper.showOrderActivity(getActivity(), "1");
                    break;
                case R.id.spread_layout:
                    UIHelper.showSpreadActivity(getActivity());
                    break;
                case R.id.help_layout:
                    UIHelper.showHelpActivity(getActivity());
                    break;
                case R.id.seller_layout://商家加盟
                    UIHelper.showApplyShoppingActivity(getActivity());
                    break;
                case R.id.proxy_layout:
                    UIHelper.showApplyServiceActivity(getActivity());
                    break;
                case R.id.add_shopping_layout:
                    UIHelper.showAddShoppingActivity(getActivity());
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

    private void showLoginDialog() {
        if (mLoginDialog == null) {
            mLoginDialog = new Dialog(getActivity(), R.style.AlertDialogStyle2);
            View rootView = getLayoutInflater().inflate(R.layout.login_dialog, null);
            rootView.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showUserLoginActivity(getActivity());
                }
            });
            int screenHeight = DeviceUtil.getScreenHeight();
            mLoginDialog.setCancelable(false);//点击外部不可dismiss
            mLoginDialog.setCanceledOnTouchOutside(true);
            Window window = mLoginDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mLoginDialog.setContentView(rootView, new ViewGroup.LayoutParams(DeviceUtil.getScreenWidth() / 3 * 2, screenHeight / 4));
        }

        if (!mLoginDialog.isShowing()) {
            mLoginDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginDialog!=null&&mLoginDialog.isShowing()) {
            mLoginDialog.dismiss();
        }
        mLoginDialog = null;
    }
}