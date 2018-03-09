package com.psm.lifeorganizer.android.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.android.activities.MainActivity;
import com.psm.lifeorganizer.factories.ActivityItemFactory;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends ParentFragment {

    private static final String ITEMS = "items";
    private static final String ITEM = "itemId";

    private MainActivity mActivity;
    private FragmentManager fragmentManager;

    private View layout;
    private EditText etName;
    private EditText etDescription;
    private EditText etQuantity;
    private EditText etPrice;
    private Button btSave;
    private Button btCancel;

    private ActivityItem activityItem;
    private List<ActivityItem> items;

    private ActivityItemFactory activityItemFactory;

    @Inject
    public void setActivityItemFactory(ActivityItemFactory activityItemFactory) {
        this.activityItemFactory = activityItemFactory;
    }

    public ItemFragment() {
        // Required empty public constructor
    }

    public static ItemFragment newInstance(ArrayList<ActivityItem> items, ActivityItem activityItem) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        if (activityItem!=null) {
            args.putParcelable(ITEM, activityItem);
        }
        if (items!=null) {
            args.putParcelableArrayList(ITEMS, items);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activityItem = getArguments().getParcelable(ITEM);
            items = getArguments().getParcelableArrayList(ITEMS);

        }
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_item, container, false);

        fragmentManager = mActivity.getSupportFragmentManager();

        etName = layout.findViewById(R.id.et_item_name);
        etDescription = layout.findViewById(R.id.et_item_description);
        etQuantity = layout.findViewById(R.id.et_item_quantity);
        etPrice = layout.findViewById(R.id.et_item_price);
        btSave = layout.findViewById(R.id.bt_save_item);
        btCancel = layout.findViewById(R.id.bt_cancel_item);

        if (activityItem!=null) {
            etName.setText(activityItem.getName());
            etDescription.setText(activityItem.getDescription());
            etQuantity.setText(activityItem.getQuantity());
        }

        btSave.setOnClickListener((View v) ->  {
            if (etName.getText().toString().isEmpty()) {
                MessageUtil.showSnackbar(v, getString(R.string.message_error_name_empty));
                return;
            }

            Date createdDate = null;
            Integer itemId = null;
            if (activityItem==null) {
                createdDate = new Date();
            }
            else {
                createdDate = activityItem.getCreatedDate();
                itemId = activityItem.getId();
            }
            Double price = null;
            if (!etPrice.getText().toString().isEmpty()) {
                price = Double.parseDouble(etPrice.getText().toString());
            }

            if (items!=null) {
                activityItem = activityItemFactory.create(itemId, etName.getText().toString(),
                        etDescription.getText().toString(), etQuantity.getText().toString(),
                        price, false, createdDate);
                items.add(activityItem);
            }
            else {
                activityItem.setName(etName.getText().toString());
                activityItem.setDescription(etDescription.getText().toString());
                activityItem.setQuantity(etQuantity.getText().toString());
                activityItem.setPriceUnit(price);
                activityItem.setModDate(new Date());
            }

            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            MessageUtil.showSnackbar(v, getString(R.string.message_activity_save_successfully));
            mActivity.onBackPressed();
        });


        btCancel.setOnClickListener((View v) -> mActivity.onBackPressed());

        return layout;
    }


}
