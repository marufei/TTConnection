package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.BDAddBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class BDAddLvAdapter extends BaseAdapter {
    private Context context;
    private List<BDAddBean.DataBean.RuleListBean> listBean;
    public BDAddLvAdapter(Context context,List<BDAddBean.DataBean.RuleListBean> listBean){
        this.context=context;
        this.listBean=listBean;
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
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.item_bdadd_lv,null);
            viewHolder.tv_num= (TextView) convertView.findViewById(R.id.item_bdadd_num);
            viewHolder.tv_price=(TextView)convertView.findViewById(R.id.item_bdadd_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        switch (position){
            case 0:
                viewHolder.tv_num.setText("被加"+listBean.get(position).getAddcount()+"人左右（体验）");
                break;
            case 1:
                viewHolder.tv_num.setText("保底被加"+listBean.get(position).getAddcount()+"人");
                break;
            case 2:
                viewHolder.tv_num.setText("保底被加"+listBean.get(position).getAddcount()+"人（赠送1680钻）");
                break;
            default:
                viewHolder.tv_num.setText("保底被加"+listBean.get(position).getAddcount()+"人（赠送1680钻）");
                break;

        }
//        viewHolder.tv_num.setText("被加"+listBean.get(position).getAddcount()+"人左右（体验）");
        viewHolder.tv_price.setText("￥"+listBean.get(position).getDiamondcount());
        if(listBean.get(position).isSelect()){
            viewHolder.tv_num.setBackgroundResource(R.drawable.boder_org);

        }else {
            viewHolder.tv_num.setBackgroundResource(R.drawable.boder_gray);
        }

        return convertView;
    }
    class ViewHolder{
        TextView tv_price;
        TextView tv_num;
    }
}
