package com.example.herambtinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herambtinder.Models.swipeProfile;
import com.example.herambtinder.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class swipeAdapter extends BaseAdapter {

    List<swipeProfile> profiles;
    Context context;

    public swipeAdapter(List<swipeProfile> profiles, Context context) {
        this.profiles = profiles;
        this.context = context;
    }


    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Object getItem(int i) {
        return profiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.swipeprofilelayout, parent, false);
        }

        ImageView profileImage = convertView.findViewById(R.id.swipeProfileImage);
        TextView age = convertView.findViewById(R.id.swipeAge);
        TextView bio = convertView.findViewById(R.id.swipeBio);
        TextView name = convertView.findViewById(R.id.swipeName);

        swipeProfile profile = profiles.get(position);
        profileImage.setImageResource(profile.getProfileImage());
        age.setText(profile.getAge());
        bio.setText(profile.getBio());
        name.setText(profile.getBio());






        return  convertView;
    }
}
