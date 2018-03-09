package com.psm.lifeorganizer.android.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.flask.colorpicker.ColorPickerView;
import com.psm.lifeorganizer.R;

import java.util.Calendar;

/**
 * Created by pablomaestri on 30/12/16.
 */

public class ColorPickerFragment extends DialogFragment {


    private ColorPickerListener listener;

    private ColorPickerView colorPickerView;

    public ColorPickerListener getListener() {
        return listener;
    }

    public void setListener(ColorPickerListener listener) {
        this.listener = listener;
    }

    static ColorPickerFragment newInstance(ColorPickerListener listener) {

        ColorPickerFragment fragment = new ColorPickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_color_picker, container, false);

        colorPickerView = layout.findViewById(R.id.color_picker_view);

        Button btSetColor = layout.findViewById(R.id.bt_set_color);
        btSetColor.setOnClickListener((View v) -> {
            listener.onColorSet("#" + Integer.toHexString(colorPickerView.getSelectedColor()));
            dismiss();
        });

        Button btCancelColor = layout.findViewById(R.id.bt_cancel_color);
        btCancelColor.setOnClickListener((View v) -> dismiss());

        return layout;
    }

    public interface ColorPickerListener {
        void onColorSet(String color);
    }

}
