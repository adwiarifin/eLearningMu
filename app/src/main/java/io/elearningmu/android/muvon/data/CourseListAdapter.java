package io.elearningmu.android.muvon.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import io.elearningmu.android.muvon.R;

import io.elearningmu.android.muvon.model.Course;
import io.elearningmu.android.muvon.model.response.CourseResponse;
import io.elearningmu.android.muvon.util.HTMLString;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private List<CourseResponse> listCourse;
    private Context mContext;

    final private CourseAdapterOnClickHandler mClickHandler;

    public interface CourseAdapterOnClickHandler {
        void onClick(int courseId);
    }

    public CourseListAdapter(@NonNull Context context, CourseAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
        this.listCourse = new ArrayList<>();
    }

    public void setData(List<CourseResponse> listCourse) {
        this.listCourse.clear();
        this.listCourse.addAll(listCourse);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        CourseViewHolder viewHolder = new CourseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        final Course course = listCourse.get(position).course;

        holder.txtName.setText(course.name);
        holder.txtInstructor.setText(course.instructor.name);
        holder.ratingBar.setRating(Float.valueOf(course.averageRating));
        if (course.priceHtml instanceof String) {
            String price = course.priceHtml.toString();
            if (HTMLString.hasHTMLTags(price)) {
                price = HTMLString.stripHTML(price);
            }
            holder.txtPrice.setText(price);
        }

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
        TextView txtName, txtInstructor, txtPrice;
        RatingBar ratingBar;
        CardView cardCourse;

        CourseViewHolder(View itemView) {
            super(itemView);
            cardCourse = itemView.findViewById(R.id.card_course);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            txtName = itemView.findViewById(R.id.tv_item_name);
            txtInstructor = itemView.findViewById(R.id.tv_item_instructor);
            txtPrice = itemView.findViewById(R.id.tv_item_price);
            ratingBar = itemView.findViewById(R.id.tv_item_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Course course = listCourse.get(adapterPosition).course;
            mClickHandler.onClick(course.id);
        }
    }
}
