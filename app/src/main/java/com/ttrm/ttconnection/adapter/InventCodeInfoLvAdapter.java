package com.ttrm.ttconnection.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.InventCodeEntity;
import com.ttrm.ttconnection.entity.InventCodeInfoEntity;
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
public class InventCodeInfoLvAdapter extends BaseAdapter {
    private Context context;
    private List<InventCodeInfoEntity.DataBean.CodeListBean> list = new ArrayList<>();

    public InventCodeInfoLvAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<InventCodeInfoEntity.DataBean.CodeListBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.item_code_info, null);
            viewHolder.item_code_info_copy = view.findViewById(R.id.item_code_info_copy);
            viewHolder.item_code_info_num = view.findViewById(R.id.item_code_info_num);
            viewHolder.item_code_info_status = view.findViewById(R.id.item_code_info_status);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        switch (list.get(i).getStatus()) {
            case "1":
                viewHolder.item_code_info_status.setText("未使用");
                viewHolder.item_code_info_status.setTextColor(context.getResources().getColor(R.color.gray_3));
                break;
            case "2":
                viewHolder.item_code_info_status.setText("已使用");
                viewHolder.item_code_info_status.setTextColor(context.getResources().getColor(R.color.red_left));
                break;
            case "3":
                viewHolder.item_code_info_status.setText("已复制");
                viewHolder.item_code_info_status.setTextColor(Color.BLUE);
                break;
        }
        viewHolder.item_code_info_num.setText(list.get(i).getCode());


        viewHolder.item_code_info_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", list.get(i).getCode());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                MyUtils.showToast(context, "已复制到剪切板");
                list.get(i).setStatus("3");
                notifyDataSetChanged();


            }
        });

        return view;
    }

    class ViewHolder {
        TextView item_code_info_copy, item_code_info_num, item_code_info_status;
    }
}
