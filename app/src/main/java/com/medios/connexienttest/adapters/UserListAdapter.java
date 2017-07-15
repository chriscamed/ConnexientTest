package com.medios.connexienttest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.medios.connexienttest.R;
import com.medios.connexienttest.model.objectsmodel.UserObjectModel;

import java.util.ArrayList;

/**
 * Created by Camilo on 7/15/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private ArrayList<UserObjectModel> userList;
    private Context context;

    public UserListAdapter(Context context, ArrayList<UserObjectModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(userList.get(position).getImageUrl())
                .into(holder.userImageView);

        holder.userNameTextview.setText(String.format("%1$s, %2$s", userList.get(position).getLastName(), userList.get(position).getName()));
        holder.streetTextView.setText(userList.get(position).getLocation().getStreet());
        holder.cityTextView.setText(userList.get(position).getLocation().getCity());
        holder.stateTextView.setText(userList.get(position).getLocation().getState());
        holder.userGender.setText(String.valueOf(userList.get(position).getGender()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImageView;
        private TextView userNameTextview;
        private TextView streetTextView;
        private TextView cityTextView;
        private TextView stateTextView;
        private TextView userGender;

        public ViewHolder(View itemView) {
            super(itemView);
            userImageView = (ImageView) itemView.findViewById(R.id.user_image_view);
            userNameTextview = (TextView) itemView.findViewById(R.id.user_name_text_view);
            streetTextView = (TextView) itemView.findViewById(R.id.street_text_view);
            cityTextView = (TextView) itemView.findViewById(R.id.city_text_view);
            stateTextView = (TextView) itemView.findViewById(R.id.state_text_view);
            userGender = (TextView) itemView.findViewById(R.id.user_gender_text_view);
        }
    }
}
