package edu.utdallas.cs4v95.jxp127930.contactmanager.data.source;

import java.util.List;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;

/**
 * Created by john on 2/16/17.
 */

public interface ContactsDataSource {

    interface LoadContactsCallback {

        void onContactsLoaded(List<Contact> contacts);

        void onDataNotAvailable();

    }

    interface GetContactCallback {

        void onContactLoaded(Contact contact);

        void onDataNotAvailable();
    }

    interface NewContactCallback {


    }

    void getContacts(LoadContactsCallback callback);

    void getContact(UUID contactId, GetContactCallback callback);

    void saveContact(Contact contact);

    void deleteAllContacts();

    void deleteContact(UUID contactId);

}
