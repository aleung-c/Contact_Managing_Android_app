package com.example.aleung_c.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
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
        LinearLayout msg_layout = (LinearLayout)findViewById(R.id.readmsg_msgbody);
        for( int i = 0; i < messages.size(); i++ )
        {
            TextView textView_date = new TextView(this); // affiche la date
            TextView textView_msgbody = new TextView(this); // affiche le body
//            textView.setLayoutParams();

            textView_msgbody.setWidth(msg_layout.getWidth());
            if (messages.get(i).getSenderNb().equals(myphonenb)) {
                textView_msgbody.setGravity(Gravity.RIGHT);
                textView_date.setGravity(Gravity.RIGHT);
            }
            else {
                textView_msgbody.setGravity(Gravity.LEFT);
                textView_date.setGravity(Gravity.LEFT);
            }
            textView_date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11.0f);
            textView_date.setText(messages.get(i).getDate());
            textView_msgbody.setText(messages.get(i).getMsgBody());
            msg_layout.addView(textView_date);
            msg_layout.addView(textView_msgbody);
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
