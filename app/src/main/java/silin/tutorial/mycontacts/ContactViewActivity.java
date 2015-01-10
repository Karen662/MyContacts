package silin.tutorial.mycontacts;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactViewActivity extends ActionBarActivity {

    public static final String EXTRA = "CVA_Contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Contact contact = (Contact) getIntent().getSerializableExtra(EXTRA);
        TextView textView = (TextView) findViewById(R.id.contact_view_name);
        textView.setText(contact.getName());

        RelativeLayout headerSection = (RelativeLayout) findViewById(R.id.contact_view_header);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x,
                height = point.y;

        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                return id == R.id.contact_view_edit;

            }
        });
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.contact_view_fields);
        listView.setAdapter(new FieldsAdapter(contact.phoneNumbers, contact.emails));
    }

    private class FieldsAdapter extends BaseAdapter {
        private ArrayList<String> mPhoneNumbers, mEmails;

        private FieldsAdapter(ArrayList<String> phoneNumbers, ArrayList<String> emails) {
            mPhoneNumbers = phoneNumbers;
            mEmails = emails;
        }

        @Override
        public int getCount() {
            return mEmails.size() + mPhoneNumbers.size();
        }

        @Override
        public Object getItem(int position) {
            if (isEmail(position)) return mEmails.get(position - mPhoneNumbers.size());
            else return mPhoneNumbers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);

            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            String value = (String) getItem(position);
            contactValue.setText(value);

            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.field_icon);

            if (isFirst(position)) {
                if (isEmail(position)) iconImageView.setImageResource(R.drawable.ic_email);

                else iconImageView.setImageResource(R.drawable.ic_call);
            }

            return convertView;
        }

        private boolean isEmail(int position) {
            return position > mPhoneNumbers.size() - 1;
        }

        private boolean isFirst(int position) {
            return position == 0 || position == mPhoneNumbers.size();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_view, menu);
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
