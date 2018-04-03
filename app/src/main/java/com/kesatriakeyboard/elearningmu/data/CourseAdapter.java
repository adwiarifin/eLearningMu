package com.kesatriakeyboard.elearningmu.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.kesatriakeyboard.elearningmu.R;
import com.kesatriakeyboard.elearningmu.model.Course;
import com.kesatriakeyboard.elearningmu.model.CourseWithHeaderStatus;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<CourseWithHeaderStatus> listCourse;
    private Context mContext;

    public CourseAdapter(Context context) {
        this.mContext = context;
        this.listCourse = new ArrayList<>();
    }

    public void setData(List<CourseWithHeaderStatus> listCourse) {
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
        holder.txtPrice.setText(course.priceHtml.toString());

        RequestOptions options = new RequestOptions()
                .fitCenter();

        Glide.with(mContext)
                .load(course.featuredImage)
                .apply(options)
                .into(holder.imgPhoto);

        holder.cardCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Course id : " + course.id, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCourse == null ? 0 : listCourse.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView txtName, txtPrice;
        CardView cardCourse;

        public CourseViewHolder(View itemView) {
            super(itemView);
            cardCourse = itemView.findViewById(R.id.card_course);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            txtName = itemView.findViewById(R.id.tv_item_name);
            txtPrice = itemView.findViewById(R.id.tv_item_price);
        }
    }
}
