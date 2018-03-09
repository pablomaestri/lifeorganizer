package com.psm.lifeorganizer.android.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.android.adapters.ActivityItemAdapter;
import com.psm.lifeorganizer.android.adapters.ActivityItemUseAdapter;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.services.ActivityItemService;
import com.psm.lifeorganizer.services.ActivityService;
import com.psm.lifeorganizer.util.DateUtil;
import com.psm.lifeorganizer.util.MessageUtil;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityUseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityUseFragment extends ParentFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTIVITY = "activity";


    private MainActivity mActivity;

    private Activity activity;

    private ExpandableLayout elActivityDetail;
    private LinearLayout llActivityName;

    private TextView tvActivityName;
    private TextView tvActivityDesc;
    private TextView tvActivityDate;
    private TextView tvActivityType;
    private ImageView ivActivityDetail;
    private RecyclerView rvItems;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton fabCompleteActivity;

    private ActivityService activityService;
    private ActivityItemService activityItemService;

    @Inject
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Inject
    public void setActivityItemService(ActivityItemService activityItemService) {
        this.activityItemService = activityItemService;
    }

    public ActivityUseFragment() {
        // Required empty public constructor
    }

    public static ActivityUseFragment newInstance(Activity activity) {
        ActivityUseFragment fragment = new ActivityUseFragment();
        Bundle args = new Bundle();
        args.putParcelable(ACTIVITY, activity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activity = getArguments().getParcelable(ACTIVITY);
        }
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_activity_use, container, false);

        elActivityDetail = layout.findViewById(R.id.el_activity_detail);

        tvActivityName = layout.findViewById(R.id.tv_activity_name);
        tvActivityDesc = layout.findViewById(R.id.tv_activity_desc);
        tvActivityDate = layout.findViewById(R.id.tv_activity_date);
        tvActivityType = layout.findViewById(R.id.tv_activity_type);
        ivActivityDetail = layout.findViewById(R.id.ib_activity_detail);
        rvItems = layout.findViewById(R.id.rv_items_use);

        tvActivityName.setText(activity.getName());
        tvActivityDesc.setText(activity.getDescription());
        tvActivityDate.setText(DateUtil.formatBirthday(activity.getActivityDate()));
        tvActivityType.setText(activity.getActivityType().getName());

        final ActivityItemUseAdapter itemAdapter = new ActivityItemUseAdapter(activity, mActivity, activityItemService);
        rvItems.setAdapter(itemAdapter);
        layoutManager = new LinearLayoutManager(mActivity);
        rvItems.setLayoutManager(layoutManager);

        llActivityName = layout.findViewById(R.id.ll_activity_name);

        llActivityName.setOnClickListener((View v) -> {
            if (elActivityDetail.isExpanded()) {
                elActivityDetail.collapse();
                ivActivityDetail.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            }
            else {
                elActivityDetail.expand();
                ivActivityDetail.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            }
        });

        fabCompleteActivity = layout.findViewById(R.id.fab_complete_activity);

        fabCompleteActivity.setOnClickListener((final View v) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
            alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setTitle("Customized Alert Dialog");
            alertDialogBuilder.setMessage("This is a simple alert dialog.");

            alertDialogBuilder.setPositiveButton("Sí", (DialogInterface dialog, int which) -> {
                activity.setCompleted(true);
                activityService.saveActivity(activity);
                MessageUtil.showSnackbar(v, getString(R.string.message_activity_completed_successfully));
                mActivity.onBackPressed();
            });
            alertDialogBuilder.setNegativeButton("No", (DialogInterface dialog, int which) ->  {
            });

            alertDialogBuilder.show();

        });

        if (activity.isCompleted()) {
            fabCompleteActivity.setVisibility(View.GONE);
        }

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity));
    }

}
