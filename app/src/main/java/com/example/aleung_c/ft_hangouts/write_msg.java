package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class write_msg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_msg, menu);

        DatabaseHandler db = new DatabaseHandler(this);


        List<Contact>db_contacts = db.getAllContacts();

        // set the adapter with contact list.
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db_contacts);

        // display the adapter.
        AutoCompleteTextView autoctextview = (AutoCompleteTextView) findViewById(R.id.write_msg_name_select);
        autoctextview.setAdapter(adapter);

        autoctextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position).toString();
                Contact contact = (Contact) parent.getItemAtPosition(position);

                String phone_nb = contact.getPhonenb();
                TextView display_num = (TextView) findViewById(R.id.write_msg_display_num);
                display_num.setText(phone_nb);
            }
        });

        click_send_msg();

        db.close();

        return true;
    }

    private void click_send_msg() {
        Button submit_btn = (Button) findViewById(R.id.write_msg_submit_btn);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


