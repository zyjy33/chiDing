package com.yunsen.enjoy.activity.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.buy.GoodsListActivity;
import com.yunsen.enjoy.activity.mine.adapter.TeamInfoAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.HttpCallBack;
import com.yunsen.enjoy.http.HttpProxy;
import com.yunsen.enjoy.model.ListOrderCountBean;
import com.yunsen.enjoy.model.TeamInfoBean;
import com.yunsen.enjoy.model.TeamItemBean;
import com.yunsen.enjoy.ui.loadmore.ZyBottomView;
import com.yunsen.enjoy.ui.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yunsen.enjoy.ui.recyclerview.LoadMoreLayout;
import com.yunsen.enjoy.ui.recyclerview.RecyclerViewUtils;
import com.yunsen.enjoy.widget.FlowLayout;
import com.yunsen.enjoy.widget.NoticeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/8/2/002.
 */

public class SpreadFragment extends BaseFragment {

    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.notice_view)
    NoticeView noticeView;
    private int fragmentType;
    private TeamInfoAdapter mAdapter;
    private int mPageIndex = 1;
    private boolean mIsLoadMore;
    private boolean mHasMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_spread;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        fragmentType = bundle.getInt(Constants.FRAGMENT_TYPE_KEY);
        if (fragmentType == Constants.VIP_TAG) {

        } else if (fragmentType == Constants.SHOPPING_TAG) {

        }
    }

    @Override
    protected void initData() {
        SinaRefreshView headerView = new SinaRefreshView(getActivity());
        refreshLayout.setHeaderView(headerView);
        LoadingView loadingView = new LoadingView(getActivity());
        refreshLayout.setBottomView(loadingView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TeamInfoAdapter(getActivity(), R.layout.team_info_item, new ArrayList<TeamItemBean>());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        HttpProxy.getFriendRing(String.valueOf(mPageIndex), new HttpCallBack<TeamInfoBean>() {
            @Override
            public void onSuccess(TeamInfoBean responseData) {
                if (mIsLoadMore) {
                    boolean hasMore = mAdapter.addBaseDatas(responseData.getListModel());
                    if (hasMore) {
                        refreshLayout.finishLoadmore();
                    } else {
                        refreshLayout.setBottomView(new ZyBottomView(getActivity()));
                    }
                } else {
                    mAdapter.upBaseDatas(responseData.getListModel());
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
                if (mPageIndex == 1) {
                    recyclerView.setVisibility(View.GONE);
                    noticeView.showNoticeType(NoticeView.Type.NO_DATA );
                }
            }
        });
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadingView loadingView = new LoadingView(getActivity());
                        refreshLayout.setBottomView(loadingView);
                        mIsLoadMore = false;
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
                        mIsLoadMore = true;
                        mPageIndex++;
                        requestData();
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
