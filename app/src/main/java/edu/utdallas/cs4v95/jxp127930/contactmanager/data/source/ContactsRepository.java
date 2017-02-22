package edu.utdallas.cs4v95.jxp127930.contactmanager.data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;

/**
 * Created by john on 2/16/17.
 */

public class ContactsRepository implements ContactsDataSource {

    private static ContactsRepository INSTANCE = null;

    private final ContactsDataSource contactsLocalDataSource;

    Map<UUID, Contact> cachedContacts;

    boolean cacheIsDirty = false;

    private ContactsRepository(@NonNull ContactsDataSource contactsLocalDataSource) {
        this.contactsLocalDataSource = contactsLocalDataSource;
    }

    public static ContactsRepository getInstance(ContactsDataSource contactsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ContactsRepository(contactsLocalDataSource);;
        }
        return INSTANCE;
    }

    public static void destroyInstance() { INSTANCE = null; }

    @Override
    public void getContacts(final LoadContactsCallback callback) {

        if (cachedContacts != null && !cacheIsDirty) {
            callback.onContactsLoaded(new ArrayList<>(cachedContacts.values()));
        }

        contactsLocalDataSource.getContacts(new LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                refreshCache(contacts);
                callback.onContactsLoaded(new ArrayList<>(cachedContacts.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });


    }

    @Override
    public void getContact(final UUID contactId, final GetContactCallback callback) {
        final Contact cachedContact = getContactWithId(contactId);

        if (cachedContact != null) {
            callback.onContactLoaded(cachedContact);
            return;
        }

        contactsLocalDataSource.getContact(contactId, new GetContactCallback() {
            @Override
            public void onContactLoaded(Contact contact) {
                if (cachedContacts == null) {
                    cachedContacts = new LinkedHashMap<>();
                }
                cachedContacts.put(contact.getId(), contact);
                callback.onContactLoaded(contact);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    private Contact getContactWithId(UUID id) {
        if(cachedContacts == null || cachedContacts.isEmpty()) {
            return null;
        }
        else {
            return cachedContacts.get(id);
        }
    }

    @Override
    public void deleteContact(UUID contactId) {
        contactsLocalDataSource.deleteContact(contactId);
        cachedContacts.remove(contactId);
    }

    @Override
    public void saveContact(Contact contact) {
        contactsLocalDataSource.saveContact(contact);

        if (cachedContacts == null) {
            cachedContacts = new LinkedHashMap<>();
        }
        cachedContacts.put(contact.getId(), contact);

    }

    @Override
    public void deleteAllContacts() {
        contactsLocalDataSource.deleteAllContacts();

        if (cachedContacts == null) {
            cachedContacts  = new LinkedHashMap<>();
        }
        cachedContacts.clear();
    }

    private void refreshCache(List<Contact> contacts) {
        if (cachedContacts == null) {
            cachedContacts = new LinkedHashMap<>();
        }
        cachedContacts.clear();
        for (Contact contact: contacts) {
            cachedContacts.put(contact.getId(), contact);
        }
        cacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Contact> contacts) {
        contactsLocalDataSource.deleteAllContacts();
        for (Contact contact: contacts) {
            contactsLocalDataSource.saveContact(contact);
        }
    }

    public void newContact() {

    }
}
