package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.InventBean;
import com.ttrm.ttconnection.util.DateTimeUtils;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/2/7.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class InventLvAdapter extends BaseAdapter {
    private Context context;
    private List<InventBean.DataBean.RecomLogBean> list;
    public InventLvAdapter(List<InventBean.DataBean.RecomLogBean> list,Context context){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.item_lv_invent,null);
            viewHolder.item_invent_time= (TextView) view.findViewById(R.id.item_invent_time);
            viewHolder.item_invent_title=(TextView)view.findViewById(R.id.item_invent_title);
            viewHolder.item_invent_zs=(TextView)view.findViewById(R.id.item_invent_zs);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.item_invent_title.setText(list.get(i).getRemark());
        viewHolder.item_invent_time.setText(DateTimeUtils.time2Date(Long.valueOf(list.get(i).getAddtime())*1000));
        viewHolder.item_invent_zs.setText("+"+list.get(i).getReward()+"钻");
        return view;
    }

    class ViewHolder{
        TextView item_invent_title,item_invent_time,item_invent_zs;
    }
}
