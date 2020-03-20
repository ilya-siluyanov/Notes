package com.example.isilu.notes.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isilu.notes.R;
import com.example.isilu.notes.data.database.DatabaseSource;
import com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment;
import com.example.isilu.notes.fragment.pickerFragment.TimePickerFragment;
import com.example.isilu.notes.listener.BackKeyListener;
import com.example.isilu.notes.listener.ResultCallback;
import com.example.isilu.notes.model.Note;
import com.example.isilu.notes.model.Time;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment.DATE_EXTRA;
import static com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment.REQUEST_CODE_DATE_PICK;
import static com.example.isilu.notes.fragment.pickerFragment.TimePickerFragment.REQUEST_CODE_TIME_PICK;

public class NoteFragment extends Fragment implements BackKeyListener, ResultCallback {
    private static final String ARG_ID = "argument_id";
    private Note note;
    private View view;
    private TextView description;
    private TextView date;
    private TextView time;
    private TextView toolbarTitle;
    public NoteFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String id) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initializeFields(inflater, container);
        setValues();
        setListeners();
        return view;
    }

    private void initializeFields(LayoutInflater inflater, ViewGroup container) {
        UUID id = UUID.fromString(getArguments().getString(ARG_ID));
        note = DatabaseSource.getSource(getActivity()).getNote(id);
        view = inflater.inflate(R.layout.note_details, container, false);
        description = (TextView) view.findViewById(R.id.details_desc);
        date = (TextView) view.findViewById(R.id.details_date);
        time = (TextView)view.findViewById(R.id.details_time);
    }

    private void setValues() {
        toolbarTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbarTitle.setText(note.getTitle());
        description.setText(note.getDescription());
        date.setText(note.getDate());
        time.setText(note.getTime().toString());
    }

    private void setListeners() {
        toolbarTitle.setOnClickListener(i->{
            @SuppressLint("InflateParams") EditText editText = (EditText) LayoutInflater.from(getActivity()).inflate(R.layout.change_field, null, false);
            editText.setText(toolbarTitle.getText());
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.change_title)
                    .setView(editText)
                    .setPositiveButton(android.R.string.ok, (j, k) -> {
                        toolbarTitle.setText(editText.getText());
                        note.setTitle(editText.getText().toString());
                    }).show();
        });
        description.setOnClickListener(i -> {
            @SuppressLint("InflateParams") EditText editText = (EditText) LayoutInflater.from(getActivity()).inflate(R.layout.change_field, null, false);
            editText.setText(description.getText());
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.change_description)
                    .setView(editText)
                    .setPositiveButton(android.R.string.ok, (j, k) -> {
                        description.setText(editText.getText());
                        note.setDescription(editText.getText().toString());
                    }).show();
        });
        date.setOnClickListener(i -> {
            DialogFragment fragment = DatePickerFragment.newInstance(note.getDate());
            fragment.setTargetFragment(this, REQUEST_CODE_DATE_PICK);
            fragment.show(getFragmentManager(), null);
        });
        time.setOnClickListener(i -> {
            DialogFragment fragment = TimePickerFragment.newInstance();
            fragment.setTargetFragment(this, REQUEST_CODE_TIME_PICK);
            fragment.show(getFragmentManager(), null);
        });
    }

    @Override
    public void setResult() {
        DatabaseSource.getSource(getActivity()).updateNote(note);
        getActivity().finish();
    }

    @Override
    public void onBackPressed() {
        setResult();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DATE_PICK && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DATE_EXTRA);
            note.setDate(date);
            this.date.setText(date);
        }
        if (requestCode == REQUEST_CODE_TIME_PICK && resultCode == RESULT_OK) {
            Time time = new Time(data.getStringExtra(TimePickerFragment.EXTRA_TIME));
            note.setTime(time);
            this.time.setText(time.toString());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_note,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult();
        return super.onOptionsItemSelected(item);
    }
}
