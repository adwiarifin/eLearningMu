package io.elearningmu.android.muvon.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.model.CourseItem;
import io.elearningmu.android.muvon.util.HTMLString;

public class CourseIndexAdapter extends RecyclerView.Adapter<CourseIndexAdapter.CourseIndexViewHolder> {

    private final List<CourseItem> listCourseUnit;
    private Context mContext;
    private CourseIndexAdapterOnClickHandler mClickHandler;

    public CourseIndexAdapter(Context mContext, CourseIndexAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mClickHandler = clickHandler;
        this.listCourseUnit = new ArrayList<>();
    }

    @NonNull
    @Override
    public CourseIndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_index, parent, false);
        CourseIndexViewHolder viewHolder = new CourseIndexViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseIndexViewHolder holder, int position) {
        CourseItem courseItem = listCourseUnit.get(position);

        holder.textTitle.setText(HTMLString.parseHTML(courseItem.title));
        switch (courseItem.type) {
            case "section":
                holder.textTitle.setTypeface(null, Typeface.BOLD);
                holder.textTitle.setPadding(0, 0, 0, 0);
                holder.checkDone.setVisibility(View.GONE);
                break;
            case "unit":
                holder.textTitle.setTypeface(null, Typeface.NORMAL);
                holder.textTitle.setPadding(32, 0, 0, 0);
                holder.checkDone.setVisibility(View.VISIBLE);
                if (courseItem.status == 1) {
                    holder.checkDone.setChecked(true);
                } else {
                    holder.checkDone.setChecked(false);
                }
                break;
            case "quiz":
                holder.textTitle.setTypeface(null, Typeface.BOLD);
                holder.textTitle.setPadding(0, 0, 0, 0);
                holder.checkDone.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listCourseUnit.size();
    }

    public void setData(List<CourseItem> listCourseItem) {
        Log.d("ADAPTER", "size: " + listCourseItem.size());
        this.listCourseUnit.clear();
        this.listCourseUnit.addAll(listCourseItem);
        notifyDataSetChanged();
    }

    public interface CourseIndexAdapterOnClickHandler {
        void onClick(int courseItemId, String courseItemTitle);
    }

    class CourseIndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitle;
        CheckBox checkDone;

        CourseIndexViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.index_title);
            checkDone = itemView.findViewById(R.id.index_check);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            CourseItem courseItem = listCourseUnit.get(adapterPosition);
            if (courseItem.type.equals("unit")) {
                int id = Integer.parseInt(courseItem.id);
                String title = courseItem.title;
                mClickHandler.onClick(id, title);
            }
        }
    }
}
