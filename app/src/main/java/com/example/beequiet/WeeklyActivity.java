package com.example.beequiet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WeeklyActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> option;
    public static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView)findViewById(R.id.listView);
        option = new ArrayList<String>();
        addOption();
        listClick();
    }
    private void addOption() {
        //not sure what to do for 15 , 30 , 45
        option.add(0, "12");
        option.add(1, "1");
        option.add(2, "2");
        option.add(3, "3");
        option.add(4, "4");
        option.add(5, "5");
        option.add(6, "6");
        option.add(7, "7");
        option.add(8, "8");
        option.add(9, "9");
        option.add(10, "10");
        option.add(11, "11");
        option.add(12, "12");
        option.add(13, "1");
        option.add(14, "2");
        option.add(15, "3");
        option.add(16, "4");
        option.add(17, "5");
        option.add(18, "6");
        option.add(19, "7");
        option.add(20, "8");
        option.add(21, "9");
        option.add(22, "10");
        option.add(23, "11");
        option.add(24, "12");

        adapter = new ArrayAdapter<String>(this, R.layout.list_view, option);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void listClick() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                    case 21:
                        break;
                    case 22:
                        break;
                    case 23:
                        break;
                    case 24:
                        break;
                }
            }
        });
    }


}
