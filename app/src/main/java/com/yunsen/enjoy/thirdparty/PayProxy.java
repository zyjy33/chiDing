package com.yunsen.enjoy.thirdparty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.yunsen.enjoy.thirdparty.alipay.OrderInfoUtil2_0;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/21.
 */

public class PayProxy {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2018081461034352";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088131337200802";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final int SDK_PAY_FLAG = 111;  //支付宝支付
    public static final int PAY_FAIL = 333;//支付失败
    public static String NOTIFY_URL = "http://www.szlxkg.com/api/payment/alipayphone/notify_url.aspx";
    public static String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeTk7zTpASZRdlyNFHoSnwvqSu5N5CZEMcteNdBvfiBDAT/GyGwaf5ak80RnV/+wgwzpz7fSh1x4K6bad8yLpVPhO03EJBqGNghMf0w4eL1dC1Uqpy/X4rz4E0pzUzPl8VpkDVvR2ECbn/CQUM43gArjGH21LcaSEwHIxS2i9GIIDi6PWvtQsLZz0Pgj2mNVknyerH7jzmb7a+irZryXVEgC9IzGYK/GdWK9nyjBXr4+oaGOmNm78Hx35xjmEyz6DC9o3ljGG0IOX2qDMkcpxJ0QrhIqPywzy7e2qhjjDlbDc91nSIEVnHzI8lf5PU2oDtA66q7wqTIEOtNk9ZiWsvAgMBAAECggEAXfQ8B0tpVoGJEW/ORaTxL2D2fJZ5Ew5OhoutFVk9ZXb20eXOw2vMNctSlsP6mKp0ggMeSGRPzxdKDztlXDoF3PGq4HyZFj7KPE6SHkKt/+A2rmcRoo2whG/zTzyt5mvuIvs5H6HOEr9zFFpWFQAsCLtZyjfxRb9fYx6Oad30kLlqs0hacHv/U9gmNhARboTrQzJWR57rG5HL8lMsJr+3LhTQnz3ggSX62zoZqRT3UKWNlGmQOvZ67Y+QJxIFPR5hhtrkyFbSHHpHDsScI8wmHVUz5rA/Im4YJRBjBAOw48hzFocTfjZplAuRnInCkyirYvcMLw7CKYUm0DNZYVW0gQKBgQDPi6BfxazA4HBxkMLT5BTdDFpPXfol56TmBzWuaFoe6/UbVokiSAYD7bCNgEcA8T3Mz9uxMEVMETDieN7WP05btTqpmsrK0EgtxyWp7N6BfMlaOfkKCjg1IWQZzOjumxySrG36Vge4UEOKw/QPZdN+JkpG04Z9iJE1hHX5RJykSwKBgQDDQ8ggcHdjpy0sEql1xcVUkFeI63I6oAlsVPtR+oLVJjuvdRAxVuAW8pxwH9LtWNA/qNzVjq6oOMHMyjs0FDs/9ufwRpKoxTnk4ZyUiO0F/j0td9pEYcTg7wRpp2zIS9RIpGbU71A9dlfNQJ1R6Ck+uXCKBp51tsrDClst0udeLQKBgQDKJixhvC8EelbrNl2dduUfXL3F2jogm1xQtKsDoh5NSfQl2YaeflpljyjM2inOcCqKh9Zq4iJM8d2UaLhbI7QVvRJTSjLFklMLS5KSzOT4aGvSVu8LaWLP1SMjwiMK7MfK0OoZz5ubWYo/6ZaZPMvfbjogfDzdnpwY7VjfHoYXuwKBgFnoe91z64UXFNWWQjDYP2QQkj2ZK5+S0MPw3UeB8XvzfsfrPul49vWaAC4geR6QJlMpjYVHjWQnvhRtiZURO7bYGTOpSx4qsd2RhGdgJML9rW2iIf4ahmyLoZyEWrJfN/+6sx6Yknm6Y/M+LTHplBQtjoP/SDcrU3E684DF+4KBAoGBAIusilXganjY7ekGlbIBmE68CuOscb0Ii3PukrFIqAxriNQTsoV8A+Ti5NHMEX7cDYQP0c9KMwAV6Hz57Bma8a0tsa74Y2bwl7xqo99Bq/zCdzhrbiZmDLX1o7KTJtTLys87gdc4FEFgHGjf4Y6Jb9LSunKDiIzXEAdkf08OU976";
    public static final String RSA_PRIVATE = "";
    public static final int SDK_AUTH_FLAG = 222; //支付宝授权

    public static void payV2(final Activity act, final Handler mHandler, Map<String, String> params) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(act).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            act.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(act);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
