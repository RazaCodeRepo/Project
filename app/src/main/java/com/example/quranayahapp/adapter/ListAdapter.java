package com.example.quranayahapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranayahapp.R;
import com.example.quranayahapp.database.Ayah;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.VerseViewHolder> {

    private List<Ayah> mAyahList;
    private Context mContext;

    public ListAdapter(Context context, List<Ayah> list) {
        mContext = context;
        mAyahList = list;
    }

    @NonNull
    @Override
    public VerseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);

        return new VerseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerseViewHolder holder, int position) {
        // Determine the values of the wanted data
        Ayah mAyah = mAyahList.get(position);
        String surahName = mAyah.getSurah_name_english();
        String surahAyatNumber = mAyah.getSurah_name_arabic();


        //Set values
        holder.verseNameView.setText(surahName);
        holder.verseChapterView.setText(surahAyatNumber);

    }

    @Override
    public int getItemCount() {
        if (mAyahList == null) {
            return 0;
        }
        return mAyahList.size();
    }

    class VerseViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView verseNameView;
        TextView verseChapterView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public VerseViewHolder(View itemView) {
            super(itemView);

            verseNameView = itemView.findViewById(R.id.taskDescription);
            verseChapterView = itemView.findViewById(R.id.taskUpdatedAt);
        }
    }
}
