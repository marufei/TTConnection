package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.BDAddBean;
import com.ttrm.ttconnection.entity.OpenVipBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class OpenVipLvAdapter extends BaseAdapter {
    private Context context;
    private List<OpenVipBean.DataBean.RuleListBean> listBean;

    public OpenVipLvAdapter(Context context, List<OpenVipBean.DataBean.RuleListBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_bdadd_lv, null);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.item_bdadd_num);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.item_bdadd_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_num.setText(listBean.get(position).getRemark());
        viewHolder.tv_price.setText("￥" + listBean.get(position).getSaleprice());
        if (listBean.get(position).isSelect()) {
            viewHolder.tv_num.setBackgroundResource(R.drawable.boder_org);

        } else {
            viewHolder.tv_num.setBackgroundResource(R.drawable.boder_gray);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_price;
        TextView tv_num;
    }
}
