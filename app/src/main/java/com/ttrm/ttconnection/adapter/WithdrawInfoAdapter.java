package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.WithdrawInfoBean;
import com.ttrm.ttconnection.util.DateTimeUtils;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/17.
 */

public class WithdrawInfoAdapter extends BaseAdapter {
    private Context context;
    private List<WithdrawInfoBean.DataBean.CashLogBean> beanList;

    public WithdrawInfoAdapter(Context context, List<WithdrawInfoBean.DataBean.CashLogBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int i) {
        return beanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_withdraw, null);
            viewHolder.tv_time = (TextView) view.findViewById(R.id.item_withdraw_time);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.item_withdraw_title);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String str_time = DateTimeUtils.formatDate(beanList.get(i).getAddtime());
        viewHolder.tv_title.setText(beanList.get(i).getRemark());
        viewHolder.tv_time.setText(str_time);
        return view;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_time;
    }
}
