package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();
        ListView listContent = (ListView) findViewById(R.id.contact_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listContent.setAdapter(adapter);
        listContent.setOnItemClickListener(this);
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

//    @Override
//    public void onBackPressed() {
//        onDestroy();
//        System.exit(0);
//    }

    public void goto_contact_list(View v)
    {
        Intent intent = new Intent(this, search_contacts.class);
        startActivity(intent);
    }

    public void goto_add_contact(View v)
    {
        Intent intent = new Intent(this, add_contact.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        LinearLayout linearLayout = new LinearLayout(this);
//        setContentView(linearLayout);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);

        String name = (String) parent.getItemAtPosition(position).toString();
//        TextView text = new TextView(this);


        Intent intent = new Intent(this, display_contact.class);

        intent.putExtra("CONTACT_NAME", name);
        startActivity(intent);
    }
}
