package com.example.asm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asm.Activity.ClazzActivity;
import com.example.asm.Activity.StudentActivity;
import com.example.asm.R;
import com.example.asm.models.Clazz;

import java.util.ArrayList;

public class ClazzAdapter extends BaseAdapter {
private ArrayList<Clazz> list;
public ClazzAdapter(ArrayList<Clazz> list){
    this.list=list;
}

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if(view==null){
            view=View.inflate(_viewGroup.getContext(),R.layout.layout_clazz_detal_item,null);
            TextView tvClazzName=view.findViewById(R.id.tvClazzName);
            TextView tvClazzTime=view.findViewById(R.id.tvClazzTime);
            TextView tvClazzRoom=view.findViewById(R.id.tvClazzRoom);
            ImageButton imageButtonEdit=view.findViewById(R.id.imageButtonEdit);
            ImageButton imageButtonTrash=view.findViewById(R.id.imageButtonTrash);
            ViewHolder holder = new ViewHolder(tvClazzName,tvClazzTime,tvClazzRoom,imageButtonEdit,imageButtonTrash);
            view.setTag(holder);

        }
        Clazz clazz = (Clazz) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvClazzName.setText(clazz.getName());
        holder.tvClazzTime.setText(clazz.getTime());
        holder.tvClazzRoom.setText(clazz.getRoom());
        holder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClazzActivity clazzActivity = (ClazzActivity) _viewGroup.getContext();
                clazzActivity.onClickEdit(clazz);
            }
        });
        holder.imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClazzActivity clazzActivity = (ClazzActivity) _viewGroup.getContext();
                clazzActivity.onDelete(clazz);
            }
        });
        return view;
    }
    public static class ViewHolder{
        final TextView tvClazzName,tvClazzTime,tvClazzRoom;
        final ImageButton imageButtonEdit,imageButtonTrash;

        public ViewHolder(TextView tvClazzName, TextView tvClazzTime, TextView tvClazzRoom, ImageButton imageButtonEdit, ImageButton imageButtonTrash) {
            this.tvClazzName = tvClazzName;
            this.tvClazzTime = tvClazzTime;
            this.tvClazzRoom = tvClazzRoom;
            this.imageButtonEdit = imageButtonEdit;
            this.imageButtonTrash = imageButtonTrash;
        }
    }
}
