package com.ndw6152.testadd;

import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<ItemCost> laborCostArray = new ArrayList<>();
    private final ArrayList<ItemCost> partsCostArray = new ArrayList<>();
    private double onSiteServiceCharge;
    private String comments;

    private ItemCostAdapter laborCostAdapter;
    private ItemCostAdapter partsCostAdapter;


    int count = 0;

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String str = gson.toJson(object);
        return str;
    }

    public void initFabActions() {
        FabSpeedDial fabSpeedDial = findViewById(R.id.fabSpeedDial_quote_actions);
        // fab actions and listeners
        // https://github.com/yavski/fab-speed-dial
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });


        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add_comments:
                        break;
                    case R.id.action_add_labor_cost:
                        laborCostArray.add(new ItemCost("Labor" + count, count++));
                        laborCostAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_add_parts_cost:

                        partsCostArray.add(new ItemCost("Parts" + count, count++));
                        partsCostAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_add_service_cost:
                        JobQuote quote = createJobQuote();
                        String str = toJson(quote);

                        Log.i("he", "llo");
                        break;
                }
                return false;
            }
        });
    }

    public void initQuoteListViews() {
        laborCostAdapter = new ItemCostAdapter(MainActivity.this, laborCostArray);
        ListView view = findViewById(R.id.listView_labor_cost);
        view.setAdapter(laborCostAdapter);


        partsCostAdapter = new ItemCostAdapter(MainActivity.this, partsCostArray);
        ListView view2 = findViewById(R.id.listView_parts_cost);
        view2.setAdapter(partsCostAdapter);
    }


    public JobQuote createJobQuote() {
        return new JobQuote(laborCostArray, partsCostArray, onSiteServiceCharge, comments);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        laborCostArray.add(new ItemCost("hello", 20));
        initQuoteListViews();
        initFabActions();
    }



}
