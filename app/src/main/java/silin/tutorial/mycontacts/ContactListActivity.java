package silin.tutorial.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class ContactListActivity extends ActionBarActivity implements ContactListFragment.Contract {

    private ContactListFragment mContactListFragment;

    private ContactViewFragment mContactViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mContactListFragment = (ContactListFragment) (getFragmentManager().findFragmentById(R.id.list_fragment_container));

        if (mContactListFragment == null)
        {
            mContactListFragment = new ContactListFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.list_fragment_container, mContactListFragment)
                    .commit();
        }

        mContactViewFragment = (ContactViewFragment) (getFragmentManager().findFragmentById(R.id.view_fragment_container));

        if (mContactViewFragment == null && findViewById(R.id.view_fragment_container) != null)
        {
            mContactViewFragment = new ContactViewFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.view_fragment_container, mContactViewFragment)
                    .commit();
        }
    }


    @Override
    public void selectedPosition(int position) {
        Intent i = new Intent(this, ContactViewActivity.class);
        i.putExtra(ContactViewActivity.EXTRA, position);
        startActivity(i);
    }
}
