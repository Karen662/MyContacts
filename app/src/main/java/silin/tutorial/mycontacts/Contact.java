package silin.tutorial.mycontacts;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String mName;

    protected ArrayList<String> emails,
        phoneNumbers;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
