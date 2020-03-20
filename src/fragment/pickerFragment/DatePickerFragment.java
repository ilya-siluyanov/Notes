package com.example.isilu.notes.fragment.pickerFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.isilu.notes.R;
import com.example.isilu.notes.model.FormatUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
public class DatePickerFragment extends DialogFragment {
    public static final int REQUEST_CODE_DATE_PICK;
    public static final String DATE_EXTRA;
    private static final String ARG_DATE;
    static {
        REQUEST_CODE_DATE_PICK = 0;
        DATE_EXTRA = "date_extra";
        ARG_DATE = "argument_date";
    }
    private DatePicker picker;
    private Calendar calendar;

    public static DatePickerFragment newInstance(String date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createNewDialog();
    }

    private void setCurrentDate() {
        Date date = FormatUtils.getParsedDate(Objects.requireNonNull(getArguments().getString(ARG_DATE)));
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker, i, i1, i2) -> calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()));
    }

    private Dialog createNewDialog() {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null, false);
        this.picker = (DatePicker) view.findViewById(R.id.date_picker);
        setCurrentDate();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i1) -> getTargetFragment().onActivityResult(REQUEST_CODE_DATE_PICK, RESULT_OK, new Intent().putExtra(DATE_EXTRA, FormatUtils.getFormattedDate(calendar.getTime()))))
                .create();
    }
}
