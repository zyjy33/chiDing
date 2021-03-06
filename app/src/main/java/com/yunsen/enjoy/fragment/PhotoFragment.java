package com.yunsen.enjoy.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.adapter.PhotoImgAdapter;
import com.yunsen.enjoy.model.PhotoInfo;
import com.yunsen.enjoy.ui.UIHelper;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/4/004.
 */

public class PhotoFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private ArrayList<PhotoInfo> mDatas;
    private PhotoImgAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mDatas = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            PhotoInfo info = new PhotoInfo("", "图片" + i);
            info.setResId(R.mipmap.ic_launcher);
            mDatas.add(info);

        }
        mAdapter = new PhotoImgAdapter(getActivity(), R.layout.photo_and_tv_item, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        UIHelper.showPhotoBrowseActivity(getActivity(), mDatas, position);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
