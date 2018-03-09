package com.psm.lifeorganizer.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.services.ActivityItemService;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

/**
 * Created by pmaestri on 2/28/2018.
 */

public class ActivityItemUseAdapter extends RecyclerView.Adapter<ActivityItemUseAdapter.CustomViewHolder> {


    private Activity activity;
    private List<ActivityItem> items;
    private MainActivity mContext;
    private ActivityItemService activityItemService;

    private View layout;

    public ActivityItemUseAdapter(Activity activity, MainActivity mContext, ActivityItemService activityItemService) {
        this.activity = activity;
        this.items = activity.getItems();
        this.mContext = mContext;
        this.activityItemService = activityItemService;
    }

    public void addItem(ActivityItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<ActivityItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_item_use, null);

        return new ActivityItemUseAdapter.CustomViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        final ActivityItem item = items.get(position);
        holder.getTvName().setText(item.getName());
        if (item.getDescription()!=null) {
            holder.getTvDesc().setText(item.getDescription());
        }
        else {
            holder.getLlItemDesc().setVisibility(View.GONE);
        }
        if (item.getQuantity()!=null) {
            holder.getTvQuantity().setText(item.getQuantity());
        }
        else {
            holder.getLlItemQuantity().setVisibility(View.GONE);
        }
        if (item.getPriceUnit()!=null) {
            holder.getTvPrice().setText(item.getPriceUnit().toString());
        }
        else {
            holder.getLlItemPrice().setVisibility(View.GONE);
        }
        if (item.isCompleted()) {
            checkItem(holder);
        }
        else {
            uncheckItem(holder);
        }

        if (activity.isCompleted()) {
            holder.getIvItemCompleted().setVisibility(View.GONE);
        }
        else {
            holder.llItemName.setOnClickListener((View v) -> {
                item.setCompleted(!item.isCompleted());
                if (item.isCompleted()) {
                    checkItem(holder);
                }
                else {
                    uncheckItem(holder);
                }
                activityItemService.saveActivityItem(item);
            });
        }

        holder.getIbItemDetail().setOnClickListener((View v) -> {
            if (holder.getElItemDetail().isExpanded()) {
                holder.getElItemDetail().collapse();
                holder.getIbItemDetail().setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
            else {
                holder.getElItemDetail().expand();
                holder.getIbItemDetail().setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            }

        });
    }

    private void checkItem(CustomViewHolder holder) {
        holder.getIvItemCompleted().setImageResource(R.drawable.ic_circle_checked_black_24dp);
        holder.getLlItemName().setBackgroundColor(mContext.getResources().getColor(R.color.colorItemCompleted));
        holder.getElItemDetail().setBackgroundColor(mContext.getResources().getColor(R.color.colorItemCompleted));
    }

    private void uncheckItem(CustomViewHolder holder) {
        holder.getIvItemCompleted().setImageResource(R.drawable.ic_circle_unchecked_black_24dp);
        holder.getLlItemName().setBackgroundColor(mContext.getResources().getColor(R.color.colorItemUncompleted));
        holder.getElItemDetail().setBackgroundColor(mContext.getResources().getColor(R.color.colorItemUncompleted));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvDesc;
        private TextView tvQuantity;
        private TextView tvPrice;
        private ExpandableLayout elItemDetail;
        private ImageButton ibItemDetail;
        private LinearLayout llItemName;
        private LinearLayout llItemQuantity;
        private LinearLayout llItemDesc;
        private LinearLayout llItemPrice;
        private ImageView ivItemCompleted;

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvDesc() {
            return tvDesc;
        }

        public TextView getTvQuantity() {
            return tvQuantity;
        }

        public TextView getTvPrice() {
            return tvPrice;
        }

        public ExpandableLayout getElItemDetail() {
            return elItemDetail;
        }

        public ImageButton getIbItemDetail() {
            return ibItemDetail;
        }

        public LinearLayout getLlItemName() {
            return llItemName;
        }

        public LinearLayout getLlItemQuantity() {
            return llItemQuantity;
        }

        public LinearLayout getLlItemDesc() {
            return llItemDesc;
        }

        public LinearLayout getLlItemPrice() {
            return llItemPrice;
        }

        public ImageView getIvItemCompleted() {
            return ivItemCompleted;
        }

        public CustomViewHolder(View layout) {
            super(layout);

            tvName = layout.findViewById(R.id.tv_item_name);
            tvDesc = layout.findViewById(R.id.tv_item_desc);
            tvQuantity = layout.findViewById(R.id.tv_item_quantity);
            tvPrice = layout.findViewById(R.id.tv_item_price);
            elItemDetail = layout.findViewById(R.id.el_item_detail);
            ibItemDetail = layout.findViewById(R.id.ib_item_detail);
            llItemName = layout.findViewById(R.id.ll_item_name);
            llItemQuantity = layout.findViewById(R.id.ll_item_quantity);
            llItemDesc = layout.findViewById(R.id.ll_item_desc);
            llItemPrice = layout.findViewById(R.id.ll_item_price);
            ivItemCompleted = layout.findViewById(R.id.iv_item_completed);
        }
    }

}
