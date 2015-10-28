package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;

public class search_contacts extends Activity implements View.OnKeyListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        set_autocomp_bar();
        display_list("");

    }

    public void push_search_btn(View view) {
        EditText search_text = (EditText) findViewById(R.id.search_contact_name);
        String text_entered = search_text.getText().toString();
        display_list(text_entered);
    }

    private void set_autocomp_bar() {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> db_contacts = db.getAllContacts();

        // set the adapter with contact list.
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db_contacts);
        EditText etsearch = (EditText) findViewById(R.id.search_contact_name);
        // on text change events.
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText etsearch = (EditText) findViewById(R.id.search_contact_name);
                String textinbar = etsearch.getText().toString();
                display_list(textinbar);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etsearch.setOnKeyListener(this);
        db.close();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText search_bar = (EditText) findViewById(R.id.search_contact_name);
        String textinbar = search_bar.getText().toString();
        if (keyCode == 66) // == ENTER
        {
            display_list(textinbar);
        }
        return true;
    }

    private void display_list(String display_type) {
        DatabaseHandler db = new DatabaseHandler(this);

        List<Contact> contacts;
        if (display_type.equals(""))
            contacts = db.getAllContacts();
        else
            contacts = db.getAllContactsfromName_start(display_type);
        ListView listContent = (ListView) findViewById(R.id.contact_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listContent.setAdapter(adapter);
        listContent.setOnItemClickListener(this);
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String name = parent.getItemAtPosition(position).toString();
        Contact contact = (Contact) parent.getItemAtPosition(position);
        int id_to_display = contact.getId();

        Intent intent = new Intent(this, display_contact.class);

        intent.putExtra("CONTACT_NAME", name);
        intent.putExtra("CONTACT_ID", id_to_display);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, UserSettingActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Set app visible or not.
    @Override
    protected void onResume() {
        super.onResume();
        App_visibility.activityResumed();

        // change action bar color
        AppUtils utils = new AppUtils();
        utils.set_actionbar_color(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        App_visibility.activityPaused();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //App_visibility.activityPaused();
        App_visibility.set_time();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //App_visibility.activityResumed();
        App_visibility.display_time(this);
    }
}