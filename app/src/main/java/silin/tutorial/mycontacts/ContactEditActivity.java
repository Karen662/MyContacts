package silin.tutorial.mycontacts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactEditActivity extends ActionBarActivity {
    public static final String EXTRA = "CEA_Contact";

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        mContact = (Contact) getIntent().getSerializableExtra(EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.contact_edit_name);
                mContact.setName(editName.getText().toString());

                mContact.phoneNumbers = getSectionValues(R.id.phonenumber_section);
                mContact.emails = getSectionValues(R.id.email_section);

                Toast.makeText(ContactEditActivity.this, getString(R.string.contact_edit_success_toast), Toast.LENGTH_LONG).show();

                finish();
            }
        });

        EditText editName = (EditText) findViewById(R.id.contact_edit_name);
        editName.setText(mContact.getName());

        addToSection(R.id.phonenumber_section, mContact.phoneNumbers);
        addToSection(R.id.email_section, mContact.emails);

        TextView addNewPhoneNumberTextView = (TextView) findViewById(R.id.add_new_phonenumber),
                addNewEmailTextView = (TextView) findViewById(R.id.add_new_email);

        addNewPhoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.phonenumber_section, "");
            }
        });

        addNewEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.email_section, "");
            }
        });
    }

    private ArrayList<String> getSectionValues(int sectionID) {
        LinearLayout sectionLayout = (LinearLayout) findViewById(sectionID);
        ArrayList<String> values = new ArrayList<String>();

        for (int i = 0; i < sectionLayout.getChildCount(); i++) {
            EditText editNumber = (EditText) sectionLayout.getChildAt(i);
            values.add(editNumber.getText().toString());
        }

        return values;
    }

    private void addToSection(int section, String value) {
        LinearLayout sectionLayout = (LinearLayout) findViewById(section);
        EditText editText = new EditText(ContactEditActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        editText.setLayoutParams(layoutParams);
        editText.setText(value);
        sectionLayout.addView(editText);

    }

    private void addToSection(int section, ArrayList<String> values) {
        LinearLayout sectionLayout = (LinearLayout) findViewById(section);

        for (int i = 0; i < values.size(); i++) {
            EditText editText = new EditText(ContactEditActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(layoutParams);
            editText.setText(values.get(i));
            sectionLayout.addView(editText);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);
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
}
