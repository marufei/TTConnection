package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.InviteBean;
import com.ttrm.ttconnection.entity.WithdrawInfoBean;
import com.ttrm.ttconnection.util.DateTimeUtils;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/17.
 */

public class InviteInfoAdapter extends BaseAdapter {
    private Context context;
    private List<InviteBean.DataBean.RecomLogBean> beanList;

    public InviteInfoAdapter(Context context, List<InviteBean.DataBean.RecomLogBean> beanList) {
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
            view = View.inflate(context, R.layout.item_invite, null);
            viewHolder.tv_time = (TextView) view.findViewById(R.id.item_invite_time);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.item_invite_title);
            viewHolder.tv_price=(TextView)view.findViewById(R.id.item_invite_price);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String str_time = DateTimeUtils.formateDate1(Long.valueOf(beanList.get(i).getAddtime()));
        viewHolder.tv_title.setText(beanList.get(i).getRemark());
        viewHolder.tv_time.setText(str_time);
        if(beanList.get(i).getFee()>=0) {
            viewHolder.tv_price.setText("+"+String.valueOf(beanList.get(i).getDiamond()));
        }else {
            viewHolder.tv_price.setText(String.valueOf(beanList.get(i).getDiamond()));
        }
        return view;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_price;
    }
}
