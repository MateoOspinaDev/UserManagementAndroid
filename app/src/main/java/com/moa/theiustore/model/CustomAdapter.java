package com.moa.theiustore.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moa.theiustore.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<User> usersList;

    public CustomAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textViewName;
        TextView textViewPhone;
        ImageView imageView;

        User user = usersList.get(i);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_list_view, null);

            textViewName = view.findViewById(R.id.textViewName);
            textViewPhone = view.findViewById(R.id.textViewPhone);
            imageView = view.findViewById(R.id.imageViewContact);

            textViewName.setText(user.getName());
            textViewPhone.setText(user.getPhone());
            imageView.setImageResource(R.drawable.contact_image);

            return view;
        }

        return null;
    }
}
