package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class add_contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        DatabaseHandler db = new DatabaseHandler(this);

        Intent intent = getIntent();
        if (getIntent().getExtras().getString("ADDCONTACT_ACTION").equals("EDIT")) {
            setTitle(R.string.title_activity_edit_contact);
            int id_to_display = (int) intent.getExtras().getInt("EDITCONTACT_ID");
            Contact contact_to_display = db.getContact(id_to_display);

            // Displaying infos //
            TextView intro_txt = (TextView) findViewById(R.id.add_contact_intro);
            intro_txt.setText(R.string.edit_contact_page_intro);

            EditText name = (EditText) findViewById(R.id.name_field);
            name.setText(contact_to_display.getName());

            EditText number = (EditText) findViewById(R.id.number_field);
            number.setText(contact_to_display.getPhonenb());

            Button submit_btn = (Button) findViewById(R.id.add_contact_submit_btn);
            submit_btn.setText(R.string.confirm_edit_btn);
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
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

   public void Submit(View view) {
       DatabaseHandler db = new DatabaseHandler(this);


       Contact contact_added = new Contact();


       EditText name_field = (EditText) findViewById(R.id.name_field);
       EditText number_field = (EditText) findViewById(R.id.number_field);

       if (are_fields_OK(name_field, number_field))
       {
           // set contact values
           contact_added.setName(name_field.getText().toString());
           contact_added.setPhonenb(number_field.getText().toString());

           if (getIntent().getExtras().getString("ADDCONTACT_ACTION").equals("ADD")) {
               db.addContact(contact_added);
           } else if (getIntent().getExtras().getString("ADDCONTACT_ACTION").equals("EDIT"))
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
        TextView error_text = (TextView) findViewById(R.id.add_contact_error_text);

        if (name_field.getText().toString() == null || name_field.getText().toString().isEmpty()
                || number_field.getText().toString() == null
                || number_field.getText().toString().isEmpty()) {
            error_text.setText(R.string.add_contact_fields_empty);
            return false;
        }
        error_text.setText("");
        return true;
    }
}

