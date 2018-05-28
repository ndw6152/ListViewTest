package com.ndw6152.testadd;

import android.content.DialogInterface;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<ListViewItem> laborCostArray = new ArrayList<>();
    private final ArrayList<ListViewItem> partsCostArray = new ArrayList<>();
    private double onSiteServiceCharge = 0.0;
    private String comments = "";

    private ItemCostAdapter laborCostAdapter;
    private ItemCostAdapter partsCostAdapter;

    private int count = 0;

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String str = gson.toJson(object);
        return str;
    }


    public JobQuote createJobQuote() {
        return new JobQuote(laborCostArray, partsCostArray, onSiteServiceCharge, comments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.job_quote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_save_profile)
        {
            JobQuote quote = createJobQuote();
            String str = toJson(quote);
            Log.i("Save quote", str);
        }
        return super.onOptionsItemSelected(item);
    }



    public void showAddServiceChargeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.onsitecharge_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editText_onSiteCost = dialogView.findViewById(R.id.editText_onSiteCost);

        final TextView textView_onSiteCost = findViewById(R.id.textView_onSiteCost);

        dialogBuilder.setMessage("Enter on-site service charge:");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                onSiteServiceCharge = Integer.parseInt(editText_onSiteCost.getText().toString());
                String charge = "$" + editText_onSiteCost.getText().toString();
                textView_onSiteCost.setText(charge);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showAddCommentsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.comments_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.editText_comments);
        final TextView textView_comments = findViewById(R.id.textView_comments);
        dialogBuilder.setMessage("Enter on-site service charge:");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String comments = editText.getText().toString();
                textView_comments.setText(comments);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void initQuoteListViews() {
        laborCostAdapter = new ItemCostAdapter(MainActivity.this, laborCostArray);
        ListView view = findViewById(R.id.listView_labor_cost);
        view.setAdapter(laborCostAdapter);


        partsCostAdapter = new ItemCostAdapter(MainActivity.this, partsCostArray);
        ListView view2 = findViewById(R.id.listView_parts_cost);
        view2.setAdapter(partsCostAdapter);
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
                        showAddCommentsDialog();
                        break;

                    case R.id.action_add_labor_cost:
                        laborCostArray.add(new ListViewItem("Labor" + count, count++));
                        laborCostAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_add_parts_cost:
                        partsCostArray.add(new ListViewItem("Parts" + count, count++));
                        partsCostAdapter.notifyDataSetChanged();
                        break;

                    case R.id.action_add_service_cost:
                        showAddServiceChargeDialog();
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        laborCostArray.add(new ListViewItem("hello", 20));
        initQuoteListViews();
        initFabActions();
    }



}
