package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        // contact to send to at the end.
        final Contact dest = new Contact();
        dest.setName("");
        dest.setPhonenb("");

        // Call contacts for autocompletion.
        List<Contact>db_contacts = db.getAllContacts();
        // set the adapter with contact list.
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db_contacts);
        // display the adapter.
        AutoCompleteTextView autoctextview = (AutoCompleteTextView) findViewById(R.id.write_msg_name_select);
        autoctextview.setAdapter(adapter);


        // ICI faire intent recup si user vient de display_contact.


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

        return true;
    }

    private void click_send_msg(final Contact dest) {
        final DatabaseHandler db = new DatabaseHandler(this);
        Button submit_btn = (Button) findViewById(R.id.write_msg_submit_btn);
        final AutoCompleteTextView actview = (AutoCompleteTextView) findViewById(R.id.write_msg_name_select);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dest.getId() != -1)
                {
                    Contact check_contact = db.getContact(dest.getId());
                    if (check_contact.getName().equals(actview.getText().toString()) &&
                            dest.getPhonenb().equals(check_contact.getPhonenb()))
                    {
                        TextView label_body = (TextView) findViewById(R.id.write_msg_body_label); //
                        label_body.setText("MESSAGE OK"); //
                            // CONTACT OK TO SEND
                    }
                    else
                    {
                         // il y a eu un changement. Clear all.
                        TextView label_body = (TextView) findViewById(R.id.write_msg_body_label); //
                        dest.setId(-1);
                        TextView nb_display = (TextView) findViewById(R.id.write_msg_display_num);
                        nb_display.setText("");
                        // si actv entry == Phone
                        if (PhoneNumberUtils.isGlobalPhoneNumber(actview.getText().toString())) {
                            dest.setName(actview.getText().toString());
                            label_body.setText("Send direct number"); //
                        }
                        else
                        {
                            label_body.setText("MESSAGE CHANGE name entered, search for it.");//
                            List<Contact> getcontact = db.getAllContactsfromNameStrict(actview.getText().toString());
                            if (getcontact.size() > 1)
                            {
                                label_body.setText("Ambiguous name"); //
                                dest.setId(-1);
                                return;
                            }
                            else if (getcontact.size() != 1)
                            {
                                label_body.setText("no contact by this name"); //
                                dest.setId(-1);
                                return;
                            }
                            else
                            {
                                label_body.setText("Found someone"); //
                                actview.setText(getcontact.get(0).getName());
                                nb_display.setText(getcontact.get(0).getPhonenb());
                                dest.setId(getcontact.get(0).getId());
                                dest.setName(getcontact.get(0).getName());
                                dest.setPhonenb(getcontact.get(0).getPhonenb());
                                return;
                            }
                        }
                        // si actv entry == phone

                    }
                }
                else // regular check
                {


                }
            }
        });
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


