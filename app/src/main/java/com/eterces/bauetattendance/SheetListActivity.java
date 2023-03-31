package com.eterces.bauetattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    private ListView sheetList;
    private Adapter adapter;
    private ArrayList <String> listItems =new ArrayList();
    private long cid;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);
        cid=getIntent().getLongExtra("cid",-1);

        loadListItems();
        sheetList =findViewById(R.id.sheetList);
        adapter =new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        sheetList.setAdapter((ListAdapter) adapter);

        sheetList.setOnItemClickListener((parent, view , position ,id) -> openSheetAvtivity(position));
        setToolbar();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Select Months");
        title.setTextColor(ContextCompat.getColor(this, R.color.bl));
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.bl3));
    }

    private void openSheetAvtivity(int position) {

        long [] idArray =getIntent().getLongArrayExtra("idArray");
        int [] rollArray =getIntent().getIntArrayExtra("rollArray");
        String[] nameArrya =getIntent().getStringArrayExtra("nameArray");

        Intent intent =new Intent(this,SheetActivity.class);
        intent.putExtra("idArray",idArray);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArrya);
        intent.putExtra("month",listItems.get(position));

        startActivity(intent);
    }

    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);
        while (cursor.moveToNext()) {
           String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY));
           listItems.add(date.substring(4));
        }
    }
}