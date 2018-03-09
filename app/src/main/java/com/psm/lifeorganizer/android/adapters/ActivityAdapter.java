package com.psm.lifeorganizer.android.adapters;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.android.fragments.ActivityFragment;
import com.psm.lifeorganizer.android.fragments.ActivityUseFragment;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.Enum.ActivityType;
import com.psm.lifeorganizer.services.ActivityService;
import com.psm.lifeorganizer.util.DateUtil;
import com.psm.lifeorganizer.util.MessageUtil;

import java.util.List;

/**
 * Created by pmaestri on 1/31/2018.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.CustomViewHolder> {

    private List<Activity> activityList;
    private MainActivity mContext;
    private ActivityService activityService;

    private View layout;

    private FragmentManager fragmentManager;

    public ActivityAdapter(MainActivity context, List<Activity> activities, ActivityService activityService) {
        this.activityList = activities;
        this.mContext = context;
        this.activityService = activityService;

        fragmentManager = this.mContext.getSupportFragmentManager();


    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
        notifyDataSetChanged();
    }

    public void addListActivity(List<Activity> activities) {
        activityList.addAll(activities);
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity, null);

        return new CustomViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        final Activity activity = activityList.get(position);
        holder.getTvActivityName().setText(activity.getName());
        holder.getTvActivityDate().setText(DateUtil.formatBirthday(activity.getActivityDate()));

        //set color
        holder.getIvActivityIcon().setColorFilter(Color.parseColor(activity.getColor()));
        holder.getTvActivityNameLabel().setTextColor(Color.parseColor(activity.getColor()));
        holder.getTvActivityName().setTextColor(Color.parseColor(activity.getColor()));
        holder.getTvActivityDateLabel().setTextColor(Color.parseColor(activity.getColor()));
        holder.getTvActivityDate().setTextColor(Color.parseColor(activity.getColor()));

        if (activity.getActivityType().equals(ActivityType.ACTION)) {
            holder.getIvActivityIcon().setImageResource(R.drawable.ic_event_black_24dp);
        }
        else if (activity.getActivityType().equals(ActivityType.ELEMENT)) {
            holder.getIvActivityIcon().setImageResource(R.drawable.ic_assignment_black_24dp);
        }

        layout.setOnClickListener((View view) -> {
            fragmentManager.beginTransaction().replace(R.id.container, ActivityUseFragment
                    .newInstance(activity))
                    .addToBackStack("activity_fragment").commit();
            mContext.showUpButton(true);
        });

        if (!activity.isCompleted()) {
            layout.setOnLongClickListener((View v) -> {
                fragmentManager.beginTransaction().replace(R.id.container, ActivityFragment
                        .newInstance(activity.getId()))
                        .addToBackStack("activity_fragment").commit();
                mContext.showUpButton(true);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != activityList ? activityList.size() : 0);
    }

    public Activity getItem(int position) {
        return activityList.get(position);
    }

    public void removeItem(int position) {
        activityList.remove(position);
        notifyItemRemoved(position);
    }

    public void clearData() {
        activityList.clear();
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends ParentCustomViewHolder {

        private ImageView ivActivityIcon;
        private TextView tvActivityNameLabel;
        private TextView tvActivityName;
        private TextView tvActivityDateLabel;
        private TextView tvActivityDate;

        public ImageView getIvActivityIcon() {
            return ivActivityIcon;
        }

        public TextView getTvActivityNameLabel() {
            return tvActivityNameLabel;
        }

        public TextView getTvActivityName() {
            return tvActivityName;
        }

        public TextView getTvActivityDateLabel() {
            return tvActivityDateLabel;
        }

        public TextView getTvActivityDate() {
            return tvActivityDate;
        }

        public CustomViewHolder(View view) {
            super(view);

            this.ivActivityIcon = view.findViewById(R.id.iv_activity_icon);
            this.tvActivityNameLabel = view.findViewById(R.id.tv_activity_name_label);
            this.tvActivityName = view.findViewById(R.id.tv_activity_name);
            this.tvActivityDateLabel = view.findViewById(R.id.tv_activity_date_label);
            this.tvActivityDate = view.findViewById(R.id.tv_activity_date);

            this.viewForeground = view.findViewById(R.id.view_foreground);
            this.viewBackground = view.findViewById(R.id.view_background);
        }
    }

}
