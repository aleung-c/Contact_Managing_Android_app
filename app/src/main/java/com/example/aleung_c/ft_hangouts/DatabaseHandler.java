package com.example.aleung_c.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Labels table name
    public static final String TABLE = "Contacts";
    // Labels Table Columns names
    // Contact table
    public static final String KEY_ID = "id";                   // string 0
    public static final String KEY_name = "name";               // string 1
    public static final String KEY_Phonenb = "phone_number";    // string 2
    public static final String KEY_Organisation = "organisation";// string 3
    public static final String KEY_Role = "role";               // string 4
    public static final String KEY_Mail = "mail";               // string 5

    // Message Table
    public static final String TABLE_MSG = "Messages";
    // reuse KEY_ID                                             // string 0
    public static final String KEY_date_created = "msg_date";   // string 1
    public static final String KEY_sender_name = "sender_name"; // string 2
    public static final String KEY_sender_nb = "sender_nb";     // string 3
    public static final String KEY_dest_name = "dest_name";     // string 4
    public static final String KEY_dest_nb = "dest_nb";         // string 5
    public static final String KEY_msg_body = "msg_body";       // string 6

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // on create of instance -> create databases.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Contact Table creation.
        // create string with sql request
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_name + " TEXT,"
                + KEY_Phonenb + " TEXT,"
                + KEY_Organisation + " TEXT,"
                + KEY_Role + " TEXT,"
                + KEY_Mail + " TEXT"
                + ")";
        // use string to create sql table
        db.execSQL(CREATE_CONTACTS_TABLE);
        // Messages Table creation.
        // create string with sql request
        CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MSG + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_date_created + " DATE,"
                + KEY_sender_name + " TEXT,"
                + KEY_sender_nb + " TEXT,"
                + KEY_dest_name + " TEXT,"
                + KEY_dest_nb + " TEXT,"
                + KEY_msg_body + " TEXT"
                + ")";
        // use string to create sql table
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // on upgrade of db, erase and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ", " + TABLE_MSG);
        // Create tables again
        onCreate(db);
    }

    // DATABASE HANDLING

    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db
        ContentValues values = new ContentValues(); // prepare list of values to add;

        //get values from contact object;
        values.put(KEY_name, contact.getName());
        values.put(KEY_Phonenb, contact.getPhonenb());
        values.put(KEY_Organisation, contact.getOrganisation());
        values.put(KEY_Role, contact.getRole());
        values.put(KEY_Mail, contact.getMail());

        // Inserting Row from values.
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase(); // open db
        Cursor cursor;

        // use cursor to get contact from db with id
        cursor = db.query(TABLE, new String[] { KEY_ID,
                        KEY_name, KEY_Phonenb, KEY_Organisation, KEY_Role, KEY_Mail }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        //fill contact object to return with values fetched in db
        assert cursor != null; // cursor cannot be null;
        Contact contact_to_add;
        contact_to_add = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
        cursor.close();
        return contact_to_add;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>(); // list to return;
        SQLiteDatabase db = this.getWritableDatabase(); // open db to fetch all contacts;
        String selectQuery = "SELECT  * FROM " + TABLE; // SQL request;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // cursor on first element;
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhonenb(cursor.getString(2));
                contact.setOrganisation(cursor.getString(3));
                contact.setRole(cursor.getString(4));
                contact.setMail(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    // get contacts from name - contains name
    public List<Contact> getAllContactsfromName_contains(String name_asked) {
        List<Contact> contactList = new ArrayList<>(); // list to return;
        SQLiteDatabase db = this.getWritableDatabase(); // open db to fetch all contacts;
        String selectQuery = "SELECT  * FROM " + TABLE +
                " WHERE " + KEY_name +
                " LIKE '%" + name_asked + "%'"; // SQL request;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // cursor on first element;
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhonenb(cursor.getString(2));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    // get contacts from name - name start with
    public List<Contact> getAllContactsfromName_start(String name_asked) {
        List<Contact> contactList = new ArrayList<>(); // list to return;
        SQLiteDatabase db = this.getWritableDatabase(); // open db to fetch all contacts;
        String selectQuery = "SELECT  * FROM " + TABLE +
                " WHERE " + KEY_name +
                " LIKE '" + name_asked + "%' ORDER BY " + KEY_name; // SQL request;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // cursor on first element;
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhonenb(cursor.getString(2));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    // get contacts from name strict
    public List<Contact> getAllContactsfromNameStrict(String name_asked) {
        List<Contact> contactList = new ArrayList<>(); // list to return;
        SQLiteDatabase db = this.getWritableDatabase(); // open db to fetch all contacts;
        String selectQuery = "SELECT  * FROM " + TABLE +
                " WHERE " + KEY_name +
                " LIKE '" + name_asked + "'"; // SQL request;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) { // cursor on first element;
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhonenb(cursor.getString(2));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        SQLiteDatabase db = this.getReadableDatabase(); // open db to fetch all contacts;
        String countQuery = "SELECT  * FROM " + TABLE; // SQL request;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_name, contact.getName());
        values.put(KEY_Phonenb, contact.getPhonenb());
        values.put(KEY_Organisation, contact.getOrganisation());
        values.put(KEY_Role, contact.getRole());
        values.put(KEY_Mail, contact.getMail());

        // updating row
        return db.update(TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    // --------------- MESSAGES HANDLING ------------------- //

    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase(); // open db
        ContentValues values = new ContentValues(); // prepare list of values to add;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        //get values from contact object;
        values.put(KEY_date_created, dateFormat.format(now));
        values.put(KEY_sender_name, message.getSenderName());
        values.put(KEY_sender_nb, message.getSenderNb());
        values.put(KEY_dest_name, message.getDestName());
        values.put(KEY_dest_nb, message.getDestNb());
        values.put(KEY_msg_body, message.getMsgBody());

        // Inserting Row from values.
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

}
