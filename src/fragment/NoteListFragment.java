package com.example.isilu.notes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.isilu.notes.R;
import com.example.isilu.notes.activity.NoteActivity;
import com.example.isilu.notes.data.database.DatabaseSource;
import com.example.isilu.notes.model.Note;

import java.util.List;

import static com.example.isilu.notes.logger.LogHelper.log;

public class NoteListFragment extends Fragment {

    private View emptyLayout;
    private View recyclerLayout;
    private ViewGroup container;
    private boolean isEmptyLayout;
    private NoteAdapter adapter;
    private DatabaseSource source;
    private View.OnClickListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log(this.getClass(), "onCreateView");
        this.recyclerLayout = inflater.inflate(R.layout.list_view, container, false);
        this.emptyLayout = inflater.inflate(R.layout.empty_layout, container, false);
        this.container = container;
        this.source = DatabaseSource.getSource(getActivity());
        this.listener = i -> {
            Intent intent = NoteActivity.intent(getContext());
            startActivity(intent);
        };
        initializeRecyclerView();
        initializeEmptyLayout();
        adapter.setNoteList(source.getNotes());
        isEmptyLayout = !(adapter.getNoteList().size() > 0);
        return isEmptyLayout ? emptyLayout : recyclerLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void initializeRecyclerView() {
        log(this.getClass(), "initializeRecyclerView");
        RecyclerView recyclerView = (RecyclerView) recyclerLayout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNoteList(source.getNotes());
    }

    private void initializeEmptyLayout() {
        log(this.getClass(), "initializeEmptyLayout");
        Button button = (Button) emptyLayout.findViewById(R.id.create_new_note_on_empty_layout);
        button.setOnClickListener(listener);
    }

    void updateUI() {
        getActivity().invalidateOptionsMenu();
        List<Note> notes = source.getNotes();
        if (isEmptyLayout) {
            if (notes.size() > 0) {
                setRecyclerLayout();
            }
        } else {
            if (notes.isEmpty()) {
                setEmptyLayout();
            }
        }
        adapter.setNoteList(notes);
        adapter.notifyDataSetChanged();
        log("UI updated");
    }

    private void setRecyclerLayout() {
        container.removeAllViews();
        container.addView(recyclerLayout);
        isEmptyLayout = false;

        log("recycler layout set");
    }

    private void setEmptyLayout() {
        container.removeAllViews();
        container.addView(emptyLayout);
        isEmptyLayout = true;
        log("empty layout set");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (adapter.getNoteList().size() > 0)
            inflater.inflate(R.menu.list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_list_fragment_add:
                listener.onClick(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Note note;
        private TextView title;
        private Button deleteButton;

        NoteHolder(View itemView) {
            super(itemView);
            log(this.getClass(), "new");
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title_text_view);
            deleteButton = (Button) itemView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(i -> {
                source.removeNote(note);
                updateUI();
            });
        }

        void bindNote(Note note) {
            log(this.getClass(), "bindNote");
            this.note = note;
            this.title.setText(note.getTitle());
        }

        @Override
        public void onClick(View view) {
            Intent intent = NoteActivity.intent(getActivity(), note.getId());
            //TODO: NEW REQUEST CODE
            startActivity(intent);
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {
        private List<Note> noteList;
        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            log(this.getClass(), "onCreateViewHolder");
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_element, parent, false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            log(this.getClass(), "onBindViewHolder");
            Note note = noteList.get(holder.getAdapterPosition());
            holder.bindNote(note);
        }

        List<Note> getNoteList() {
            return noteList;
        }

        void setNoteList(List<Note> noteList) {
            this.noteList = noteList;
        }

        @Override
        public int getItemCount() {
            return noteList.size();
        }
    }

}
