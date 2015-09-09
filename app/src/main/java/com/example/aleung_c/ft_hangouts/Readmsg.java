package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Readmsg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmsg);
        DatabaseHandler db = new DatabaseHandler(this);

        Intent intent = getIntent();
        int id_to_display = (int) intent.getExtras().getInt("CONTACT_ID");
        Contact contact_to_display = db.getContact(id_to_display);

        // Displaying infos //
        TextView name = (TextView) findViewById(R.id.readmsg_contact_name);
        name.setText(contact_to_display.getName());

        TextView number = (TextView) findViewById(R.id.readmsg_contact_nb);
        number.setText(contact_to_display.getPhonenb());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_readmsg, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
