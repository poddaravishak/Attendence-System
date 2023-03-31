package com.eterces.bauetattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        showTable();
        setToolbar();

    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("Attendencs Sheet");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void showTable() {
        DbHelper dbHelper = new DbHelper(this);

        TableLayout tableLayout =findViewById(R.id.tableLayout);

        long [] idArray =getIntent().getLongArrayExtra("idArray");
        int [] rollArray =getIntent().getIntArrayExtra("rollArray");
        String[] nameArrya =getIntent().getStringArrayExtra("nameArray");
        String month = getIntent().getStringExtra("month");

        int DAY_IN_MONTH = getDayInMonth(month);
        Log.e("TAG", "DAY_IN_MONTH: " + DAY_IN_MONTH);

        //row setup
        int rowSize = idArray.length + 1;
        TableRow[] rows =new  TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[][] status_tvs =new TextView[rowSize][DAY_IN_MONTH + 1];


        for (int i = 0; i < rowSize; i++) {
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);

            for (int j = 0; j <= DAY_IN_MONTH; j++) {
                status_tvs[i][j] = new TextView(this);

            }



        }

        //header

        roll_tvs[0].setText("Roll");
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("Name");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
        for (int j = 1; j <= DAY_IN_MONTH; j++) {
            status_tvs[0][j].setText(String.valueOf(j) + "");
            status_tvs[0][j].setTypeface(status_tvs[0][j].getTypeface(), Typeface.BOLD);

        }

        for (int j = 1; j < rowSize; j++) {
            roll_tvs[j].setText(String.valueOf(rollArray[j - 1]));
            name_tvs[j].setText(nameArrya[j - 1]);

            for (int k = 1; k <= DAY_IN_MONTH; k++) {
                String day  = String.valueOf(k);
                if (day.length()==1)day ="0"+day;
                String date = day+".0"+month ;
                String status = dbHelper.getStatus(idArray[j-1],date);
                Log.e("TAG", "idArray: " + idArray[j-1] );
                Log.e("TAG", "date: " + date );
                Log.e("TAG", "status: " + status );
                status_tvs[j][k].setText(status);


            }

        }

        for (int j = 0; j < rowSize; j++) {
            rows[j] =new TableRow(this);

            if (j%2==0)
                rows[j].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else
                rows[j].setBackgroundColor(Color.parseColor("#E4E4E4"));
            roll_tvs[j].setPadding(16,16,16,16);
            name_tvs[j].setPadding(16,16,16,16);

            rows[j].addView(roll_tvs[j]);
            rows[j].addView(name_tvs[j]);


            for (int k = 1; k <= DAY_IN_MONTH; k++) {
                status_tvs[j][k].setPadding(16,16,16,16);

                rows[j].addView(status_tvs[j][k]);


            }


            tableLayout.addView(rows[j]);

        }
        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
//a can be red color
        TableLayout tableLayout1 = findViewById(R.id.tableLayout);
        String targetText = "A"; // replace with the text you want to look for

        for (int i = 0; i < tableLayout1.getChildCount(); i++) {
            View row = tableLayout1.getChildAt(i);
            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View cell = tableRow.getChildAt(j);
                    if (cell instanceof TextView) {
                        TextView textView = (TextView) cell;
                        if (textView.getText().toString().equals(targetText)) {
                            textView.setTextColor(Color.RED);
                            textView.setTypeface(null, Typeface.BOLD);

                            textView.setPadding(10,0,0,0);
                        }
                    }
                }
            }
        }

        TableLayout tableLayout2 = findViewById(R.id.tableLayout);
        String targetText2 = "P"; // replace with the text you want to look for

        for (int i = 0; i < tableLayout2.getChildCount(); i++) {
            View row = tableLayout2.getChildAt(i);
            if (row instanceof TableRow) {
                TableRow tableRow = (TableRow) row;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View cell = tableRow.getChildAt(j);
                    if (cell instanceof TextView) {
                        TextView textView = (TextView) cell;
                        if (textView.getText().toString().equals(targetText2)) {
                            textView.setTextColor(Color.GREEN);textView.setTypeface(null, Typeface.BOLD);
                            textView.setPadding(10,0,0,0);
                        }
                    }
                }
            }
        }



    }


    private int getDayInMonth(String sMonth) {
        //2.2010
        int monthIndex = Integer.parseInt(String.valueOf(sMonth.charAt(0))) - 1 ;
        int year = Integer.parseInt(sMonth.substring(2));

        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthIndex);
        calendar.set(Calendar.YEAR,year);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


}