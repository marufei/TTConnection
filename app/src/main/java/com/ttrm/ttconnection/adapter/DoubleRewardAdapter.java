package com.ttrm.ttconnection.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.DoubleRewardBean;
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
public class DoubleRewardAdapter extends BaseAdapter {
    private Context context;
    private List<DoubleRewardBean.DataBean.AddedLogBean> list;

    public DoubleRewardAdapter(List<DoubleRewardBean.DataBean.AddedLogBean> list, Context context) {
        this.context = context;
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
            view = View.inflate(context, R.layout.item_lv_double_reward, null);
            viewHolder.item_double_reward_time1 = (TextView) view.findViewById(R.id.item_double_reward_time1);
            viewHolder.item_double_reward_time2 = (TextView) view.findViewById(R.id.item_double_reward_time2);
            viewHolder.item_double_reward_remark = (TextView) view.findViewById(R.id.item_double_reward_remark);
            viewHolder.item_double_reward_sh = (TextView) view.findViewById(R.id.item_double_reward_sh);
            viewHolder.item_double_reward_notice = (TextView) view.findViewById(R.id.item_double_reward_notice);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_double_reward_remark.setText(list.get(i).getRemark());
        //审核状态1待审核2审核通过3审核不同4拉黑
        switch (list.get(i).getStatus()) {
            case "1":
                viewHolder.item_double_reward_notice.setVisibility(View.GONE);
                viewHolder.item_double_reward_sh.setText("待审核");
                viewHolder.item_double_reward_sh.setTextColor(context.getResources().getColor(R.color.status_dsh));
                if (!TextUtils.isEmpty(list.get(i).getAddtime())) {
                    viewHolder.item_double_reward_time1.setText(DateTimeUtils.formatDateStrToOtherStrLine1(list.get(i).getAddtime()));
                    viewHolder.item_double_reward_time2.setText(DateTimeUtils.formatDateStrToOtherStr16(list.get(i).getAddtime()));
                }
                break;
            case "2":
                viewHolder.item_double_reward_notice.setVisibility(View.GONE);
                viewHolder.item_double_reward_sh.setText("审核通过");
                viewHolder.item_double_reward_sh.setTextColor(context.getResources().getColor(R.color.status_tg));
                if (!TextUtils.isEmpty(list.get(i).getDotime())) {
                    viewHolder.item_double_reward_time1.setText(DateTimeUtils.formatDateStrToOtherStrLine1(list.get(i).getDotime()));
                    viewHolder.item_double_reward_time2.setText(DateTimeUtils.formatDateStrToOtherStr16(list.get(i).getDotime()));
                }
                break;
            case "3":
                viewHolder.item_double_reward_notice.setVisibility(View.VISIBLE);
                viewHolder.item_double_reward_sh.setText("审核不通过");
                viewHolder.item_double_reward_sh.setTextColor(context.getResources().getColor(R.color.status_btg));
                if (!TextUtils.isEmpty(list.get(i).getDotime())) {
                    viewHolder.item_double_reward_time1.setText(DateTimeUtils.formatDateStrToOtherStrLine1(list.get(i).getDotime()));
                    viewHolder.item_double_reward_time2.setText(DateTimeUtils.formatDateStrToOtherStr16(list.get(i).getDotime()));
                }
                break;
            case "4":
                viewHolder.item_double_reward_notice.setVisibility(View.VISIBLE);
                viewHolder.item_double_reward_sh.setText("拉入黑名单");
                viewHolder.item_double_reward_sh.setTextColor(context.getResources().getColor(R.color.gray_3));
                if (!TextUtils.isEmpty(list.get(i).getDotime())) {
                    viewHolder.item_double_reward_time1.setText(DateTimeUtils.formatDateStrToOtherStrLine1(list.get(i).getDotime()));
                    viewHolder.item_double_reward_time2.setText(DateTimeUtils.formatDateStrToOtherStr16(list.get(i).getDotime()));
                }
                break;
            default:
                viewHolder.item_double_reward_notice.setVisibility(View.GONE);
                viewHolder.item_double_reward_sh.setText("--");
                viewHolder.item_double_reward_sh.setTextColor(context.getResources().getColor(R.color.gray_3));
                if (!TextUtils.isEmpty(list.get(i).getDotime())) {
                    viewHolder.item_double_reward_time1.setText(DateTimeUtils.formatDateStrToOtherStrLine1(list.get(i).getDotime()));
                    viewHolder.item_double_reward_time2.setText(DateTimeUtils.formatDateStrToOtherStr16(list.get(i).getDotime()));
                }
                break;
        }
        return view;
    }

    class ViewHolder {
        TextView item_double_reward_time1, item_double_reward_time2, item_double_reward_remark, item_double_reward_sh, item_double_reward_notice;
    }
}
