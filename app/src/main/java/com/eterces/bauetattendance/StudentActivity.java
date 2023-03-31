package com.eterces.bauetattendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class StudentActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String classname;
    private String subName;
    private int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();
    private DbHelper dbHelper;
    private long cid;
    private MyCalender calender;
    private TextView subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        calender = new MyCalender();

        Intent intent = getIntent();
        classname = intent.getStringExtra("className");
        subName = intent.getStringExtra("subjectName");
        position = intent.getIntExtra("position", -1);
        cid = intent.getLongExtra("cid", -1);
        dbHelper = new DbHelper(this);

        setToolbar();
        loadData();
        recyclerView = findViewById(R.id.student_recyler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StudentAdapter(this, studentItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        loadStatusData();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable(cid);
        studentItems.clear();
        while (cursor.moveToNext()) {
            long sid = cursor.getLong(cursor.getColumnIndex(DbHelper.S_ID));
            int roll = cursor.getInt(cursor.getColumnIndex(DbHelper.STUDENT_ROLL_KEY));
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));

           // Log.e("TAG", "loadData: " + sid + "," + roll + "," + name);

            studentItems.add(new StudentItem(sid, roll, name));

        }
        cursor.close();
    }

    private void changeStatus(int position) {
        String status = studentItems.get(position).getStatus();
        if (status.equals("P")) status = "A";
        else status = "P";
        studentItems.get(position).setStatus(status);
        adapter.notifyItemChanged(position);
    }


    private void setToolbar() {


        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);
        save.setOnClickListener(v -> saveStatus());

        title.setText(classname);
        subtitle.setText(subName + " | " + calender.getDate());

        back.setOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.bl3));
    }

    private void saveStatus() {
        for (StudentItem studentItem : studentItems) {
            String status = studentItem.getStatus();
            if (status != "P") status = "A";
            long value = dbHelper.addStatus(studentItem.getSid(),cid,calender.getDate(),status);
            Log.e("TAG", "saveStatus: " + calender.getDate());
            if (value == -1)
                dbHelper.updateStatus(studentItem.getSid(), calender.getDate(), status);

        }
    }

    private void loadStatusData() {
        for (StudentItem studentItem : studentItems) {
            String status = dbHelper.getStatus(studentItem.getSid(), calender.getDate());
            if (status != null) studentItem.setStatus(status);
            else studentItem.setStatus("");

        }
        adapter.notifyDataSetChanged();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.add_student) {
            showAddStudentDialog();
        } else if (menuItem.getItemId() == R.id.show_Calender) {
            showCalender();
        }
        else if (menuItem.getItemId() == R.id.show_attendance) {
            openSheetList();
        }
        return true;

    }

    private void openSheetList() {


        long[] idArray =new long[studentItems.size()];
        for (int i =0; i<idArray.length;i++)
            idArray[i] =studentItems.get(i).getSid();

        int[] rollArray = new int[studentItems.size()];
        for (int i =0; i<rollArray.length;i++)
            rollArray[i] =studentItems.get(i).getId();

        String[] nameArrya =new String[studentItems.size()];
        for (int i =0; i<nameArrya.length;i++)
            nameArrya[i] =studentItems.get(i).getName();

        Intent intent =new Intent(this,SheetListActivity.class);
        intent.putExtra("cid",cid);

        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArrya);
        startActivity(intent);
    }

    private void showCalender() {

        calender.show(getSupportFragmentManager(), "");
        calender.setOnCalenderokClickListener(this::onCalenderokClicked);
    }

    private void onCalenderokClicked(int year, int month, int day) {
        calender.setDate(year, month, day);
        subtitle.setText(calender.getDate());
        loadStatusData();
    }

    private void showAddStudentDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.STUDENT_ADD_DIALOG);
        dialog.setListner((roll, name) -> addStudent(roll, name));
    }

    private void addStudent(String roll_string, String name) {
        int roll = Integer.valueOf(roll_string);
        long sid = dbHelper.addStudent(cid, roll, name);
        StudentItem studentItem = new StudentItem(sid, roll, name);
        studentItems.add(studentItem);
        Cursor cursor = dbHelper.getStudentTable(cid);
        // Log.e("TAG", "loadData: " + cursor.getCount() );
    }

    //context

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                deleteStudent(item.getGroupId());
        }
        return super.onContextItemSelected(item);

    }

    private void deleteStudent(int position) {
        dbHelper.deleteStudent(studentItems.get(position).getSid());
        studentItems.remove(position);
        adapter.notifyItemRemoved(position);
    }
}