package com.yunsen.enjoy.fragment;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.yanzhenjie.permission.Permission;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.adapter.GoodsListAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.common.SpConstants;
import com.yunsen.enjoy.fragment.home.BannerAdapter;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.AdvertModel;
import com.yunsen.enjoy.model.CarDetails;
import com.yunsen.enjoy.model.SProviderModel;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.ui.loadmore.ZyBottomView;
import com.yunsen.enjoy.ui.loopviewpager.AutoLoopViewPager;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.ui.viewpagerindicator.CirclePageIndicator;
import com.yunsen.enjoy.utils.SharedPreference;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.SearchActionBar;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;


/**
 * 首页
 */
public class MainPagerFragment extends BaseFragment implements SearchActionBar.SearchClickListener, MultiItemTypeAdapter.OnItemClickListener, View.OnClickListener {

    private AutoLoopViewPager banner;
    private CirclePageIndicator indicatorLayout;
    private BannerAdapter bannerAdapter;
    private RecyclerView recyclerView;
    //    private View topView;
    private GoodsListAdapter mAdapter;

    private List<CarDetails> mAdverModels = new ArrayList<>();
    private int mPageIndex = 1;
    private View topView;
    private View topNearbyLayout;
    private View nearbyLayout;
    private View vipDiscountTv;
    private View moneyDiscountTv;
    private View topMoneyDiscountTv;
    private View topVipDiscountTv;
    private View deliciousLayout;
    private View entertainmentLayout;
    private View stayLayout;
    private View shareLayout;
    private View experienceLayout;
    private View searchLayout;
    private View qrCodeImg;
    private TwinklingRefreshLayout refreshLayout;
    private boolean mIsLoadMore = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView() {
        searchLayout = rootView.findViewById(R.id.search_layout);
        qrCodeImg = rootView.findViewById(R.id.qrcode_img);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        nearbyLayout = rootView.findViewById(R.id.nearby_layout);
        moneyDiscountTv = rootView.findViewById(R.id.money_discount_tv);
        vipDiscountTv = rootView.findViewById(R.id.vip_discount_tv);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        topView = inflater.inflate(R.layout.fragment_top_layout, null);

        deliciousLayout = topView.findViewById(R.id.delicious_layout);
        entertainmentLayout = topView.findViewById(R.id.entertainment_layout);
        stayLayout = topView.findViewById(R.id.stay_layout);
        experienceLayout = topView.findViewById(R.id.experience_layout);
        shareLayout = topView.findViewById(R.id.share_layout);

        topNearbyLayout = topView.findViewById(R.id.top_nearby_layout);
        topMoneyDiscountTv = topView.findViewById(R.id.top_money_discount_tv);
        topVipDiscountTv = topView.findViewById(R.id.top_vip_discount_tv);

        banner = (AutoLoopViewPager) topView.findViewById(R.id.pager);
        indicatorLayout = ((CirclePageIndicator) topView.findViewById(R.id.indicator));


    }

    @Override
    protected void initData() {
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getActivity());
        refreshLayout.setBottomView(loadingView);

        bannerAdapter = new BannerAdapter(getData(), getActivity());
        banner.setAdapter(bannerAdapter);
        indicatorLayout.setViewPager(banner);
        indicatorLayout.setPadding(5, 5, 10, 5);


        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);

        ArrayList<SProviderModel> storeModes = new ArrayList<>();
        mAdapter = new GoodsListAdapter(getActivity(), R.layout.goods_list_item, storeModes);
        HeaderAndFooterRecyclerViewAdapter recyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, topView);

        String currentCity = SharedPreference.getInstance().getString(SpConstants.CITY_KEY, "深圳市");

    }


    private static final String TAG = "MainPagerFragment";

    @Override
    protected void requestData() {
        HttpProxy.getHomeAdvertList(11, new HttpCallBack<List<AdvertModel>>() {
            @Override
            public void onSuccess(List<AdvertModel> responseData) {
                if (responseData != null && responseData.size() > 0) {
                    bannerAdapter = new BannerAdapter(responseData, getActivity());// TODO: 2018/4/20 need upData方法
                    banner.setAdapter(bannerAdapter);
                    indicatorLayout.setViewPager(banner);
                    indicatorLayout.setPadding(5, 5, 10, 5);
                }
            }

            @Override
            public void onError(Request request, Exception e) {

            }
        });

//        /**
//         * 首页的商品
//         */
//        HttpProxy.getHomeGoods(new HttpCallBack<List<CarDetails>>() {
//            @Override
//            public void onSuccess(List<CarDetails> responseData) {
//                mAdapter.addBaseDatas(responseData);
//            }
//
//            @Override
//            public void onError(Request request, Exception e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//        });
        requestServiceMore();

    }

    public void requestServiceMore() {
        HttpProxy.getServiceMoreProvider(mPageIndex, null, new HttpCallBack<List<SProviderModel>>() {
            @Override
            public void onSuccess(List<SProviderModel> responseData) {
                if (mIsLoadMore) {
                    boolean hasMore = mAdapter.addBaseDatas(responseData);
                    if (!hasMore) {
                        Log.e(TAG, "onSuccess: 没有更多数据");
                        refreshLayout.setBottomView(new ZyBottomView(getActivity()));
                    } else {
                        Log.e(TAG, "onSuccess: 加载更多" );
                        refreshLayout.finishLoadmore();
                    }
                } else {
                    mAdapter.upBaseDatas(responseData);
                    refreshLayout.finishRefreshing();
                    refreshLayout.finishLoadmore();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                if (mIsLoadMore) {
                    refreshLayout.setBottomView(new ZyBottomView(getActivity()));
                } else {
                    refreshLayout.finishRefreshing();
                }
            }
        });
    }

    @Override
    protected void initListener() {
        searchLayout.setOnClickListener(this);
        qrCodeImg.setOnClickListener(this);

        deliciousLayout.setOnClickListener(this);
        entertainmentLayout.setOnClickListener(this);
        stayLayout.setOnClickListener(this);
        experienceLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);

        topMoneyDiscountTv.setOnClickListener(this);
        topVipDiscountTv.setOnClickListener(this);
        moneyDiscountTv.setOnClickListener(this);
        vipDiscountTv.setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            long totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (topNearbyLayout.getY() >= totalDy) {
                    nearbyLayout.setVisibility(View.GONE);
                } else {
                    nearbyLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPageIndex = 1;
                        mIsLoadMore = false;
                        refreshLayout.setBottomView(new LoadingView(getActivity()));
                        requestServiceMore();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsLoadMore = true;
                        mPageIndex++;
                        requestServiceMore();
                    }
                }, 500);

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


    public List<AdvertModel> getData() {
        ArrayList<AdvertModel> data = new ArrayList<>();
        data.add(new AdvertModel(R.mipmap.adv_home, null));
        return data;
    }

    @Override
    public void onSearchClick(SearchActionBar.ViewType type) {
        switch (type) {
            case LEFT_IMG:
                UIHelper.showSelectCityActivity(getActivity());
                break;
            case CENTER_LAYOUT:
                UIHelper.showSearchActivity(getActivity());
                break;
            case RIGHT_IMG:
                ((BaseFragmentActivity) getActivity()).requestPermission(Permission.CALL_PHONE, Constants.CALL_PHONE);
                break;
        }
    }

    private String getAdverModelUrl(int index) {
        if (mAdverModels.size() > index) {
            return String.valueOf(mAdverModels.get(index).getId());
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoScroll();
    }


    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        if (adapter instanceof GoodsListAdapter) {
            List<SProviderModel> datas = ((GoodsListAdapter) adapter).getDatas();
            int pos = position - 1;
            if (pos >= 0 && pos < datas.size()) {
                int id = datas.get(pos).getId();
                UIHelper.showFoodDescriptionActivity(getActivity(), String.valueOf(id), datas.get(pos).getName());
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout:
                UIHelper.showSearchActivity(getActivity());
                break;
            case R.id.qrcode_img:
                ToastUtils.makeTextShort("二维码");
                break;
            case R.id.money_discount_tv: //消费卷优惠
            case R.id.top_money_discount_tv: //消费卷优惠
                ToastUtils.makeTextShort("消费卷优惠");
                break;
            case R.id.top_vip_discount_tv: //会员优惠
            case R.id.vip_discount_tv: //会员优惠
                ToastUtils.makeTextShort("会员优惠");
                break;
            case R.id.delicious_layout:
//                ToastUtils.makeTextShort("美食");
                UIHelper.showGoodsListActivity(getActivity(), "美食", Constants.DELICIOUS_ID);
                break;
            case R.id.entertainment_layout:
                UIHelper.showGoodsListActivity(getActivity(), "娱乐", Constants.ENTERTAINMENT_ID);
//                ToastUtils.makeTextShort("娱乐");
                break;
            case R.id.stay_layout:
                UIHelper.showGoodsListActivity(getActivity(), "住宿", Constants.STAY_ID);
//                ToastUtils.makeTextShort("住宿");
                break;
            case R.id.experience_layout:
                UIHelper.showGoodsListActivity(getActivity(), "商品体验", Constants.EXPERIENCE_ID);
//                ToastUtils.makeTextShort("商品体验");
                break;
            case R.id.share_layout:
                UIHelper.showInvitationFriendActivity(getActivity());
                break;
        }
    }
}
