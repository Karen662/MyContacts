package silin.tutorial.mycontacts;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends Fragment {

    private int mColor;

    private int mPosition;

    private Contact mContact;

    private TextView mContactName;

    private FieldsAdapter mAdapter;

    public ContactViewFragment() {
        // Required empty public constructor
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_view, container, false);

        mContact = ContactList.getInstance().get(mPosition);
        mContactName = (TextView) view.findViewById(R.id.contact_view_name);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.contact_view_toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.contact_view_edit) {
                    Intent i = new Intent(getActivity(), ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, mPosition);
                    startActivity(i);

                    return true;
                }

                return false;
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact.phoneNumbers, mContact.emails);
        listView.setAdapter(mAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.freedom_tower);
        Palette palette = Palette.generate(bitmap);

        mColor = palette.getMutedSwatch().getRgb();

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI() {
        mContactName.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);

            TextView contactValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            String value = (String) getItem(position);
            contactValue.setText(value);

            ImageView iconImageView = (ImageView) convertView.findViewById(R.id.field_icon);

            if (isFirst(position)) {
                if (isEmail(position)) iconImageView.setImageResource(R.drawable.ic_email);

                else iconImageView.setImageResource(R.drawable.ic_call);
            }
            iconImageView.setColorFilter(mColor);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_contact_view, menu);
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
