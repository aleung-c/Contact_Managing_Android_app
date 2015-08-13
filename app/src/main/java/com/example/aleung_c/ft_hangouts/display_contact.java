package com.example.aleung_c.ft_hangouts;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_contact, menu);
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

    public void Onclick_edit(View v) {

    }

    public void Onclick_delete(View v) {
        final DatabaseHandler db = new DatabaseHandler(this);

        Intent intent = getIntent();
        int id_to_display = (int) intent.getExtras().getInt("CONTACT_ID");
        final Contact contact_to_display = db.getContact(id_to_display);

        // alert dialog
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Delete contact ?");
        alertDialog.setMessage("Are you sure you want to delete this contact?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    // delete from db
                    db.deleteContact(contact_to_display);
                        back_to_list();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void back_to_list() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
