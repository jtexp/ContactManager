package edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.Injection;
import edu.utdallas.cs4v95.jxp127930.contactmanager.R;

/**
 * Created by john on 2/15/17.
 *
 * This screen allows the user to enter data into a new contact, save a contact, edit a contact
 * and delete a contact.
 *
 */

public class ContactDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT_ID = "CONTACT_ID";

    private ContactDetailPresenter contactDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        String contactId = getIntent().getStringExtra(EXTRA_CONTACT_ID);


        ContactDetailFragment fragment = (ContactDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ContactDetailFragment.newInstance();

            if (getIntent().hasExtra(EXTRA_CONTACT_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_CONTACT_ID, contactId);
                fragment.setArguments(bundle);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


        // Create the presenter
        contactDetailPresenter = new ContactDetailPresenter(
                Injection.provideContactsRepository(getApplicationContext()), fragment, contactId);

    }



}
