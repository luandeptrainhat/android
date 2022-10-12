package com.example.asm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.asm.Activity.StudentActivity;
import com.example.asm.R;
import com.example.asm.models.Student;

import java.util.ArrayList;

/*
* đưa dữ liệu vào layout item
* */
public class StudentAdapter extends BaseAdapter {

    private ArrayList<Student> list;
    public  StudentAdapter(ArrayList<Student>list){
        this.list = list;
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
        View view=_view;
        if(view==null){
            view=View.inflate(_viewGroup.getContext(), R.layout.layout_student_detail_item,null);
            TextView tvStudentName = view.findViewById(R.id.tvStudentName);
            TextView tvStudentClazz=view.findViewById(R.id.tvStudentClazz);
            ImageButton imageButtonTrash= view.findViewById(R.id.imageButtonTrash);
            ImageButton imageButtonEdit=view.findViewById(R.id.imageButtonEdit);
            ViewHolder holder = new ViewHolder(tvStudentName,tvStudentClazz,imageButtonEdit,imageButtonTrash);
            view.setTag(holder);

        }
        Student student =(Student) getItem(position);
        ViewHolder holder  = (ViewHolder) view.getTag();
        holder.tvStudentClazz.setText(student.getClazz_id().toString());
        holder.tvStudentName.setText(student.getName());
        holder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentActivity studentActivity = (StudentActivity) _viewGroup.getContext();
                studentActivity.onClickEdit(student);

            }
        });
        holder.imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentActivity studentActivity = (StudentActivity) _viewGroup.getContext();
                studentActivity.onDelete(student);
            }
        });
        return view;
    }
    public  static  class ViewHolder{
         final TextView tvStudentName, tvStudentClazz;
         final ImageButton imageButtonTrash,imageButtonEdit;

        public ViewHolder(TextView tvStudentName, TextView tvStudentClazz,ImageButton imageButtonEdit,ImageButton imageButtonTrash) {
            this.tvStudentName = tvStudentName;
            this.tvStudentClazz = tvStudentClazz;
            this.imageButtonEdit = imageButtonEdit;
            this.imageButtonTrash = imageButtonTrash;
        }
    }
}
