package com.example.isilu.notes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.isilu.notes.R;
import com.example.isilu.notes.data.database.DatabaseSource;
import com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment;
import com.example.isilu.notes.fragment.pickerFragment.TimePickerFragment;
import com.example.isilu.notes.listener.BackKeyListener;
import com.example.isilu.notes.listener.ResultCallback;
import com.example.isilu.notes.model.FormatUtils;
import com.example.isilu.notes.model.Note;
import com.example.isilu.notes.model.Time;

import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment.DATE_EXTRA;
import static com.example.isilu.notes.fragment.pickerFragment.DatePickerFragment.REQUEST_CODE_DATE_PICK;
import static com.example.isilu.notes.fragment.pickerFragment.TimePickerFragment.REQUEST_CODE_TIME_PICK;
import static com.example.isilu.notes.model.FormatUtils.getFormattedDate;
import static com.example.isilu.notes.model.FormatUtils.getFormattedTime;

public class NewNoteFragment extends Fragment implements BackKeyListener, ResultCallback {
    private Note note;
    private View view;
    private EditText title;
    private EditText description;
    private Button dateButton;
    private Button timeButton;

    public static Fragment newInstance() {
        return new NewNoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initializeFields(inflater, container);
        setListeners();
        setValues();
        return view;
    }

    private void initializeFields(LayoutInflater inflater, ViewGroup container) {
        note = new Note();

        view = inflater.inflate(R.layout.new_note, container, false);

        title = (EditText) view.findViewById(R.id.new_note_title);
        description = (EditText) view.findViewById(R.id.new_note_desc);
        dateButton = (Button) view.findViewById(R.id.new_note_date_picker_button);
        timeButton = (Button) view.findViewById(R.id.new_note_time_picker_button);
    }

    private void setListeners() {
        title.addTextChangedListener(new TextWatcher() {
            int maxLength = Integer.parseInt(getString(R.string.maxCountOfTitleLength));
            private String sourceString;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sourceString = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() < maxLength)
                    note.setTitle(charSequence.toString());
                else {
                    title.setText(sourceString);
                    Toast.makeText(getActivity(), getString(R.string.too_long_string, maxLength), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        dateButton.setOnClickListener(i -> {
            DialogFragment fragment = DatePickerFragment.newInstance(FormatUtils.getFormattedDate(new Date()));
            fragment.setTargetFragment(this, REQUEST_CODE_DATE_PICK);
            fragment.show(getFragmentManager(), null);
        });

        timeButton.setOnClickListener(i -> {
            DialogFragment fragment = TimePickerFragment.newInstance();
            fragment.setTargetFragment(this, REQUEST_CODE_TIME_PICK);
            fragment.show(getFragmentManager(), null);
        });
    }

    private void setValues() {
        note.setDate(FormatUtils.getFormattedDate(new Date()));
        note.setTime(new Time(getFormattedTime(new Date())));
        dateButton.setText(getFormattedDate(new Date()));
        timeButton.setText(getFormattedTime(new Date()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DATE_PICK && resultCode == RESULT_OK) {
            String date = data.getStringExtra(DATE_EXTRA);
            note.setDate(date);
            dateButton.setText(date);
        }
        if (requestCode == REQUEST_CODE_TIME_PICK && resultCode == RESULT_OK) {
            Time time = new Time(data.getStringExtra(TimePickerFragment.EXTRA_TIME));
            note.setTime(time);
            timeButton.setText(time.toString());
        }

    }

    @Override
    public void onBackPressed() {
        setResult();
    }

    @Override
    public void setResult() {
        if (!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty())
            DatabaseSource.getSource(getActivity()).addNote(note);
        getActivity().finish();
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
