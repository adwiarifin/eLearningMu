package io.elearningmu.android.muvon.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.model.Curriculum;
import io.elearningmu.android.muvon.util.HTMLString;

public class CurriculumListAdapter extends RecyclerView.Adapter<CurriculumListAdapter.ReviewViewHolder> {

    private List<Curriculum> mCurriculumList;
    private Context mContext;

    public CurriculumListAdapter(@NonNull Context context) {
        this.mContext = context;
        this.mCurriculumList = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_curriculum, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Curriculum curriculum = mCurriculumList.get(position);

        String formattedText = HTMLString.parseHTML(curriculum.title);
        holder.curriculumItem.setText(formattedText);
        if (curriculum.type.equals("section")) {
            holder.curriculumItem.setTypeface(null, Typeface.BOLD);
        } else if (curriculum.type.equals("unit")) {
            holder.curriculumItem.setPadding(32, 0, 0, 0);
        } else if (curriculum.type.equals("quiz")) {
            holder.curriculumItem.setTypeface(null, Typeface.BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return mCurriculumList != null ? mCurriculumList.size() : 0;
    }

    public void setCurriculumList(List<Curriculum> curriculumList) {
        this.mCurriculumList.clear();
        this.mCurriculumList.addAll(curriculumList);
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        final TextView curriculumItem;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            curriculumItem = itemView.findViewById(R.id.course_detail_curriculum_item);
        }
    }
}