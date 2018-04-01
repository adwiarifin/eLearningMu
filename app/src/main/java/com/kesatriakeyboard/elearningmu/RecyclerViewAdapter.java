package com.kesatriakeyboard.elearningmu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomRecylcerView> {

    private List<UserList.UserDataList> itemList;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public RecyclerViewAdapter(Context context, List<UserList.UserDataList> itemList) {
        this.itemList = itemList;
        mRequestQueue = SingletonRequestQueue.getInstance(context).getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    @NonNull
    @Override
    public CustomRecylcerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, null);
        CustomRecylcerView crv = new CustomRecylcerView(layoutView);
        return crv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecylcerView holder, int position) {
        UserList.UserDataList myData = itemList.get(position);
        holder.txtLabel.setText(String.format("%s %s", myData.firstName, myData.lastName));
        holder.avatar.setImageUrl(myData.avatar, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CustomRecylcerView extends RecyclerView.ViewHolder {
        TextView txtLabel;
        NetworkImageView avatar;

        CustomRecylcerView(View itemView){
            super(itemView);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            avatar = itemView.findViewById(R.id.imgNetwork);
        }
    }
}
