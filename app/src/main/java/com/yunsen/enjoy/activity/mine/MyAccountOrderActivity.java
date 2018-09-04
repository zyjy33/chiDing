package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.buy.GoodsListActivity;
import com.yunsen.enjoy.activity.mine.adapter.CheckItemAdapter;
import com.yunsen.enjoy.activity.mine.adapter.MyAccountOrderAdapter;
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.CheckedData;
import com.yunsen.enjoy.model.ComplaintBean;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.WalletCashBean;
import com.yunsen.enjoy.ui.loadmore.ZyBottomView;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.NoticeView;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class MyAccountOrderActivity extends BaseFragmentActivity {
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.action_back_layout)
    LinearLayout actionBackLayout;
    @Bind(R.id.team_top_layout)
    LinearLayout teamTopLayout;
    @Bind(R.id.filter_tv)
    TextView filterTv;
    @Bind(R.id.notice_view)
    NoticeView noticeView;
    @Bind(R.id.current_filter_tv)
    TextView currentFilterTv;
    @Bind(R.id.filter_layout)
    LinearLayout filterLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private PopupWindow popWnd;
    private int mPageIndex = 1;
    private boolean mIsLoadMore = true;
    private MyAccountOrderAdapter mAdapter;
    private List<WalletCashBean> mDatas;
    private String mNearDay = "0";  //nday:时间筛选（0-全部,1-近一周,2-近一月,3-近三月,4-三月前）
    private String mFundId = "16,5,1";  //资金类型：fund_id（余额=1,现金=11,积分=2,红包=3,养老金=4,备货金=5,备货金余额=6,备货金累计=7,消费价值=8,营销劵=9,培训金=10,公益价值=12,投资价值=13,行为价值=14,时间价值=15..多个逗号分隔）
    private String mExpensesId = "0"; //消费类型：expenses_id(充值受益=1,招募展会=2,招募产品=3,招募渠道=4,招募广告=5,终端消费=6,终端推广=7,营销服务=8,平台广告=9,备用金=10,其他11)
    private String mFlag = "1"; //1 收益 2消费

    @Override
    public int getLayout() {
        return R.layout.activity_my_account_order;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(this);
        refreshLayout.setBottomView(loadingView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new MyAccountOrderAdapter(this, R.layout.my_account_order_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter viewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(viewAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        actionBarTitle.setText("我的账单");
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestList(true);
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestList(false);
                    }
                }, 500);
            }
        });
    }

    private static final String TAG = "MyAccountOrderActivity";

    @Override
    public void requestData() {
        requestList(true);
    }

    private void requestList(boolean isUpData) {
        if (isUpData) {
            refreshLayout.setBottomView(new LoadingView(MyAccountOrderActivity.this));
            mPageIndex = 1;
            mIsLoadMore = false;
        } else {
            mIsLoadMore = true;
            mPageIndex++;
        }
        HttpProxy.getDdekUserPayrecordList(String.valueOf(mPageIndex), String.valueOf(mNearDay), String.valueOf(mFundId), String.valueOf(mFlag),
                new HttpCallBack<List<WalletCashBean>>() {
                    @Override
                    public void onSuccess(List<WalletCashBean> responseData) {
                        if (mIsLoadMore) {
                            refreshLayout.setVisibility(View.VISIBLE);
                            noticeView.setVisibility(View.GONE);
                            boolean hasMore = mAdapter.addBaseDatas(responseData);
                            if (hasMore) {
                                refreshLayout.finishLoadmore();
                            } else {
                                refreshLayout.setBottomView(new ZyBottomView(MyAccountOrderActivity.this));
                            }
                        } else {
                            refreshLayout.setVisibility(View.VISIBLE);
                            noticeView.setVisibility(View.GONE);
                            mAdapter.upBaseDatas(responseData);
                            refreshLayout.finishRefreshing();
                            refreshLayout.finishLoadmore();
                            if (responseData == null || responseData.size() == 0) {
                                refreshLayout.setVisibility(View.GONE);
                                noticeView.setVisibility(View.VISIBLE);
                                noticeView.showNoticeType(NoticeView.Type.NO_DATA);
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        if (mIsLoadMore) {
                            refreshLayout.setBottomView(new ZyBottomView(MyAccountOrderActivity.this));
                        } else {
                            refreshLayout.finishRefreshing();
                            refreshLayout.setVisibility(View.GONE);
                            noticeView.setVisibility(View.VISIBLE);
                            noticeView.showNoticeType(NoticeView.Type.NO_DATA);
                        }
                    }
                });
    }


    /**
     * 显示类型的列表
     */
    private void showFiltePopupWindow() {
        int[] location = new int[2];
//        dRecyclerView.getLocationInWindow(location);//在屏幕中的位置

        if (popWnd == null) {
            View contentView = LayoutInflater.from(MyAccountOrderActivity.this).inflate(R.layout.filter_popup_layout, null);
            RecyclerView recyclerContent = contentView.findViewById(R.id.recycler_content);


            GridLayoutManager layoutManager1 = new GridLayoutManager(this, 4);
            recyclerContent.setLayoutManager(layoutManager1);
            ArrayList<CheckedData> data1 = new ArrayList<>();
            data1.add(new CheckedData("0", "全部", "16,5,9,1", false, "1"));
            data1.add(new CheckedData("1", "充值", "16,9", false, "1"));
            data1.add(new CheckedData("2", "消费", "16,5", false, "2"));
            data1.add(new CheckedData("3", "赠送", "16,5,1", false, "1"));
//            data1.add(new CheckedData("4", "转赠", 2, false));
//            data1.add(new CheckedData("3", "退款", 1, false));
            final CheckItemAdapter adapter1 = new CheckItemAdapter(this, R.layout.checked_item, data1);
            recyclerContent.setAdapter(adapter1);

            RecyclerView recyclerTime = (RecyclerView) contentView.findViewById(R.id.recycler_time);
            GridLayoutManager layoutManager2 = new GridLayoutManager(this, 5);
            recyclerTime.setLayoutManager(layoutManager2);
            ArrayList<CheckedData> data2 = new ArrayList<>();
            data2.add(new CheckedData("0", "全部", "0", false));
            data2.add(new CheckedData("1", "近一周", "1", false));
            data2.add(new CheckedData("2", "近一月", "2", false));
            data2.add(new CheckedData("3", "近三月", "3", false));
            data2.add(new CheckedData("4", "三月前", "4", false));
            final CheckItemAdapter adapter2 = new CheckItemAdapter(this, R.layout.checked_item, data2);
            recyclerTime.setAdapter(adapter2);

            RecyclerView recyclerType = (RecyclerView) contentView.findViewById(R.id.recycler_type);
            GridLayoutManager layoutManager3 = new GridLayoutManager(this, 4);
            recyclerType.setLayoutManager(layoutManager3);
            ArrayList<CheckedData> data3 = new ArrayList<>();
            data3.add(new CheckedData("0", "全部", "16", false));
            data3.add(new CheckedData("1", "消费券", "16", false));
            final CheckItemAdapter adapter3 = new CheckItemAdapter(this, R.layout.checked_item, data3);
            recyclerType.setAdapter(adapter3);

            popWnd = new PopupWindow(this);
            popWnd.setContentView(contentView);
            popWnd.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));

            contentView.findViewById(R.id.reset_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: aaa");
                    adapter1.reset();
                    adapter2.reset();
                    adapter3.reset();
                    mNearDay = "0";
                    mFundId = "16,9,5,1";
                    mFlag = "1";
                }
            });
            contentView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: bbbb");
                    CheckedData contentModel = adapter1.getCurrentModel();
                    CheckedData timeModel = adapter2.getCurrentModel();
                    CheckedData typeModel = adapter3.getCurrentModel();
                    StringBuffer buffer = new StringBuffer();
                    if (timeModel != null) {
                        mNearDay = timeModel.getValue();
                        if (timeModel.getName() != null) {
                            buffer.append(timeModel.getName());
                        } else {
                            buffer.append("全部");
                        }
                    }

                    if (contentModel != null) {
                        mFlag = contentModel.getFlag();
                        if (contentModel.getName() != null) {
                            buffer.append(contentModel.getName());
                        }
                    }
                    if (typeModel != null) {
                        mFundId = typeModel.getValue();
                        if (typeModel.getName() != null) {
                            buffer.append(typeModel.getName());
                        }
                    }

                    buffer.append("账单");
                    currentFilterTv.setText(buffer.toString());
                    popWnd.dismiss();
                    requestList(true);
                }
            });


            adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof CheckItemAdapter) {
                        ((CheckItemAdapter) adapter).setSelected(position);
                        String name = ((CheckItemAdapter) adapter).getDatas().get(position).getName();
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            adapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof CheckItemAdapter) {
                        ((CheckItemAdapter) adapter).setSelected(position);
                        String name = ((CheckItemAdapter) adapter).getDatas().get(position).getName();
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

            adapter3.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof CheckItemAdapter) {
                        ((CheckItemAdapter) adapter).setSelected(position);
                        String name = ((CheckItemAdapter) adapter).getDatas().get(position).getName();
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

        }
        popWnd.setWidth(DeviceUtil.getScreenWidth());
        popWnd.setHeight(DeviceUtil.getScreenHeight());

        popWnd.setOutsideTouchable(true);
        popWnd.setFocusable(true);
        popWnd.showAsDropDown(filterLayout);


    }


    @OnClick({R.id.action_back, R.id.filter_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.filter_tv:
                showFiltePopupWindow();
                break;
        }
    }
}
