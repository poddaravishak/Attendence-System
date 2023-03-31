package com.eterces.bauetattendance;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    public static final String CLASS_ADD_DIALOG="addClass";
    public static final String CLASS_UPDATE_DIALOG="updateclass";
    public static final String STUDENT_ADD_DIALOG="addstudent";

   private  onClickListner listner;
    public interface onClickListner{
        void onClick(String text1,String text2);

    }

    public void setListner(onClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=null;
        if(getTag().equals(CLASS_ADD_DIALOG))dialog=getAdddClassDialog();
        if(getTag().equals(STUDENT_ADD_DIALOG))dialog=getADDStudentDialog();
       // if(getTag().equals(CLASS_UPDATE_DIALOG))dialog=getUpdateclassDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }

/*
    public Dialog getUpdateclassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.title_dialog);
        title.setText("Update Class");

        EditText  class_edt = view.findViewById(R.id.edit01);
        class_edt.setHint("Class Name");
        EditText subject_edt = view.findViewById(R.id.edit02);
        subject_edt.setHint("Subject Name");

        Button calcle = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Update");

        calcle.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v ->{

            String className=class_edt.getText().toString();
            String subName=subject_edt.getText().toString();
            listner.onClick(className,subName);


            dismiss();
        });
        return builder.create();
    }
*/

    private Dialog getADDStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.title_dialog);
        title.setText("ADD New Student");

        EditText  roll_edt = view.findViewById(R.id.edit01);
        roll_edt.setHint("Student id");
        EditText name_edt = view.findViewById(R.id.edit02);
        name_edt.setHint("Student Name");

        Button calcle = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        calcle.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v ->{

            String roll=roll_edt.getText().toString();
            String name=name_edt.getText().toString();
            roll_edt.setText(String.valueOf(Integer.parseInt(roll)+1));
            name_edt.setText("");
            listner.onClick(roll,name);

           // dismiss();
        });
        return builder.create();
    }



    //classdialog
    private Dialog getAdddClassDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title=view.findViewById(R.id.title_dialog);
        title.setText("ADD a New Class");

       EditText  class_edt = view.findViewById(R.id.edit01);
       class_edt.setHint("Class Name");
       EditText subject_edt = view.findViewById(R.id.edit02);
       subject_edt.setHint("Subject Name");

        Button calcle = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        calcle.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v ->{

            String className=class_edt.getText().toString();
            String subName=subject_edt.getText().toString();
            listner.onClick(className,subName);


            dismiss();
        });
        return builder.create();
    }
}
