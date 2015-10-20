package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ActionBar;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);



        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();
        ListView listContent = (ListView) findViewById(R.id.contact_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listContent.setAdapter(adapter);
        listContent.setOnItemClickListener(this);
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
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

//    @Override
//    public void onBackPressed() {
//        onDestroy();
//        System.exit(0);
//    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String name = (String) parent.getItemAtPosition(position).toString();
        Contact contact = (Contact) parent.getItemAtPosition(position);
        int id_to_display = contact.getId();

        Intent intent = new Intent(this, display_contact.class);

        intent.putExtra("CONTACT_NAME", name);
        intent.putExtra("CONTACT_ID", (int)id_to_display);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    // GOTO BUTTONS
    public void goto_contact_list(View v)
    {
        Intent intent = new Intent(this, search_contacts.class);
        startActivity(intent);
    }

    public void goto_add_contact(View v)
    {
        Intent intent = new Intent(this, add_contact.class);
        intent.putExtra("ADDCONTACT_ACTION", "ADD");
        startActivity(intent);
    }

    public void goto_write_msg(View v)
    {
        Intent intent = new Intent(this, write_msg.class);
        intent.putExtra("WRITE_MSG_ID", -1);
        startActivity(intent);
    }

    // Set app visible or not.
    @Override
    protected void onResume() {
        super.onResume();
        App_visibility.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        App_visibility.activityPaused();
    }
}


