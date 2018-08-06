package com.yunsen.enjoy.activity.buy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.model.SelectStringModel;
import com.yunsen.enjoy.model.UsedFunction;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.utils.DeviceUtil;
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


        switch (mTypeId) {
            case Constants.DELICIOUS_ID: //美食
                mRecyclerTop.setLayoutManager(new GridLayoutManager(this, 4));
                ArrayList<UsedFunction> datas1 = new ArrayList<>();
                datas1.add(new UsedFunction(R.mipmap.btn_qubu, "全部"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "中餐"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "火锅"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "西餐"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "自助餐"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "水果"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "宵夜"));
                datas1.add(new UsedFunction(R.mipmap.app_icon, "烧烤"));
                mRecyclerTop.setAdapter(new ImageAndTextAdapter(this, R.layout.img_and_text_layout_2, datas1));
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

    private static final String TAG = "GoodsListActivity";

    @Override
    protected void initListener() {
        topAllType.setOnClickListener(this);
        topNearbyType.setOnClickListener(this);
        topSortType.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
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
                        mIsLoadMore=false;
                        mPageIndex = 1;
                        requestData();

                    }
                }, 500);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsLoadMore=true;
                        mPageIndex++;
                        requestData();
                    }
                }, 500);
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

        HttpProxy.getServiceMoreProvider(mPageIndex, null, new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                if (mIsLoadMore) {
                    boolean hasMore = mAdapter.addBaseDatas(responseData);
//                    ToastUtils.makeTextShort("没有更多数据");
                    refreshLayout.finishLoadmore();
                } else {
                    mAdapter.upBaseDatas(responseData);
                    refreshLayout.finishRefreshing();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if (mIsLoadMore) {
                    refreshLayout.finishLoadmore();
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
        if (mTypePopupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.type_popup_layout, null);
            RecyclerView typeRecycler = (RecyclerView) view.findViewById(R.id.recycler_type);
            typeRecycler.setLayoutManager(new LinearLayoutManager(this));
            ArrayList<String> datas = new ArrayList<>();
            datas.add("全部");
            datas.add("中餐");
            datas.add("火锅");
            datas.add("西餐");
            datas.add("自助餐");
            datas.add("水果");
            datas.add("夜宵");
            datas.add("烧烤");
            TypeListAdapter adapter = new TypeListAdapter(this, R.layout.type_item_layout, datas);
            typeRecycler.setAdapter(adapter);
            mTypePopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mTypePopupWindow.setFocusable(true);
            mTypePopupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof TypeListAdapter) {
                        String data = ((TypeListAdapter) adapter).getDatas().get(position);
                        topAllType.setText(data);
                        aboveAllType.setText(data);
                        mTypePopupWindow.dismiss();
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
            models.add(new SelectStringModel("附近"));
            models.add(new SelectStringModel("<10Km"));
            models.add(new SelectStringModel("<30Km"));
            models.add(new SelectStringModel("<50Km"));
            models.add(new SelectStringModel("<70Km"));
            models.add(new SelectStringModel("全部"));
            SelectStringAdapter adapter = new SelectStringAdapter(this, R.layout.select_string_item, models);
            nearByRecycler.setAdapter(adapter);

            mNearByPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mNearByPopup.setFocusable(true);
            mNearByPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof SelectStringAdapter) {
                        String data = ((SelectStringAdapter) adapter).getDatas().get(position).getName();
                        aboveNearbyTv.setText(data);
                        topNearbyType.setText(data);
                        ((SelectStringAdapter) adapter).selectItem(position);
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
            models.add(new SelectStringModel("智能排序"));
            models.add(new SelectStringModel("最近距离"));
            SelectStringAdapter adapter = new SelectStringAdapter(this, R.layout.select_string_item, models);
            nearByRecycler.setAdapter(adapter);

            mSortPopup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mSortPopup.setFocusable(true);
            mSortPopup.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.empty));
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof SelectStringAdapter) {
                        String data = ((SelectStringAdapter) adapter).getDatas().get(position).getName();
                        aboveSortTv.setText(data);
                        topSortType.setText(data);
                        ((SelectStringAdapter) adapter).selectItem(position);
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
        if (position > 0 && position < mDatas.size() + 1) {
            SProviderModel data = mDatas.get(position);
            UIHelper.showFoodDescriptionActivity(this, String.valueOf(data.getUser_id()), data.getName());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
