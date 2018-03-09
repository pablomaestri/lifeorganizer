package com.psm.lifeorganizer.android.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.android.adapters.ActivityAdapter;
import com.psm.lifeorganizer.android.adapters.ActivityItemAdapter;
import com.psm.lifeorganizer.android.helpers.RecyclerItemTouchHelper;
import com.psm.lifeorganizer.factories.ActivityFactory;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.models.Enum.ActivityStyle;
import com.psm.lifeorganizer.models.Enum.ActivityType;
import com.psm.lifeorganizer.services.ActivityService;
import com.psm.lifeorganizer.util.DateUtil;
import com.psm.lifeorganizer.util.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends ParentFragment implements DatePickerFragment.DatePickerListener,
        ColorPickerFragment.ColorPickerListener,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTIVITY_ID = "activity_id";

    private ActivityService activityService;
    private ActivityFactory activityFactory;

    private Integer activityId;
    private Activity activity;

    private FragmentManager fragmentManager;
    private MainActivity mActivity;

    private View layout;
    private CheckBox cbSaveTemplate;
    private EditText etName;
    private EditText etDescription;
    private EditText etDate;
    private ImageButton ibColor;
    private ImageView ivColor;
    private Spinner spType;
    private RecyclerView rvItems;
    private LinearLayoutManager layoutManager;
    private ImageView ivDate;
    private FloatingActionButton fabItem;
    private Button btSave;
    private Button btCancel;

    private static final String DEFAULT_COLOR = "#ff000000";

    private String colorSelected;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Inject
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Inject
    public void setActivityFactory(ActivityFactory activityFactory) {
        this.activityFactory = activityFactory;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param activityId Parameter 1.
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance(Integer activityId) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        if (activityId!=null) {
            args.putInt(ACTIVITY_ID, activityId);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (!getArguments().isEmpty()) {
                activityId = getArguments().getInt(ACTIVITY_ID);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_activity, container, false);
        mActivity = (MainActivity) getActivity();
        fragmentManager = mActivity.getSupportFragmentManager();

        cbSaveTemplate = layout.findViewById(R.id.cb_save_template);
        etName = layout.findViewById(R.id.et_activity_name);
        etDescription = layout.findViewById(R.id.et_description);
        etDate = layout.findViewById(R.id.et_activity_date);
        ivDate = layout.findViewById(R.id.ib_activity_date);
        ivColor = layout.findViewById(R.id.iv_activity_color);
        ibColor = layout.findViewById(R.id.ib_activity_color);
        spType = layout.findViewById(R.id.sp_activity_type);
        rvItems = layout.findViewById(R.id.rv_items);
        fabItem = layout.findViewById(R.id.fab_add_item);
        btSave = layout.findViewById(R.id.bt_save_activity);
        btCancel = layout.findViewById(R.id.bt_cancel_activity);

        if (activity==null) {
            if (activityId!=null) {

                getActivity(activityId);
                etName.setText(activity.getName());
                etDescription.setText(activity.getDescription());
                etDate.setText(DateUtil.formatBirthday(activity.getActivityDate()));
                spType.setSelection(activity.getActivityType().ordinal());
                colorSelected = activity.getColor();
                cbSaveTemplate.setVisibility(View.GONE);
            }
            else {
                activity = activityFactory.createEmpty();
                colorSelected = DEFAULT_COLOR;
            }
        }

        View.OnClickListener openDateDialog = (View view) -> showDatePickerDialog();
        etDate.setOnClickListener(openDateDialog);
        ivDate.setOnClickListener(openDateDialog);

        GradientDrawable drawable = generateCircle(colorSelected);
        ivColor.setImageDrawable(drawable);
        ibColor.setOnClickListener(this::showColorPickerDialog);


        ArrayAdapter<ActivityType> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, Arrays.asList(ActivityType.values()));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(dataAdapter);

        final ActivityItemAdapter itemAdapter = new ActivityItemAdapter((List<ActivityItem>) activity.getItems(), mActivity);
        rvItems.setAdapter(itemAdapter);
        layoutManager = new LinearLayoutManager(mActivity);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this ) ;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvItems);


        fabItem.setOnClickListener((View v) ->  {
            fragmentManager.beginTransaction().replace(R.id.container,
                    ItemFragment.newInstance(((ArrayList<ActivityItem>)((ActivityItemAdapter)rvItems.getAdapter()).getItems()),
                            null)).addToBackStack("item_fragment").commit();
            mActivity.showUpButton(true);
        });

        btSave.setOnClickListener((View v) -> {

            if (etName.getText().toString().isEmpty()) {
                MessageUtil.showSnackbar(v, getString(R.string.message_error_name_empty));
                return;
            }

            List<ActivityItem> items = ((ActivityItemAdapter) rvItems.getAdapter()).getItems();

            if (items.isEmpty()) {
                MessageUtil.showSnackbar(v, getString(R.string.message_error_items_empty));
                return;
            }

            activity = activityFactory.create(activityId, etName.getText().toString(), etDescription.getText().toString(), DateUtil.parseBirthday(etDate.getText().toString()),
                    items, false, ActivityStyle.NORMAL, (ActivityType) spType.getSelectedItem(), colorSelected, new Date());

            for (ActivityItem item:items) {
                item.setActivity(activity);
            }

            activityService.saveActivity(activity);

            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            MessageUtil.showSnackbar(v, getString(R.string.message_activity_save_successfully));
            mActivity.onBackPressed();

        });

        btCancel.setOnClickListener((View v) -> mActivity.onBackPressed());

        return layout;
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = DatePickerFragment.newInstance(this);
        newFragment.show(mActivity.getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar birthdayCalendar = new GregorianCalendar(year, month, day);
        etDate.setText(DateUtil.formatBirthday(birthdayCalendar.getTime()));
    }

    public void showColorPickerDialog(View v) {

        ColorPickerFragment newFragment = ColorPickerFragment.newInstance(this);
        newFragment.show(mActivity.getSupportFragmentManager(), "colorPicker");
    }

    @Override
    public void onColorSet(String color) {
        colorSelected = color;
        GradientDrawable drawable = generateCircle(color);
        ivColor.setImageDrawable(drawable);
    }

    private GradientDrawable generateCircle(String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor(color));
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(2, Color.parseColor(color));
        drawable.setSize(65, 65);

        return drawable;
    }

    private void getActivity(int activityId) {
        activity = activityService.getActivity(activityId);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //Borrar elemento
        if (viewHolder instanceof ActivityItemAdapter.CustomViewHolder) {
            ActivityItemAdapter adapter = (ActivityItemAdapter) rvItems.getAdapter();
            ActivityItem item = adapter.getItem(position);

            //TODO: mostrar advertencia si es necesario

            //remover del adapter
            adapter.removeItem(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_activity));
    }

}
