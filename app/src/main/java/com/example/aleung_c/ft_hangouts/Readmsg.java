package com.example.aleung_c.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;


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

        // display messages.
        TelephonyManager  tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String myphonenb = tm.getLine1Number();

        List<Message> messages =  db.getAllMessagesfromId(id_to_display, myphonenb);
//        ListView listmsgContent = (ListView) findViewById(R.id.msg_list);
//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
//        listmsgContent.setAdapter(adapter);

        LinearLayout current_layout = (LinearLayout)findViewById(R.id.read_msg_main);
//        setContentView(linearLayout);
        current_layout.setOrientation(LinearLayout.VERTICAL);
        for( int i = 0; i < messages.size(); i++ )
        {
            TextView textView = new TextView(this);
//            textView.setLayoutParams();

            textView.setWidth(current_layout.getWidth());
            if (messages.get(i).getSenderNb() == myphonenb)
                textView.setGravity(Gravity.RIGHT);
            else
                textView.setGravity(Gravity.LEFT);

            textView.setText(messages.get(i).getMsgBody());
            current_layout.addView(textView);
        }
        db.close();
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
