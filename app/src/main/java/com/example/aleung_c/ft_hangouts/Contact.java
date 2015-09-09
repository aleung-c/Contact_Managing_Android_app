package com.example.aleung_c.ft_hangouts;

/**
 * Created by aleung-c on 10/08/15.
 */
public class Contact {
    int _contact_id;
    String _contact_name;
    String _contact_phonenb;
    String _contact_organisation = "";
    String _contact_role = "";
    String _contact_mail = "";

    // constructors

    public Contact(){
        this._contact_id = -1;
        this._contact_name = null;
        this._contact_phonenb = null;
    }

    public Contact(String name, String _phone_number){
        this._contact_name = name;
        this._contact_phonenb = _phone_number;
    }

    public Contact(int id, String name, String _phone_number){
        this._contact_id = id;
        this._contact_name = name;
        this._contact_phonenb = _phone_number;
    }

    // accessors

        // Getters.
            public Integer getId() {return (this._contact_id);}
            public String getName() {return (this._contact_name);}
            public String getPhonenb() {return (this._contact_phonenb);}
            public String getOrganisation() {return (this._contact_organisation);}
            public String getRole() {return (this._contact_role);}
            public String getMail() {return (this._contact_mail);}
            public String toString() {return (this._contact_name);}

        // Setters;
            public void setId(Integer id) {this._contact_id = id;}
            public void setName(String name) {this._contact_name = name;}
            public void setPhonenb(String phonenb) {
                this._contact_phonenb = phonenb;
            }
            public void setOrganisation(String organisation) {
        this._contact_organisation = organisation;
    }
            public void setRole(String role) {
        this._contact_role = role;
    }
            public void setMail(String mail) {
        this._contact_mail = mail;
    }
}
