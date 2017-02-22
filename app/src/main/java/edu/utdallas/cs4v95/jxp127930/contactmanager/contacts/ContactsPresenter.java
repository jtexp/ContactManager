package edu.utdallas.cs4v95.jxp127930.contactmanager.contacts;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsDataSource;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsRepository;

/**
 * Created by john on 2/15/17.
 *
 * The presenter communicates between the model and the view.
 *
 */

public class ContactsPresenter implements ContactsContract.Presenter {

    private final ContactsRepository contactsRepository;
    private final ContactsContract.View contactsView;


    public ContactsPresenter(@NonNull ContactsRepository contactsRepository, @NonNull ContactsContract.View contactsView) {
        this.contactsRepository = contactsRepository;
        this.contactsView = contactsView;

        contactsView.setPresenter(this);

    }

    @Override
    public void start() {
        loadContacts();
    }


    @Override
    public void loadContacts() {

        contactsRepository.getContacts(new ContactsDataSource.LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                Collections.sort(contacts);
                contactsView.showContacts(contacts);
            }

            @Override
            public void onDataNotAvailable() {

            }

        });

    }

    @Override
    public void openContactDetails(Contact requestedContact) {
        contactsView.showContactDetails(requestedContact.getId().toString());
    }

    @Override
    public void addNewContact() {
        UUID id = null;
        contactsView.showContactDetails(null);

    }
}
