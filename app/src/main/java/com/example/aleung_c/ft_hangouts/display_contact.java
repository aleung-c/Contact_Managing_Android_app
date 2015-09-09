package com.example.aleung_c.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class display_contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        DatabaseHandler db = new DatabaseHandler(this);

        Intent intent = getIntent();
        int id_to_display = (int) intent.getExtras().getInt("CONTACT_ID");
        Contact contact_to_display = db.getContact(id_to_display);

        // Displaying infos //
        TextView name = (TextView) findViewById(R.id.contact_name);
        name.setText(contact_to_display.getName());

        TextView number = (TextView) findViewById(R.id.contact_nb);
        number.setText(contact_to_display.getPhonenb());

        TextView organisation = (TextView) findViewById(R.id.contact_orga);
        organisation.setText(contact_to_display.getOrganisation());

        TextView role = (TextView) findViewById(R.id.contact_role);
        role.setText(contact_to_display.getRole());

        TextView mail = (TextView) findViewById(R.id.contact_mail);
        mail.setText(contact_to_display.getRole());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Onclick_edit(View v) {

    }

    public void Onclick_delete(View v) {
        final DatabaseHandler db = new DatabaseHandler(this);

        Intent intent = getIntent();
        int id_to_display = (int) intent.getExtras().getInt("CONTACT_ID");
        final Contact contact_to_display = db.getContact(id_to_display);

        // alert dialog
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.Delete_window_title));
        alertDialog.setMessage(getResources().getString(R.string.Delete_window_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.txt_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete from db
                        db.deleteContact(contact_to_display);
                        back_to_list();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.txt_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void goto_write_msg(View v) {
        Intent prev_intent = getIntent();
        int id_to_pass = (int) prev_intent.getExtras().getInt("CONTACT_ID");
        Intent intent = new Intent(this, write_msg.class);
        intent.putExtra("WRITE_MSG_ID", (int) id_to_pass);
        startActivity(intent);
    }

    public void goto_readmsg(View v) {
        Intent prev_intent = getIntent();
        int id_to_display = (int) prev_intent.getExtras().getInt("CONTACT_ID");
        Intent intent = new Intent(this, Readmsg.class);
        intent.putExtra("CONTACT_ID", id_to_display);
        startActivity(intent);
    }

    public void goto_edit_contact(View v) {
        Intent prev_intent = getIntent();
        int id_to_display = (int) prev_intent.getExtras().getInt("CONTACT_ID");
        Intent intent = new Intent(this, add_contact.class);
        intent.putExtra("ADDCONTACT_ACTION", "EDIT");
        intent.putExtra("EDITCONTACT_ID", (int) id_to_display);
        startActivity(intent);
    }



    public void back_to_list() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
