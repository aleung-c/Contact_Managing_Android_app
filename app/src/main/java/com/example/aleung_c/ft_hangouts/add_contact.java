package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class add_contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        DatabaseHandler db = new DatabaseHandler(this);

        Intent intent;
        intent = getIntent();
        String action = getIntent().getExtras().getString("ADDCONTACT_ACTION");
        assert action != null;
        if (action.equals("EDIT")) {
            setTitle(R.string.title_activity_edit_contact);
            int id_to_display = intent.getExtras().getInt("EDITCONTACT_ID");
            Contact contact_to_display = db.getContact(id_to_display);

            // Displaying infos //
            TextView intro_txt = (TextView) findViewById(R.id.add_contact_intro);
            intro_txt.setText(R.string.edit_contact_page_intro);

            EditText name = (EditText) findViewById(R.id.name_field);
            name.setText(contact_to_display.getName());

            EditText number = (EditText) findViewById(R.id.number_field);
            number.setText(contact_to_display.getPhonenb());

            EditText orga = (EditText) findViewById(R.id.orga_field);
            orga.setText(contact_to_display.getOrganisation());

            EditText role = (EditText) findViewById(R.id.role_field);
            role.setText(contact_to_display.getRole());

            EditText mail = (EditText) findViewById(R.id.mail_field);
            mail.setText(contact_to_display.getMail());

            Button submit_btn = (Button) findViewById(R.id.add_contact_submit_btn);
            submit_btn.setText(R.string.confirm_edit_btn);
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No menu
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

   public void Submit(View view) {
       DatabaseHandler db = new DatabaseHandler(this);


       Contact contact_added = new Contact();
       EditText name_field = (EditText) findViewById(R.id.name_field);
       EditText number_field = (EditText) findViewById(R.id.number_field);
       EditText orga_field = (EditText) findViewById(R.id.orga_field);
       EditText role_field = (EditText) findViewById(R.id.role_field);
       EditText mail_field = (EditText) findViewById(R.id.mail_field);
       if (are_fields_OK(name_field, number_field))
       {
           // set contact values
           contact_added.setName(name_field.getText().toString());
           contact_added.setPhonenb(number_field.getText().toString());
           contact_added.setOrganisation(orga_field.getText().toString());
           contact_added.setRole(role_field.getText().toString());
           contact_added.setMail(mail_field.getText().toString());

           String action = getIntent().getExtras().getString("ADDCONTACT_ACTION");
           assert action != null;
           if (action.equals("ADD")) {
               db.addContact(contact_added);
           }
           else if (action.equals("EDIT"))
           {
               // set contact id for updating
               contact_added.setId(db.getContact(getIntent().getExtras().getInt("EDITCONTACT_ID")).getId());
               db.updateContact(contact_added);
           }
           Intent intent = new Intent(this, MainActivity.class);
           intent.putExtra("CONTACT_ADDED", "true");
           db.close();
           startActivity(intent);
       }
    }

    public boolean are_fields_OK(EditText name_field, EditText number_field) {
        // Check differents erreurs
        DatabaseHandler db = new DatabaseHandler(this);
        TextView error_text = (TextView) findViewById(R.id.add_contact_error_text);

        // check fields empty ou null;
        if (name_field.getText().toString().isEmpty() || number_field.getText().toString().isEmpty()) {
            error_text.setText(R.string.add_contact_fields_empty);
            return false;
        }
        // check nb already exist;
        String action = getIntent().getExtras().getString("ADDCONTACT_ACTION");
        assert action != null;
        if (action.equals("ADD")) {
            List<Contact> search_nb = db.getAllContactsfromPhonenb(number_field.getText().toString());
            if (search_nb.size() != 0) {
                error_text.setText(R.string.add_contact_nb_exist);
                return false;
            }
        }
        error_text.setText("");
        return true;
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

