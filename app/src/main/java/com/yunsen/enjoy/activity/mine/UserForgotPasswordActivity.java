package com.yunsen.enjoy.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.utils.Validator;
import com.yunsen.enjoy.widget.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

public class UserForgotPasswordActivity extends AppCompatActivity implements
        OnClickListener {

    private Button img_title_login;
    private EditText userpwd, userphone, et_user_yz;
    private Button btn_register, get_yz;
    private String name, phone, postbox, pwd, pwdagain, insertdata, yz,
            shoujihao;
    private String str, hengyuName;
    private DialogProgress progress;
    private String strUrl;
    private TextView regise_tip, tv_title;
    private String yanzhengma;
    private LinearLayout ll_zhuchexieyi;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wangji_pass);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initdata();

        tv_title = (TextView) findViewById(R.id.tv_title);
        String type_num = getIntent().getStringExtra("type");
        System.out.println("=================type_num==" + type_num);
        if (type_num != null) {
            if (type_num.equals("1")) {
                type = "password";
                tv_title.setText("找回用户密码");
            } else if (type_num.equals("2")) {
                type = "paypassword";
                tv_title.setText("找回支付密码");
                userpwd.setHint("请输入支付密码(6-16位数字的密码)");
            } else {

            }
        } else {

        }

        //		ImageView img_menu = (ImageView) findViewById(R.id.img_menu);
        TextView img_menu = (TextView) findViewById(R.id.iv_fanhui);
        img_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }

    Handler handler = new Handler() {

        public void dispatchMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    String strhengyuname = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), strhengyuname,
                            Toast.LENGTH_SHORT).show();
                    progress.CloseProgress();
                    // Intent intent = new Intent(UserRegisterActivity.this,
                    // UserLoginActivity.class);
                    // startActivity(intent);
                    finish();
                    break;
                case 1:
                    String strmsg = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), strmsg, Toast.LENGTH_SHORT)
                            .show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "验证码已发送",
                            Toast.LENGTH_SHORT).show();
                    new Thread() {
                        public void run() {
                            for (int i = 120; i >= 0; i--) {
                                if (i == 0) {
                                    handler.sendEmptyMessage(4);
                                } else {
                                    Message msg = new Message();
                                    msg.arg1 = i;
                                    msg.what = 5;
                                    handler.sendMessage(msg);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {

                                        e.printStackTrace();
                                    }

                                }
                            }
                        }

                        ;
                    }.start();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "验证码已下发",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    get_yz.setEnabled(true);
                    get_yz.setText("获取验证码");
                    break;
                case 5:
                    get_yz.setEnabled(false);
                    get_yz.setText(msg.arg1 + "s");
                    break;
                default:
                    break;
            }
        }

        ;

    };

    private void initdata() {
        try {
            // ll_zhuchexieyi.setVisibility(View.GONE);
            LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
            //			ll_buju.setBackgroundResource(R.drawable.denglu_beijing);
            et_user_yz = (EditText) findViewById(R.id.et_user_yz);
            get_yz = (Button) findViewById(R.id.get_yz);
            // img_title_login = (Button) findViewById(R.id.img_title_login);
            // username = (EditText) findViewById(R.id.et_user_name);
            userphone = (EditText) findViewById(R.id.et_user_phone);
            // userpostbox = (EditText) findViewById(R.id.et_user_postbox);
            userpwd = (EditText) findViewById(R.id.et_user_pwd);
            // userpwdagain = (EditText) findViewById(R.id.et_user_pwd_again);
            btn_register = (Button) findViewById(R.id.btn_register);
            // img_title_login.setOnClickListener(this);
            btn_register.setOnClickListener(this);
            get_yz.setOnClickListener(this);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regise_tip:
                UIHelper.showUserAgreement(UserForgotPasswordActivity.this);
                break;
            case R.id.get_yz:
                phone = userphone.getText().toString().trim();
                if ("".equals(phone)) {
                    Toast.makeText(UserForgotPasswordActivity.this, "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 11) {
                    Toast.makeText(UserForgotPasswordActivity.this, "手机号码少于11位", Toast.LENGTH_SHORT).show();
                } else {
                    if (Validator.isMobile(phone)) {
                        strUrl = URLConstants.REALM_ACCOUNT_URL + "/user_passmis_smscode?mobile=" + phone + "";

                        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {
                                super.onSuccess(arg0, arg1);
                                try {
                                    JSONObject object = new JSONObject(arg1);
                                    String result = object.getString("status");//
                                    String info = object.getString("info");// info
                                    if ("y".equals(result)) {
                                        yanzhengma = object.getString("data");
                                        handler.sendEmptyMessage(2);
                                    } else {
                                        Toast.makeText(UserForgotPasswordActivity.this, info, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, UserForgotPasswordActivity.this);
                    } else {
                        ToastUtils.makeTextShort("手机号码不正确");
                    }
                }

                break;

            case R.id.btn_register:
                yz = et_user_yz.getText().toString().trim();
                phone = userphone.getText().toString().trim();
                pwd = userpwd.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(UserForgotPasswordActivity.this, "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 11) {
                    Toast.makeText(UserForgotPasswordActivity.this,
                            "手机号码少于11位", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yz)) {
                    Toast.makeText(UserForgotPasswordActivity.this, "验证码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(UserForgotPasswordActivity.this, "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 6) {
                    Toast.makeText(UserForgotPasswordActivity.this, "密码不得小于6位",
                            Toast.LENGTH_SHORT).show();
                } else if (!(pwd.length() <= 20 && pwd.length() >= 6)) {
                    ToastUtils.makeTextShort("密码在6-20位之间");
                } else {
                    try {
                        progress = new DialogProgress(UserForgotPasswordActivity.this);
                        progress.CreateProgress();
                        new Thread() {
                            public void run() {
                                strUrl = URLConstants.REALM_ACCOUNT_URL
                                        + "/mobile_update_password?user_name="
                                        + phone + "&mobile=" + phone
                                        + "&code=" + yz + "&newpassword="
                                        + pwd + "&type=" + type + "";
                                System.out.println("找回密码" + strUrl);
                                AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int arg0, String arg1) {
                                        // method stub
                                        super.onSuccess(arg0, arg1);
                                        try {
                                            JSONObject jsonObject = new JSONObject(arg1);
                                            String status = jsonObject.getString("status");
                                            String info = jsonObject.getString("info");
                                            if ("n".equals(status)) {
                                                str = jsonObject.getString("info");
                                                String no = jsonObject.getString("info");
                                                progress.CloseProgress();
                                                Message message = new Message();
                                                message.what = 1;
                                                message.obj = str;
                                                handler.sendMessage(message);

                                            } else if ("y".equals(status)) {
                                                hengyuName = jsonObject.getString("info");
                                                SharedPreferences spPreferences = getSharedPreferences("longuserset_user", MODE_PRIVATE);
                                                Editor editor = spPreferences.edit();
                                                editor.putBoolean("save", true);
                                                editor.putString("user_name", userphone.getText().toString());
                                                editor.putString("pwd", userpwd.getText().toString());
                                                editor.commit();
                                                progress.CloseProgress();
                                                Message message = new Message();
                                                message.what = 0;
                                                message.obj = hengyuName;
                                                handler.sendMessage(message);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable arg0, String arg1) {
                                        super.onFailure(arg0, arg1);
                                        progress.CloseProgress();
                                    }
                                }, getApplicationContext());

                            }


                        }.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            default:
                break;
        }

    }

}
