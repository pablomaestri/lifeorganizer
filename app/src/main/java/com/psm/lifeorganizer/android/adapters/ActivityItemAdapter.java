package com.psm.lifeorganizer.android.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.android.fragments.ItemFragment;
import com.psm.lifeorganizer.models.ActivityItem;

import java.util.List;

/**
 * Created by pmaestri on 2/15/2018.
 */

public class ActivityItemAdapter extends RecyclerView.Adapter<ActivityItemAdapter.CustomViewHolder> {


    private List<ActivityItem> items;
    private MainActivity mContext;
    private FragmentManager fragmentManager;

    private View layout;

    public ActivityItemAdapter(List<ActivityItem> items, MainActivity mContext) {
        this.items = items;
        this.mContext = mContext;
        fragmentManager = this.mContext.getSupportFragmentManager();
    }

    public void addItem(ActivityItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<ActivityItem> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_item, null);

        return new ActivityItemAdapter.CustomViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        ActivityItem item = items.get(position);
        holder.getTvName().setText(item.getName());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fragmentManager.beginTransaction().replace(R.id.container, ItemFragment
                        .newInstance(null, items.get(position)))
                        .addToBackStack("activity_fragment").commit();
                mContext.showUpButton(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomViewHolder extends ParentCustomViewHolder {


        private TextView tvName;

        public TextView getTvName() {
            return tvName;
        }


        public CustomViewHolder(View view) {
            super(view);

            this.tvName = view.findViewById(R.id.tv_item_name);
            this.viewForeground = view.findViewById(R.id.view_foreground_item);
            this.viewBackground = view.findViewById(R.id.view_background_item);

        }
    }

    public List<ActivityItem> getItems() {
        notifyDataSetChanged();
        return items;
    }

    public ActivityItem getItem(int position) {
        return items.get(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();

    }
}
