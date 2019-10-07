package com1032.cw1.ls00735.ls00735_todolist;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author savou
 * this is the main activity where list view is displayed and items can be added and removed from the list
 */

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private EditText toDoList;
    private Button button;
    private ArrayList<String> listItems;
    private MyList mylist;

    /**
     * onPause saves contents of list into a text file
     */

    @Override
    protected void onPause() {
        super.onPause();
        /*
        if the orientation hasn't changed then save the string objects to a text file
         */
        if(!isChangingConfigurations()) {
            try {
                FileOutputStream fout = openFileOutput("file.txt", Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.writeObject(listItems);
                out.close();
                fout.close();
            } catch (Exception e) {
                Log.v("Todo List", "Error writing file: " + e.getMessage());
            }
        }

    }

    protected void onStop() {

        super.onStop();
    }

    /**
     *
     * @param savedInstanceState
     * onCreate loads strings from a text file into the list view
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        Log.v("Two Activities", "Second Activity Created");

        /*
        if orientation hasn't changed load the string objects from a text file
         */
        if(savedInstanceState == null) {
            try {
                FileInputStream fin= openFileInput("file.txt");
                ObjectInputStream in = new ObjectInputStream(fin);
                listItems = (ArrayList<String>) in.readObject();
                in.close();
                fin.close();
            } catch (Exception e) {
                Log.v("Todo List", "Error reading file: " + e.getMessage());
                listItems = new ArrayList<String>();
            }
        }
        else
        {
            listItems = new ArrayList<>();
        }

        mylist = new MyList(this, listItems);
        mylist.notifyDataSetChanged();
        setupMyListeners();

    }

    /**
     * Sets listeners on items in MainActvity
     */
    private void setupMyListeners()
    {

        /*
        open a new activity when clicking an item on short click
         */
        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = list.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected item " + text, Toast.LENGTH_SHORT).show();

            }
        });


        /*
        add item to list when pressing addTask button
         */

        button = (Button) findViewById(R.id.addTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String list = toDoList.getText().toString();
                listItems.add(list);
                mylist.notifyDataSetChanged();
                toDoList.getText().clear();

            }
        });

        /*
        deletes item on long click
         */
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = listItems.get(position);
                listItems.remove(selectedItem);
                mylist.notifyDataSetChanged();
                return true;
            }
        });
        list.setAdapter(mylist);
        mylist.notifyDataSetChanged();



        toDoList = (EditText) findViewById(R.id.textbar);
        toDoList.setText("");
        toDoList.setHint("Enter To Do List Item...");

        /*
        goes into new activity on short click
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("ListItem", list.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

    }

    /**
     *
     * @param menu
     * @return
     *
     * onCreateOptionsMenu inflates the menu and adds items to the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * @param item
     * @return
     *
     * onOptionItemSelected performs actions when an item on the action bar is selected
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        clear list when option on action bar is selected
         */
        int id = item.getItemId();
        if (id == R.id.clear_list) {
            listItems.clear();
            mylist.notifyDataSetChanged();
        }

            return super.onOptionsItemSelected(item);

        }

    /**
     *
     * @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        /*
        if orientation has changed save string objects to array list
        */
        if(isChangingConfigurations()){

           outState.putSerializable("List", listItems);
        }
    }

    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*
        if orientation has changed load string objects to array list
        */
        if(savedInstanceState.containsKey("List")){
                listItems.addAll((ArrayList<String>) savedInstanceState.getSerializable("List"));
                mylist.notifyDataSetChanged();
        }
    }
}
