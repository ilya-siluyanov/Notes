package com.example.isilu.notes.fragment.pickerFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.isilu.notes.R;
import com.example.isilu.notes.model.Time;

import static android.app.Activity.RESULT_OK;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "extra_time";
    public static final int REQUEST_CODE_TIME_PICK = 1;
    private TimePicker picker;

    public static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressWarnings("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_picker, null, false);
        picker = (TimePicker) view.findViewById(R.id.time_picker);
        picker.setIs24HourView(true);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, getListener())
                .create();
    }

    private DialogInterface.OnClickListener getListener() {
        return (dialogInterface, i) ->
                getTargetFragment().
                        onActivityResult(REQUEST_CODE_TIME_PICK, RESULT_OK,
                                new Intent().putExtra(EXTRA_TIME, new Time(picker.getHour(), picker.getMinute()).toString()));
    }
}
