package com1032.cw1.ls00735.ls00735_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author savou
 * second activity displays details of list item
 */

public class SecondActivity extends AppCompatActivity {
    private TextView textView;

    /**
     * @param savedInstanceState
     *
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.textView);
        /*
        display string from list item in main activity
         */
        textView.setText(getIntent().getStringExtra("ListItem"));

    }


    /**
     * show toast message when entering second activity
     */
    protected void onResume() {
        super.onResume();
        Log.v("Todo List", "Second Activity Resumed");
        Toast.makeText(SecondActivity.this, R.string.toast_AR, Toast.LENGTH_SHORT).show();
    }

    protected void onPause() {
        super.onPause();
        Log.v("Todo List", "Second Activity Paused");
    }

    protected void onStop() {
        super.onStop();
        Log.v("Todo List", "Second Activity Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Todo List", "Second Activity Stopped");
    }

    /**
     * @param item
     * @return
     *
     * onOptionsItemSelected performs actions when an option on the action bar is selected
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        returns to main activity when option is selected
         */

        int id = item.getItemId();
        if (id == R.id.action_activity_one) {
            Intent openMainActivity = new Intent(SecondActivity.this, MainActivity.class);
            openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(openMainActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param menu
     * @return
     *
     * onCreateOptionsMenu inflates the menu and adds items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
