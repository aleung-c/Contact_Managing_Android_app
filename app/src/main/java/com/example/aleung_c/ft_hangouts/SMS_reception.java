package com.example.aleung_c.ft_hangouts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.util.List;

public class SMS_reception extends BroadcastReceiver {

    public SMS_reception() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");

        final DatabaseHandler db = new DatabaseHandler(context);

        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            assert pdusObj != null;
            for (Object aPdusObj : pdusObj) {
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();

                // ==> Stock message in db.
                receive_msg(context, senderNum, message);
                // Show alert
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "SMS received from: " + senderNum + ", message: " + message, duration);
                toast.show();

                // check facon de voir
                if (App_visibility.isActivityVisible()) { // si app est ouverte
                    Intent newintent = new Intent(context, Readmsg.class);
                    newintent.putExtra("CONTACT_ID", db.getContactFromNb(senderNum).getId());
                    db.close();
                    newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newintent);
                } else
                    pop_notification(context, senderNum, message);
                db.close();
            }
        }
    }

    private void receive_msg(Context context, String senderNum, String msg_body) {
        final Message msg_to_rec = new Message(); // msg to send a la fin
        final DatabaseHandler db = new DatabaseHandler(context);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String myphonenb = tm.getLine1Number();

        List<Contact> search_nb = db.getAllContactsfromPhonenb(senderNum);
        // CHECK NB EXISTANT ou non.
        if (search_nb.size() != 0) { // si nb existe
            msg_to_rec.setSendName(search_nb.get(0).getName());
            msg_to_rec.setSendNb(search_nb.get(0).getPhonenb());
        }
        else
        {
            // new number, adding contact to db.
            Contact unknown_contact = new Contact();
            unknown_contact.setName(senderNum);
            unknown_contact.setPhonenb(senderNum);
            db.addContact(unknown_contact);
            msg_to_rec.setSendName(senderNum);
            msg_to_rec.setSendNb(senderNum);
        }
        msg_to_rec.setDestName("Me");
        msg_to_rec.setDestNb(myphonenb);
        msg_to_rec.setMsgBody(msg_body);
        db.addMessage(msg_to_rec);
        db.close();
    }

    private void pop_notification(Context context, String senderNum, String msg_body) {
        final DatabaseHandler db = new DatabaseHandler(context);
        Intent intent = new Intent(context, Readmsg.class);
        intent.putExtra("CONTACT_ID", db.getContactFromNb(senderNum).getId());
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(context)
                .setContentTitle("New sms from " + db.getContactFromNb(senderNum).getName())
                .setContentText(msg_body)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentIntent(pIntent)
                .setAutoCancel(true).build();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
        db.close();
    }
}


