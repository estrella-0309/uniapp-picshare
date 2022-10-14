package com.example.sharepicture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.sharepicture.R;
import com.example.sharepicture.entity.discoverPic;

import java.util.List;

public class discoverAdapter extends ArrayAdapter<discoverPic> {

    private List<discoverPic> PicData;
    private int resourceId;
    private Context context;



    public discoverAdapter(@NonNull Context context, int resource, @NonNull List<discoverPic> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resourceId = resource;
        this.PicData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        discoverPic pic = getItem(position);

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.discover_list,parent,false);
        TextView userName = view.findViewById(R.id.tv_userName);
        TextView tv_like = view.findViewById(R.id.tv_like);
        TextView tv_collect = view.findViewById(R.id.tv_collect);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        ImageView iv_pic = view.findViewById(R.id.iv_pic);

        userName.setText(pic.getUsername());
//        tv_like.setText(pic.getLikeNum());
//        tv_collect.setText(pic.getCollectNum());
        tv_title.setText(pic.getTitle());
        tv_content.setText(pic.getContent());

        String picFirstUrl = pic.getImageUrlList().toString();
        Log.d("info",picFirstUrl);
        if(!picFirstUrl.equals(null))
            Glide.with(context).load(pic.getImageUrlList()).into(iv_pic);

        return view;

    }



    public int getCount() {
        return PicData.size();
    }


}
