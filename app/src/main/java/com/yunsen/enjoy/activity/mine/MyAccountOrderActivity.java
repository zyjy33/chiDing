package com.yunsen.enjoy.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.BaseFragmentActivity;
import com.yunsen.enjoy.activity.mine.adapter.CheckItemAdapter;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.model.CheckedData;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.ToastUtils;
import com.yunsen.enjoy.widget.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.current_filter_tv)
    TextView currentFilterTv;
    @Bind(R.id.filter_layout)
    LinearLayout filterLayout;
    private PopupWindow popWnd;

    @Override
    public int getLayout() {
        return R.layout.activity_my_account_order;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        actionBarTitle.setText("我的账单");
    }

    @Override
    protected void initListener() {

    }

    private static final String TAG = "MyAccountOrderActivity";

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
            data1.add(new CheckedData("0", "全部", false));
            data1.add(new CheckedData("1", "充值", false));
            data1.add(new CheckedData("2", "消费", false));
            data1.add(new CheckedData("3", "赠送", false));
            data1.add(new CheckedData("4", "转赠", false));
            data1.add(new CheckedData("5", "退款", false));
            final CheckItemAdapter adapter1 = new CheckItemAdapter(this, R.layout.checked_item, data1);
            recyclerContent.setAdapter(adapter1);

            RecyclerView recyclerTime = (RecyclerView) contentView.findViewById(R.id.recycler_time);
            GridLayoutManager layoutManager2 = new GridLayoutManager(this, 5);
            recyclerTime.setLayoutManager(layoutManager2);
            ArrayList<CheckedData> data2 = new ArrayList<>();
            data2.add(new CheckedData("0", "全部", false));
            data2.add(new CheckedData("1", "近一周", false));
            data2.add(new CheckedData("2", "近一月", false));
            data2.add(new CheckedData("3", "近三月", false));
            data2.add(new CheckedData("4", "三月前", false));
            final CheckItemAdapter adapter2 = new CheckItemAdapter(this, R.layout.checked_item, data2);
            recyclerTime.setAdapter(adapter2);

            RecyclerView recyclerType = (RecyclerView) contentView.findViewById(R.id.recycler_type);
            GridLayoutManager layoutManager3 = new GridLayoutManager(this, 4);
            recyclerType.setLayoutManager(layoutManager3);
            ArrayList<CheckedData> data3 = new ArrayList<>();
            data3.add(new CheckedData("0", "全部", false));
            data3.add(new CheckedData("1", "吃币", false));
            data3.add(new CheckedData("2", "云币", false));
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
                }
            });
            contentView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: bbbb");
                    CheckedData contentModel = adapter1.getCurrentModel();
                    CheckedData timeModel = adapter2.getCurrentModel();
                    CheckedData typeModel = adapter3.getCurrentModel();

//                    if (contentModel == null && timeModel == null && typeModel == null) {
//                        currentFilterTv.setText("全部订单");
//                    }
                    StringBuffer buffer = new StringBuffer();

                    if (timeModel != null) {
                        buffer.append(timeModel.getName());
                    } else {
                        buffer.append("全部");
                    }
                    if (contentModel != null) {
                        buffer.append(contentModel.getName());
                    }

                    if (typeModel != null) {
                        buffer.append(typeModel.getName());
                    }
                    buffer.append("账单");
                    currentFilterTv.setText(buffer.toString());
                }
            });


            adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    if (adapter instanceof CheckItemAdapter) {
                        ((CheckItemAdapter) adapter).setSelected(position);
                        String name = ((CheckItemAdapter) adapter).getDatas().get(position).getName();
                        ToastUtils.makeTextShort(name);
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
                        ToastUtils.makeTextShort(name);
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
                        ToastUtils.makeTextShort(name);
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });

        }
        popWnd.setWidth(DeviceUtil.getWidth(this));
        popWnd.setHeight(DeviceUtil.getHeight(this));
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
