package com.example.aleung_c.ft_hangouts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aleung-c on 10/09/15.
 */
public class Message {
    int _message_id;
    String _date;
    String _sender_name = "";
    String _sender_nb = "";
    String _dest_name = "";
    String _dest_nb = "";
    String _msg_body = "";

    // constructors
    public Message(){
        this._message_id = -1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        this._date = dateFormat.format(now);
        this._sender_name = null;
        this._sender_nb = null;
        this._dest_name = null;
        this._dest_nb = null;
        this._msg_body = null;
    }


    public Message(int fetch){
        if (fetch != 0) {
            this._message_id = -1;
            this._sender_name = null;
            this._sender_nb = null;
            this._dest_name = null;
            this._dest_nb = null;
            this._msg_body = null;
        }
    }

    // accessors

    // Getters.
    public Integer getId() {return (this._message_id);}
    public String getDate() {return (this._date);}
    public String getSenderName() {return (this._sender_name);}
    public String getSenderNb() {return (this._sender_nb);}
    public String getDestName() {return (this._dest_name);}
    public String getDestNb() {return (this._dest_nb);}
    public String getMsgBody() {return (this._msg_body);}

//    public String toString() {return (this._msg_body);}

    // Setters;
    public void setId(Integer id) {this._message_id = id;}
    public void setDate(String date) {this._date = date;}
    public void setSendName(String sendname) {this._sender_name = sendname;}
    public void setSendNb(String sendnb) {this._sender_nb = sendnb;}
    public void setDestName(String destname) {this._dest_name = destname;}
    public void setDestNb(String destnb) {this._dest_nb = destnb;}
    public void setMsgBody(String msgbody) {this._msg_body = msgbody;}
}
