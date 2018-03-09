package com.psm.lifeorganizer.android.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.inject.Inject;
import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.android.adapters.ActivityAdapter;
import com.psm.lifeorganizer.android.helpers.RecyclerItemTouchHelper;
import com.psm.lifeorganizer.common.GralConstants;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.services.ActivityService;
import com.psm.lifeorganizer.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesFragment extends ParentFragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private static final String SHOW_COMPLETED = "showCompleted";

    private MainActivity activity;

    private View layout;
    private RecyclerView rvActivities;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeActiviriesContainer;

    private ActivityService activityService;

    private FragmentManager fragmentManager;

    private int page;

    private boolean showCompleted;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ActivitiesFragment newInstance(boolean showCompleted) {
        ActivitiesFragment fragment = new ActivitiesFragment();
        Bundle args = new Bundle();
        args.putBoolean(SHOW_COMPLETED, showCompleted);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showCompleted = getArguments().getBoolean(SHOW_COMPLETED);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_activities, container, false);
        activity = (MainActivity) getActivity();
        fragmentManager = activity.getSupportFragmentManager();

        // Inflate the layout for this fragment

        page = 1;

        // Initialize recycler view
        rvActivities = layout.findViewById(R.id.rv_activities);
        final ActivityAdapter activityAdapter = new ActivityAdapter(activity, new ArrayList<Activity>(), activityService);
        rvActivities.setAdapter(activityAdapter);
        layoutManager = new LinearLayoutManager(activity);
        rvActivities.setLayoutManager(layoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));



        rvActivities.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (layoutManager.findLastCompletelyVisibleItemPosition()== (GralConstants.SECOND_TOTAL_ITEMS_PAGE * page - 1)) {
                        page++;
                        getActivities();
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        //https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this ) ;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvActivities);

        progressBar = layout.findViewById(R.id.pg_activities);
        progressBar.setVisibility(View.VISIBLE);

        swipeActiviriesContainer = layout.findViewById(R.id.swComments);
        swipeActiviriesContainer.setVisibility(View.GONE);
        swipeActiviriesContainer.setOnRefreshListener(() -> {
            ActivityAdapter adapter = (ActivityAdapter) rvActivities.getAdapter();
            adapter.clearData();
            page = 1;
            getActivities();
        });

        FloatingActionButton fab = layout.findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> {
            fragmentManager.beginTransaction().replace(R.id.container, ActivityFragment
                    .newInstance(null)).addToBackStack("activity_fragment").commit();
            activity.showUpButton(true);
        });

        getActivities();

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (showCompleted) {
            toolbar.setTitle(getResources().getString(R.string.title_activities_completed));
        }
        else {
            toolbar.setTitle(getResources().getString(R.string.title_activities));
        }

    }

    private void getActivities() {

        List<Activity> activities = activityService.getActivities(showCompleted, page);
        ActivityAdapter adapter = (ActivityAdapter)  rvActivities.getAdapter();
        adapter.addListActivity(activities);

        swipeActiviriesContainer.setRefreshing(false);
        swipeActiviriesContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        //Borrar elemento
        if (viewHolder instanceof ActivityAdapter.CustomViewHolder) {
            ActivityAdapter adapter = (ActivityAdapter) rvActivities.getAdapter();
            Activity activity = adapter.getItem(position);

            //TODO: mostrar advertencia si es necesario

            //remover de la base
            activityService.removeActivity(activity);

            //remover del adapter
            adapter.removeItem(position);
            MessageUtil.showSnackbar(layout, getString(R.string.message_activity_remove_successfully));
        }
    }

}
