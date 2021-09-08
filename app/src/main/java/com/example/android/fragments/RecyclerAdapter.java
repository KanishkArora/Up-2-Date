package com.example.android.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ExampleViewHolder>{
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private RecyclerViewClick mListnerObject;

    public void setOnItemClickListener(RecyclerViewClick listener)
    {
        mListnerObject =listener;
    }
    public interface RecyclerViewClick{
        void onClick1(int pos);
    }

    public RecyclerAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_design, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        String likeCount = currentItem.getLikeCount();
        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Likes: " + likeCount);
        Glide.with(mContext).load(imageUrl).into(holder.mImageView);


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder  {
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.refimage);
            mTextViewCreator = itemView.findViewById(R.id.reftext1);
            mTextViewLikes = itemView.findViewById(R.id.reftext2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListnerObject !=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            mListnerObject.onClick1(position);
                        }
                    }
                }
            });
        }


    }
}