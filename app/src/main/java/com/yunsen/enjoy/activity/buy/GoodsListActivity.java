package com.yunsen.enjoy.activity.buy;

import android.content.Context;
import android.content.Intent;
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
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.adapter.ImageAndTextAdapter;
import com.yunsen.enjoy.adapter.SelectStringAdapter;
import com.yunsen.enjoy.adapter.TypeListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.SelectStringModel;
import com.yunsen.enjoy.model.TradeData;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loadmore.ZyBottomView;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/3/003.
 */

public class GoodsListActivity extends BaseFragmentActivity implements View.OnClickListener, MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_back_title)
    TextView actionBackTitle;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.qrcode_img)
    ImageView qrcodeImg;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.above_title_layout)
    LinearLayout aboveTitleLayout;
    @Bind(R.id.above_all_type)
    TextView aboveAllType;
    @Bind(R.id.above_nearby_tv)
    TextView aboveNearbyTv;
    @Bind(R.id.above_sort_tv)
    TextView aboveSortTv;


    private View topView;
    private GoodsListAdapter mAdapter;
    private RecyclerView mRecyclerTop;
    private View topTitleLayout;
    private TextView topAllType;
    private TextView topNearbyType;
    private TextView topSortType;
    private String mTitle;
    private int mTypeId;
    private PopupWindow mTypePopupWindow;
    private PopupWindow mNearByPopup;
    private PopupWindow mSortPopup;
    private ArrayList<SProviderModel> mDatas;
    private int mPageIndex = 1;
    private boolean mIsLoadMore = true;
    private ArrayList<TradeData> mTopDatas;
    private ImageAndTextAdapter mTopAdapter;
    private int mTreadId = 0;
    private String mOrderStrValue = "clever";
    private String mDistanceValue = "";


    @Override
    public int getLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topView = inflater.inflate(R.layout.goods_list_head_layout, null);
        mRecyclerTop = topView.findViewById(R.id.recycler_top_view);
        topTitleLayout = topView.findViewById(R.id.top_title_layout);
        topAllType = topView.findViewById(R.id.top_all_type);
        topNearbyType = topView.findViewById(R.id.top_nearby_tv);
        topSortType = topView.findViewById(R.id.top_sort_tv);
        SinaRefreshView headerView = new SinaRefreshView(this);
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(this);
        refreshLayout.setBottomView(loadingView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(Constants.GOODS_LIST_TITLE);
        mTypeId = intent.getIntExtra(Constants.GOODS_LIST_TYPE, 0);
        if (mTitle == null) {
            mTitle = Constants.EMPTY;
        }
        actionBackTitle.setText(mTitle);
        mRecyclerTop.setLayoutManager(new GridLayoutManager(this, 4));
        mTopDatas = new ArrayList<TradeData>();
        mTopAdapter = new ImageAndTextAdapter(this, R.layout.img_and_text_layout_2, mTopDatas);
        mRecyclerTop.setAdapter(mTopAdapter);
        switch (mTypeId) {
            case Constants.DELICIOUS_ID: //美食
                break;
            case Constants.ENTERTAINMENT_ID://娱乐
                mRecyclerTop.setVisibility(View.GONE);
                break;
            case Constants.STAY_ID://住宿
                mRecyclerTop.setVisibility(View.GONE);
                break;
            case Constants.EXPERIENCE_ID: //商品体验
                mRecyclerTop.setVisibility(View.GONE);
                break;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAdapter = new GoodsListAdapter(this, R.layout.goods_list_item, mDatas);
        HeaderAndFooterRecyclerViewAdapter viewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(viewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);


    }

    private int totalDy = 0;

    private static final String TAG = "GoodsListActivity";

    @Override
    protected void initListener() {
        topAllType.setOnClickListener(this);
        topNearbyType.setOnClickListener(this);
        topSortType.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                Log.e(TAG, "onScrolled: totalDy=" + totalDy + " dy=" + dy);
                if (topTitleLayout.getY() >= totalDy) {
                    aboveTitleLayout.setVisibility(View.GONE);
                } else {
                    aboveTitleLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        requestList(String.valueOf(mTreadId), true);

                    }
                }, 500);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        requestList(String.valueOf(mTreadId), false);
                    }
                }, 500);
            }
        });

        mTopAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                List<TradeData> datas = mTopAdapter.getDatas();
                if (datas != null && datas.size() > position) {
                    TradeData tradeData = datas.get(position);
                    mTreadId = tradeData.getId();
                    mPageIndex = 1;
                    mIsLoadMore = false;
                    topAllType.setText(tradeData.getTitle());
                    topAllType.setText(tradeData.getTitle());
                    requestList(String.valueOf(mTreadId), true);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void requestData() {
//        HttpProxy.getChangeGoodsList(String.valueOf(mPageIndex), "", "", new HttpCallBack<List<CarDetails>>() {
//            @Override
//            public void onSuccess(List<CarDetails> responseData) {
//                mAdapter.upBaseDatas(responseData);
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//        });
        HttpProxy.getTypeTradeList(String.valueOf(mTypeId), new HttpCallBack<List<TradeData>>() {
            @Override
            public void onSuccess(List<TradeData> responseData) {
                if (responseData == null || responseData.size() == 0) {
                    mRecyclerTop.setVisibility(View.GONE);
                } else {
                    mRecyclerTop.setVisibility(View.VISIBLE);
                    mTopAdapter.upBaseDatas(responseData);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                mRecyclerTop.setVisibility(View.GONE);
                if (!mIsLoadMore) {
                    mTopAdapter.upBaseDatas(new ArrayList<TradeData>());
                }
            }
        });

        requestList(String.valueOf(mTreadId), true);

    }

    private void requestList(String treadId, boolean isUpData) {
        if (isUpData) {
            refreshLayout.setBottomView(new LoadingView(GoodsListActivity.this));
            mPageIndex = 1;
            mIsLoadMore = false;
        } else {
            mIsLoadMore = true;
            mPageIndex++;
        }
        HttpProxy.getServiceMoreProvider(mPageIndex, null, treadId, mOrderStrValue, mDistanceValue, new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                if (mIsLoadMore) {
                    boolean hasMore = mAdapter.addBaseDatas(responseData);
                    if (hasMore) {
                        refreshLayout.finishLoadmore();
                    } else {
                        refreshLayout.setBottomView(new ZyBottomView(GoodsListActivity.this));
                    }
                } else {
                    mAdapter.upBaseDatas(responseData);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            aboveTitleLayout.setVisibility(View.GONE);
                            recyclerView.scrollBy(0, -totalDy);
                            totalDy =0;
                        }
                    });
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if (mIsLoadMore) {
                    refreshLayout.setBottomView(new ZyBottomView(GoodsListActivity.this));
                } else {
                    refreshLayout.finishRefreshing();
                }
            }
        });
    }

    public int getScollYDistance(RecyclerView recy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recy.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }


    @OnClick({R.id.action_back, R.id.search_layout, R.id.qrcode_img,
            R.id.above_all_type, R.id.above_nearby_tv, R.id.above_sort_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.search_layout:
                UIHelper.showSearchActivity(this);
                break;
            case R.id.qrcode_img:
                ToastUtils.makeTextShort("二维码");
                break;
            case R.id.above_all_type:
                showTypePopupWindow();
                break;
            case R.id.above_nearby_tv:
                showNearByPopupWindow();
                break;
            case R.id.above_sort_tv:
                showSortPopupWindow();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_all_type:
                showTypePopupWindow();
                break;
            case R.id.top_nearby_tv:
                showNearByPopupWindow();
                break;
            case R.id.top_sort_tv:
                showSortPopupWindow();
                break;
        }
    }

    /**
     * 显示全部分类列表
     */
    private void showTypePopupWindow() {
        if (mTopDatas == null) {
            return;
        }
        if (mTypePopupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.type_popup_layout, null);
            RecyclerView typeRecycler = (RecyclerView) view.findViewById(R.id.recycler_type);
            typeRecycler.setLayoutManager(new LinearLayoutManager(this));

            TypeListAdapter adapter = new TypeListAdapter(this, R.layout.type_item_layout, mTopDatas);
            typeRecycler.setAdapter(adapter);
            mTypePopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mTypePopupWindow.setFocusable(true);
            mTypePopupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof TypeListAdapter) {
                        TradeData tradeData = ((TypeListAdapter) adapter).getDatas().get(position);
                        String data = tradeData.getTitle();
                        topAllType.setText(data);
                        aboveAllType.setText(data);
                        mTypePopupWindow.dismiss();
                        mTreadId = tradeData.getId();
                        requestList(String.valueOf(mTreadId), true);
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });


        }
        if (aboveTitleLayout.getVisibility() == View.VISIBLE) {
            mTypePopupWindow.showAsDropDown(aboveAllType);
        } else {
            mTypePopupWindow.showAsDropDown(topAllType);
        }
    }

    /**
     * 显示附近的popup
     */
    private void showNearByPopupWindow() {
        if (mNearByPopup == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.nearby_layout_popup, null);
            RecyclerView nearByRecycler = (RecyclerView) view.findViewById(R.id.recycler_nearby);
            nearByRecycler.setLayoutManager(new LinearLayoutManager(this));


            ArrayList<SelectStringModel> models = new ArrayList<>();
            models.add(new SelectStringModel("附近", "<5"));
            models.add(new SelectStringModel("<10Km", "<10"));
            models.add(new SelectStringModel("<30Km", "<30"));
            models.add(new SelectStringModel("<50Km", "<50"));
            models.add(new SelectStringModel("<70Km", "<70"));
            models.add(new SelectStringModel("全部", Constants.EMPTY));
            SelectStringAdapter adapter = new SelectStringAdapter(this, R.layout.select_string_item, models);
            nearByRecycler.setAdapter(adapter);

            mNearByPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mNearByPopup.setFocusable(true);
            mNearByPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof SelectStringAdapter) {
                        SelectStringModel model = ((SelectStringAdapter) adapter).getDatas().get(position);
                        String data = model.getName();
                        aboveNearbyTv.setText(data);
                        topNearbyType.setText(data);
                        ((SelectStringAdapter) adapter).selectItem(position);
                        mDistanceValue = model.getValue();
                        requestList(String.valueOf(mTreadId), true);
                        mNearByPopup.dismiss();
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

        }

        if (aboveTitleLayout.getVisibility() == View.VISIBLE) {
            mNearByPopup.showAsDropDown(aboveNearbyTv);
        } else {
            mNearByPopup.showAsDropDown(topNearbyType);
        }
    }

    /**
     * 智能排序
     */
    private void showSortPopupWindow() {
        if (mSortPopup == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.nearby_layout_popup, null);
            RecyclerView nearByRecycler = (RecyclerView) view.findViewById(R.id.recycler_nearby);
            nearByRecycler.setLayoutManager(new LinearLayoutManager(this));


            ArrayList<SelectStringModel> models = new ArrayList<>();
            models.add(new SelectStringModel("智能排序", "clever"));
            models.add(new SelectStringModel("好评优先", "pralse"));
            models.add(new SelectStringModel("离我最近", "lately"));
            models.add(new SelectStringModel("人气最高", "buzz"));

            SelectStringAdapter adapter = new SelectStringAdapter(this, R.layout.select_string_item, models);
            nearByRecycler.setAdapter(adapter);

            mSortPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mSortPopup.setFocusable(true);
            mSortPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof SelectStringAdapter) {
                        SelectStringModel model = ((SelectStringAdapter) adapter).getDatas().get(position);
                        String data = model.getName();
                        aboveSortTv.setText(data);
                        topSortType.setText(data);

                        ((SelectStringAdapter) adapter).selectItem(position);
                        mOrderStrValue = model.getValue();
                        requestList(String.valueOf(mTreadId), true);
                        mSortPopup.dismiss();
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

        if (aboveTitleLayout.getVisibility() == View.VISIBLE) {
            mSortPopup.showAsDropDown(aboveSortTv);
        } else {
            mSortPopup.showAsDropDown(topSortType);
        }
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        int currentPos = position - 1;
        if (currentPos >= 0 && currentPos < mDatas.size()) {
            SProviderModel data = mDatas.get(currentPos);
            UIHelper.showFoodDescriptionActivity(this, String.valueOf(data.getUser_id()), data.getShop_name());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
