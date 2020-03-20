package com.example.isilu.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.isilu.notes.fragment.NewNoteFragment;
import com.example.isilu.notes.fragment.NoteFragment;
import com.example.isilu.notes.listener.BackKeyListener;

import java.util.UUID;

import static com.example.isilu.notes.logger.LogHelper.log;

public class NoteActivity extends SingleActivity {
    private static final String EXTRA_ID = "extra_id";

    public static Intent intent(Context packageContext, UUID uuid) {
        return new Intent(packageContext, NoteActivity.class).putExtra(EXTRA_ID, uuid.toString());
    }

    public static Intent intent(Context packageContext) {
        return new Intent(packageContext, NoteActivity.class);
    }


    @Override
    Fragment getFragment() {
        log(this.getClass(), "getFragment");
        if (getIntent().getStringExtra(EXTRA_ID) == null) return NewNoteFragment.newInstance();
        return NoteFragment.newInstance(getIntent().getStringExtra(EXTRA_ID));
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        BackKeyListener listener = null;
        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof BackKeyListener) listener = (BackKeyListener) fragment;
        }
        if (listener != null) listener.onBackPressed();
    }
}
