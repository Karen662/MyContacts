package silin.tutorial.mycontacts;

import java.util.ArrayList;

public class ContactList extends ArrayList<Contact> {
    private static ContactList ourInstance = new ContactList();

    public static ContactList getInstance() {
        return ourInstance;
    }

    private ContactList() {
    }
}
