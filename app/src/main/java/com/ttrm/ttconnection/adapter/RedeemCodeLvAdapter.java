package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.BaojiRuleBean;
import com.ttrm.ttconnection.entity.RedeemCodeBean;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/7.
 */

public class RedeemCodeLvAdapter extends BaseAdapter {
    private Context context;
    private List<RedeemCodeBean.DataBean.CodeListBean> listBeen;
    private IRedeemCodeListener iRedeemCodeListener;

    public IRedeemCodeListener getiRedeemCodeListener() {
        return iRedeemCodeListener;
    }

    public void setiRedeemCodeListener(IRedeemCodeListener iRedeemCodeListener) {
        this.iRedeemCodeListener = iRedeemCodeListener;
    }

    public RedeemCodeLvAdapter(Context context, List<RedeemCodeBean.DataBean.CodeListBean> listBeen){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=View.inflate(context, R.layout.item_redeem_code,null);
            viewHolder.item_redeem_code= (TextView) view.findViewById(R.id.item_redeem_code);
            viewHolder.item_redeem_status=(TextView)view.findViewById(R.id.item_redeem_status);
            viewHolder.item_redeem_copy=(TextView)view.findViewById(R.id.item_redeem_copy);
            viewHolder.item_redeem_delete=(TextView)view.findViewById(R.id.item_redeem_delete);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.item_redeem_code.setText(listBeen.get(i).getCode());
        if(listBeen.get(i).getStatus().equals("1")){
            viewHolder.item_redeem_status.setText("未使用");
            viewHolder.item_redeem_delete.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.item_redeem_status.setText("已使用");
            viewHolder.item_redeem_delete.setVisibility(View.VISIBLE);
        }
        viewHolder.item_redeem_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRedeemCodeListener.onCopy(i);
            }
        });
        viewHolder.item_redeem_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iRedeemCodeListener.onDelete(i);
            }
        });


        return view;
    }
    public class ViewHolder{
        TextView item_redeem_code,item_redeem_status,item_redeem_copy,item_redeem_delete;
    }
    public interface IRedeemCodeListener{
        void onCopy(int i);
        void onDelete(int i);
    }
}
