package edu.utdallas.cs4v95.jxp127930.contactmanager.contacts;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import edu.utdallas.cs4v95.jxp127930.contactmanager.Injection;
import edu.utdallas.cs4v95.jxp127930.contactmanager.R;

/**
 * Created by john on 2/15/17.
 *
 * This screen presents a list of contacts to the user. Using the toolbar the user can add a new
 * contact and by clicking on a contact the user can edit or delete a contact on the contactdetail
 * screen.
 *
 */

public class ContactsActivity extends AppCompatActivity {

    private ContactsPresenter contactsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        ContactsFragment fragment = (ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ContactsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


        // Create the presenter
        contactsPresenter = new ContactsPresenter(
                Injection.provideContactsRepository(getApplicationContext()), fragment);


    }

}
