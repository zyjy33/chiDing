package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.CarShoppingAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.GoodsCarInfo;
import com.yunsen.enjoy.model.OrderInfo;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.utils.StringUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.interfaces.TotalInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/25.
 * 购物车
 */

public class ShopCartActivity extends BaseFragmentActivity implements TotalInterface {

    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.shop_car_recycler)
    RecyclerView shopCarRecycler;
    @Bind(R.id.goods_all_size)
    TextView goodsAllSize;
    @Bind(R.id.goods_all_price)
    TextView goodsAllPrice;
    @Bind(R.id.change_goods_btn)
    Button changeGoodsBtn;
    @Bind(R.id.no_goods_layout)
    LinearLayout noGoodsLayout;
    @Bind(R.id.has_goods_layout)
    FrameLayout hasGoodsLayout;
    List<List<GoodsCarInfo>> mDatas;
    private CarShoppingAdapter mAdapter;
    private CheckBox checkAllGoods;
    private int mPageIndex = 1;
    private boolean mFlagLoad = true;//第一次打开不走onResumen 刷新界面
    private int mCurrentPosition = -1;

    @Override
    public int getLayout() {
        return R.layout.activity_shop_cart;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarTitle.setText("购物车");
        shopCarRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mDatas.add(new ArrayList<GoodsCarInfo>());
        mAdapter = new CarShoppingAdapter(this, R.layout.car_shopping_item, mDatas);
        shopCarRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setTotalInterface(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFlagLoad) {
            mFlagLoad = false;
        } else {
            requestData();
        }
    }

    @Override
    public void requestData() {
        HttpProxy.getMyShoppingCart(String.valueOf(mPageIndex), new HttpCallBack<List<GoodsCarInfo>>() {
            @Override
            public void onSuccess(List<GoodsCarInfo> responseData) {
                ArrayList<List<GoodsCarInfo>> list = new ArrayList<>();
                list.add(responseData);
                mAdapter.upBaseDatas(list);
                upTotalData(0, 0, -1);//还原状态
                isShowEmptyView(responseData.size() == 0);
            }

            @Override
            public void onError(Request request, Exception e) {
                isShowEmptyView(true);
            }
        });
    }


    private void submitBuyGoods(int account, double price) {
        ArrayList<GoodsCarInfo> datas = mAdapter.getCurrentRequestList(mCurrentPosition);
        String quantity = "";
        String goodsId = "";
        String articlesId = "";

        for (int i = 0; i < datas.size() - 1; i++) {
            GoodsCarInfo info = datas.get(i);
            quantity += info.getQuantity() + ",";
            goodsId += info.getGoods_id() + ",";
            articlesId += info.getArticle_id() + ",";
        }
        if (datas.size() > 0) {
            GoodsCarInfo info = datas.get(datas.size() - 1);
            quantity += info.getQuantity();
            goodsId += info.getGoods_id();
            articlesId += info.getArticle_id();
        }

        HttpProxy.addShoppingBuys(goodsId, articlesId, quantity, new HttpCallBack<OrderInfo>() {
            @Override
            public void onSuccess(OrderInfo responseData) {
                if (responseData != null) {
                    UIHelper.showMyOrderConfrimActivity(ShopCartActivity.this, responseData.getBuy_no());
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });


//        String[] requestDatas = mAdapter.getCurrentRequestDatas(mCurrentPosition);
//        if (requestDatas != null && requestDatas.length >= 3) {
//            HttpProxy.submitShoppingGoods(requestDatas[0], requestDatas[1], requestDatas[2], new HttpCallBack<OrderInfo>() {
//                @Override
//                public void onSuccess(OrderInfo responseData) {
//                    String buyNo = responseData.getBuy_no();
//                    UIHelper.showSubmitOrderActivity(getActivity());
//                }
//
//                @Override
//                public void onError(Request request, Exception e) {
//                    ToastUtils.makeTextShort(e.getMessage());
//                }
//            });
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void isShowEmptyView(boolean isEmptyData) {
        if (isEmptyData) {
            noGoodsLayout.setVisibility(View.VISIBLE);
            hasGoodsLayout.setVisibility(View.GONE);
        } else {
            noGoodsLayout.setVisibility(View.GONE);
            hasGoodsLayout.setVisibility(View.VISIBLE);
        }
    }

    private static final String TAG = "CarFragment";

    @Override
    public void upTotalData(double price, int num, int position) {
        Log.e(TAG, "upTotalData: num=" + num);
        mCurrentPosition = position;
        if (position == -1) {
            isShowEmptyView(true);
        } else {
            isShowEmptyView(false);
            goodsAllSize.setText(num + "件");
            goodsAllSize.setTag(num);
            goodsAllPrice.setTag(price);
            goodsAllPrice.setText("￥" + StringUtils.changeToMoney(price));
        }

//        if (mDatas)
//        goodsAllSize.setText(goodsSum + "件");
//        goodsAllPrice.setText("￥" + StringUtils.changeToMoney(goodsPrices));
//        isShowEmptyView(mDatas.size() == 0);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.action_back, R.id.change_goods_btn, R.id.goto_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.change_goods_btn:
                if (mAdapter == null) {
                    return;
                }
                if (goodsAllSize.getTag() == null || ((int) goodsAllSize.getTag()) == 0 || mCurrentPosition == -1) {
                    ToastUtils.makeTextShort("请选择要支付的商品");
                } else {
                    submitBuyGoods((int) goodsAllSize.getTag(), (double) goodsAllPrice.getTag());
                }
                break;
            case R.id.goto_shopping:
                UIHelper.showPartsShopActivity(this);
                finish();
                break;
        }
    }
}
