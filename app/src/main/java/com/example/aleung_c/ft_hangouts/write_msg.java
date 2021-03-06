package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class write_msg extends Activity implements View.OnKeyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_msg);

        DatabaseHandler db = new DatabaseHandler(this); // db connection.
        // contact to send to at the end.
        final Contact dest = new Contact();
        dest.setName("");
        dest.setPhonenb("");

        // the search bar.
        AutoCompleteTextView autoctextview = (AutoCompleteTextView) findViewById(R.id.write_msg_name_select);

        // intent recup si user vient de display_contact.
        int passed_id = getIntent().getExtras().getInt("WRITE_MSG_ID");
        // si entree depuis display contact page
        if (passed_id != -1)
        {
            dest.setId(passed_id);
            // ICI A FAIRE ADD Contact infos...
            dest.setName(db.getContact(passed_id).getName());
            autoctextview.setText(dest.getName());
            dest.setPhonenb(db.getContact(passed_id).getPhonenb());
            TextView display_num = (TextView) findViewById(R.id.write_msg_display_num);
            display_num.setText(dest.getPhonenb());
        }
        // SET AUTO COMPLETE TEXT VIEW
        // Call contacts for autocompletion.
        List<Contact>db_contacts = db.getAllContacts();
        // set the adapter with contact list.
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db_contacts);
        // display the adapter.
        autoctextview.setAdapter(adapter);
        // SI user click sur un choix de actv
        autoctextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position).toString();
                Contact contact = (Contact) parent.getItemAtPosition(position);
                dest.setId(contact.getId());
                dest.setName(contact.getName());
                dest.setPhonenb(contact.getPhonenb());
                String phone_nb = contact.getPhonenb();
                TextView display_num = (TextView) findViewById(R.id.write_msg_display_num);
                display_num.setText(phone_nb);
            }
        });
        click_send_msg(dest);
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No menu.

        return true;
    }

    private void click_send_msg(final Contact dest) {
        // lorsque click sur le btn send.
        final DatabaseHandler db = new DatabaseHandler(this);
        Button submit_btn = (Button) findViewById(R.id.write_msg_submit_btn);
        final AutoCompleteTextView actview = (AutoCompleteTextView) findViewById(R.id.write_msg_name_select);
        final TextView label_body = (TextView) findViewById(R.id.write_msg_display_num); //
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dest.getId() != -1) {
                    Contact check_contact = db.getContact(dest.getId());
                    if (check_contact.getName().equals(actview.getText().toString()) &&
                            dest.getPhonenb().equals(check_contact.getPhonenb())) {
                        label_body.setTextColor(Color.parseColor("#33CCFF")); //
                        label_body.setText(R.string.write_msg_error_log_OK); // contact found OK

                        // CONTACT OK TO SEND FILL MESSAGE OBJECT
                        fill_msg_and_send(dest);
                        goto_readmsg(dest.getId());
                    }
                    else {
                        // il y a eu un changement. Clear all.
                        // TextView label_body = (TextView) findViewById(R.id.write_msg_body_label); //
                        dest.setId(-1);
                        TextView nb_display = (TextView) findViewById(R.id.write_msg_display_num);
                        nb_display.setText("");
                        // go check lentry puisquil y a eu un changement
                        check_entry(dest, label_body, actview);
                    }
                }
                else { // go check lentry, clear.
                    TextView nb_display = (TextView) findViewById(R.id.write_msg_display_num);
                    nb_display.setText("");
                    check_entry(dest, label_body, actview);
                }
            }
        });
    }

    private void check_entry(Contact dest, final TextView label_body, AutoCompleteTextView actview) {
       // sert a check le format de la nouvelle entree dans la bar de contact.
        final DatabaseHandler db = new DatabaseHandler(this);
        if (PhoneNumberUtils.isGlobalPhoneNumber(actview.getText().toString())) { // DIRECT NB ROUTE
            dest.setName(actview.getText().toString());
            dest.setPhonenb(actview.getText().toString());
            label_body.setTextColor(Color.parseColor("#33CCFF")); //
            label_body.setText(R.string.write_msg_error_log_direct_nb); //

            List <Contact>search_nb = db.getAllContactsfromPhonenb(actview.getText().toString());
            // BUG LORSQUE num existe deja sous un autre nb ==> CHECK NB EXISTANT.
            if (search_nb.size() != 0) {
                // Nb existe.
                label_body.setText(R.string.write_msg_error_log_OKnb);
                dest.setId(search_nb.get(0).getId());
                dest.setName(search_nb.get(0).getName());
                dest.setPhonenb(search_nb.get(0).getPhonenb());
                fill_msg_and_send(dest);
                goto_readmsg(search_nb.get(0).getId());
                return;
            }
            // Nb n'existe pas. creating new contact.
            Contact unknown_contact = new Contact();
            unknown_contact.setName(actview.getText().toString());
            unknown_contact.setPhonenb(actview.getText().toString());
            db.addContact(unknown_contact);
            int id_ucontact = db.getContactFromNb(unknown_contact.getPhonenb()).getId();
            fill_msg_and_send(dest);
            goto_readmsg(id_ucontact);
        }
        else
        {
            List<Contact> getcontact = db.getAllContactsfromNameStrict(actview.getText().toString());
            if (getcontact.size() > 1) // ERROR route ambiguous name
            {
                label_body.setTextColor(Color.parseColor("#FF6699")); //
                label_body.setText(R.string.write_msg_error_log_ambiguous); //
                dest.setId(-1);
                return;
            }
            else if (getcontact.size() != 1) // ERROR route no contact by that name
            {
                label_body.setTextColor(Color.parseColor("#FF6699")); //
                label_body.setText(R.string.write_msg_error_log_no_contact); //
                dest.setId(-1);
                return;
            }
            else
            {
                label_body.setTextColor(Color.parseColor("#33CCFF")); //
                label_body.setText(R.string.write_msg_error_log_OK); //
                actview.setText(getcontact.get(0).getName());
                label_body.setText(getcontact.get(0).getPhonenb());
                dest.setId(getcontact.get(0).getId());
                dest.setName(getcontact.get(0).getName());
                dest.setPhonenb(getcontact.get(0).getPhonenb());
                fill_msg_and_send(dest);
                goto_readmsg(dest.getId());
                return;
            }
        }
    }

    private void fill_msg_and_send(Contact dest) {
        final Message msg_to_send = new Message(); // msg to send a la fin
        final DatabaseHandler db = new DatabaseHandler(this);
        msg_to_send.setDestName(dest.getName());
        msg_to_send.setDestNb(dest.getPhonenb());
        EditText msg_body = (EditText) findViewById(R.id.write_msg_body_text);
        msg_to_send.setMsgBody(msg_body.getText().toString());

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String myphonenb = tm.getLine1Number();
        msg_to_send.setSendName("Me");
        msg_to_send.setSendNb(myphonenb);
        db.addMessage(msg_to_send);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(dest.getPhonenb(), null, msg_to_send.toString(), null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        db.close();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        View view = this.getCurrentFocus();
        if (keyCode == 66) // == ENTER
        {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, UserSettingActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goto_readmsg(int id) {
        Intent intent = new Intent(this, Readmsg.class);
        intent.putExtra("CONTACT_ID", id);
        startActivity(intent);
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


