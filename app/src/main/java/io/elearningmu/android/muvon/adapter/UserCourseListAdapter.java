package io.elearningmu.android.muvon.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.model.Course;

public class UserCourseListAdapter extends RecyclerView.Adapter<UserCourseListAdapter.CourseViewHolder> {

    private List<Course> listCourse;
    private Context mContext;

    final private CourseAdapterOnClickHandler mClickHandler;

    public interface CourseAdapterOnClickHandler {
        void onClick(int courseId);
    }

    public UserCourseListAdapter(@NonNull Context context, CourseAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
        this.listCourse = new ArrayList<>();
    }

    public void setData(List<Course> listCourse) {
        this.listCourse.clear();
        this.listCourse.addAll(listCourse);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_course, parent, false);
        CourseViewHolder viewHolder = new CourseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        final Course course = listCourse.get(position);

        holder.txtName.setText(course.name);
        holder.txtInstructor.setText(course.instructor.name);
        holder.txtProgress.setText(String.format(Locale.getDefault(), "%d%%", course.userProgress.intValue()));
        holder.progressBar.setProgress(course.userProgress.intValue());

        RequestOptions options = new RequestOptions()
                .fitCenter();

        Glide.with(mContext)
                .load(course.featuredImage)
                .apply(options)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listCourse == null ? 0 : listCourse.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgPhoto;
        TextView txtName, txtInstructor, txtProgress;
        ProgressBar progressBar;
        CardView cardCourse;

        CourseViewHolder(View itemView) {
            super(itemView);
            cardCourse = itemView.findViewById(R.id.card_course);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            txtName = itemView.findViewById(R.id.tv_item_name);
            txtInstructor = itemView.findViewById(R.id.tv_item_instructor);
            txtProgress = itemView.findViewById(R.id.progressText);
            progressBar = itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Course course = listCourse.get(adapterPosition);
            mClickHandler.onClick(course.id);
        }
    }
}
