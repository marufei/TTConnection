package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.InventBean;
import com.ttrm.ttconnection.entity.InventCodeEntity;
import com.ttrm.ttconnection.util.DateTimeUtils;
import com.ttrm.ttconnection.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaRufei
 * on 2018/2/7.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class InventCodeLvAdapter extends BaseAdapter {
    private Context context;
    private List<InventCodeEntity.DataBean.OrderListBean> list = new ArrayList<>();

    public InventCodeLvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<InventCodeEntity.DataBean.OrderListBean> list) {
        this.list = list;
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
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_invent_code, null);
            viewHolder.item_invent_code_pic = view.findViewById(R.id.item_invent_code_pic);
            viewHolder.item_invent_code_time = view.findViewById(R.id.item_invent_code_time);
            viewHolder.item_invent_code_title = view.findViewById(R.id.item_invent_code_title);
            viewHolder.item_invent_code_all = view.findViewById(R.id.item_invent_code_all);
            viewHolder.item_invent_code_left = view.findViewById(R.id.item_invent_code_left);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.item_invent_code_title.setText(list.get(i).getRemark());
        viewHolder.item_invent_code_all.setText(list.get(i).getCodegroup()+"个");
        viewHolder.item_invent_code_left.setText(list.get(i).getRestnum()+"个");


        viewHolder.item_invent_code_time.setText( MyUtils.getStandardDate(list.get(i).getAddtime()));
        Picasso.with(context).load(list.get(i).getImgurl()).error(R.mipmap.tt_error).into(viewHolder.item_invent_code_pic);
        return view;
    }

    class ViewHolder {
        TextView item_invent_code_title, item_invent_code_all, item_invent_code_left, item_invent_code_time;
        ImageView item_invent_code_pic;
    }
}
