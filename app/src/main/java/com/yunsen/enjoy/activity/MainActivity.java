
package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.about.AboutFragment;
import com.yunsen.enjoy.common.AppManager;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.wsmanager.WsManager;
import com.yunsen.enjoy.fragment.ApplyServiceFragment;
import com.yunsen.enjoy.fragment.BuyFragment;
import com.yunsen.enjoy.fragment.CarFragment;
import com.yunsen.enjoy.fragment.DiscoverFragment;
import com.yunsen.enjoy.fragment.MainPagerFragment;
import com.yunsen.enjoy.fragment.MineFragment;
import com.yunsen.enjoy.fragment.NoticeFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.http.down.AppUpManager;
import com.yunsen.enjoy.model.PgyAppVersion;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.AccountUtils;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Request;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private int currIndex = 0;

    private RadioGroup group;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;
    private long mFirstPressedTime;
    private MineFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        WsManager.getInstance().init();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        initMainData(savedInstanceState);
        initMainView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initMainData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "AboutFragment", "ApplyServiceFragment", "MineFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    @Override
    public void requestData() {
        AppUpManager.getInstance().startCheckUpdate(this, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int index = intent.getIntExtra(Constants.FRAGMENT_TYPE_KEY, -1);
            int indexId = R.id.foot_bar_home;
            if (index != -1) {
                switch (index) {
                    case 0:
                        indexId = R.id.foot_bar_home;
                        break;
                    case 1:
                        indexId = R.id.foot_bar_im;
                        break;
                    case 2:
                        indexId = R.id.foot_bar_apply;
                        break;
                    case 3:
                        indexId = R.id.main_footbar_user;
                        break;
                }
                group.check(indexId);
            }
        }
    }

    private void initMainView() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currIndex = 0;
                        break;
                    case R.id.foot_bar_im:
                        currIndex = 1;
                        break;
                    case R.id.foot_bar_apply:
                        currIndex = 2;
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 3;
                        break;
                    default:
                        break;
                }
                showFragment();
            }
        });
        showFragment();
    }

    private void showFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new MainPagerFragment();
            case 1:
                return new AboutFragment();
            case 2:
                return new ApplyServiceFragment();
            case 3:
                mMineFragment = new MineFragment();
                return mMineFragment;
            default:
                return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mFirstPressedTime < 2000) {
                finish();
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mFirstPressedTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCurrIndex(int index) {
        if (index < 0) {
            index = 0;
        }
        if (index > 3) {
            index = 0;
        }
        switch (index) {
            case 0:
                group.check(R.id.foot_bar_home);
                break;
            case 1:
                group.check(R.id.foot_bar_im);
                break;
            case 2:
                group.check(R.id.foot_bar_apply);
                break;
            case 3:
                group.check(R.id.main_footbar_user);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.PHOTO_ACTIVITY_REQUEST) {
                Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                if (mMineFragment != null) {
                    mMineFragment.loadUserIcon(selectedImage);
                }
            }
        }
        if (requestCode == Constants.ORDER_ACT_REQUEST) {
            if (mMineFragment != null) {
                mMineFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onRequestPermissionSuccess(int requestCode) {
        super.onRequestPermissionSuccess(requestCode);
        if (requestCode == Constants.CALL_PHONE) {
            UIHelper.showPhoneNumberActivity(this, "400****120");
        } else if (requestCode == Constants.WRITE_EXTERNAL_STORAGE) {
            UIHelper.showPhotoActivity(this, Constants.PHOTO_ACTIVITY_REQUEST);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        WsManager.getInstance().disconnect();
    }
}
