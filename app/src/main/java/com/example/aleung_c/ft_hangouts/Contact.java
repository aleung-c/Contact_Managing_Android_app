package com.example.aleung_c.ft_hangouts;

/**
 * Created by aleung-c on 10/08/15.
 */
public class Contact {
    int _contact_id;
    String _contact_name;
    String _contact_phonenb;

    // constructors

    public Contact(){
        this._contact_id = 0;
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
            public Integer getId() {
                return (this._contact_id);
            }

            public String getName() {
                return (this._contact_name);
            }

            public String getPhonenb() {
                return (this._contact_phonenb);
            }

            public String toString()
        {
            return (this._contact_name);
        }

        // Setters;
            public void setId(Integer id) {
                this._contact_id = id;
            }

            public void setName(String name) {
               this._contact_name = name;
            }

            public void setPhonenb(String phonenb) {
                this._contact_phonenb = phonenb;
            }
}
