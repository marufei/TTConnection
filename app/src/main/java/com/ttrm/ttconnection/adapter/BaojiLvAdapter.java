package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.BaojiRuleBean;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/7.
 */

public class BaojiLvAdapter extends BaseAdapter {
    private Context context;
    private List<BaojiRuleBean.DataBean.RuleListBean> listBeen;
    public BaojiLvAdapter(Context context, List<BaojiRuleBean.DataBean.RuleListBean> listBeen){
        this.context=context;
        this.listBeen=listBeen;
    }
    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeen.get(i);
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
            view=View.inflate(context, R.layout.item_baoji_lv,null);
            viewHolder.tv_item= (TextView) view.findViewById(R.id.item_tv);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_item.setText(listBeen.get(i).getDiamondcount()+"颗钻石，爆机"+listBeen.get(i).getAddcount()+"人左右");
        if(listBeen.get(i).isType()){
            viewHolder.tv_item.setBackgroundResource(R.color.white_F0F0F0);
        }else {
            viewHolder.tv_item.setBackgroundResource(R.color.white);
        }
        return view;
    }
    public class ViewHolder{
        TextView tv_item;
    }
}
