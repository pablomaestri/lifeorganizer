package com.psm.lifeorganizer.android.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;

import com.psm.lifeorganizer.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pablomaestri on 30/12/16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    private DatePickerListener listener;

    public DatePickerFragment() {

    }

    public DatePickerListener getListener() {
        return listener;
    }

    public void setListener(DatePickerListener listener) {
        this.listener = listener;
    }

    public static DatePickerFragment newInstance(DatePickerListener listener) {

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);

        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datePicker, this, year, month, day);

        return datePickerDialog;

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        listener.onDateSet(view, year, month, day);
        // Do something with the date chosen by the user
        /*CreateUserActivity activity = (CreateUserActivity) getActivity();
        Calendar birthdayCalendar = new GregorianCalendar(year, month, day);
        activity.getEtBirthday().setText(DateUtil.formatBirthday(birthdayCalendar.getTime()));*/
    }

    public interface DatePickerListener {
        void onDateSet(DatePicker view, int year, int month, int day);
    }

}
