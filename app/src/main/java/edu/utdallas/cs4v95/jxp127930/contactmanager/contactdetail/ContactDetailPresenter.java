package edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsDataSource;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsRepository;

/**
 * Created by john on 2/15/17.
 *
 * The presenter communicates between the model and the view.
 */

public class ContactDetailPresenter  implements ContactDetailContract.Presenter,
        ContactsDataSource.GetContactCallback {

    private final ContactsRepository contactsRepository;
    private final ContactDetailContract.View contactDetailView;
    private final String contactId;


    public ContactDetailPresenter(@NonNull ContactsRepository contactsRepository,
                                  @NonNull ContactDetailContract.View contactDetailView,
                                  String contactId) {
        this.contactsRepository = contactsRepository;
        this.contactDetailView = contactDetailView;
        this.contactId = contactId;
        contactDetailView.setPresenter(this);

    }

    @Override
    public void start() {
        if (!isNewTask() && isDataMissing()) {
            populateContact();
        }
        else {
            createDefaults();
        }
    }

    @Override
    public void populateContact() {
        contactsRepository.getContact(UUID.fromString(contactId), this);
    }

    @Override
    public void onContactLoaded(Contact contact) {
        if (contactDetailView.isActive()) {
            contactDetailView.setFirstName(contact.getFirstName());
            contactDetailView.setLastName(contact.getLastName());
            contactDetailView.setMiddleInitial(contact.getMiddleInitial());
            contactDetailView.setPhoneNumber(contact.getPhoneNumber());
            contactDetailView.setBirthDate(contact.getBirthDate());
            contactDetailView.setDateFirstContact(contact.getDateFirstContact());
        }
    }


    private void createDefaults() {
        contactDetailView.setDateFirstContact(new SimpleDateFormat("MM-dd-yyyy")
                .format(Calendar.getInstance().getTime()).toString());

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void deleteContact() {
        if (!isNewTask()) {
            contactsRepository.deleteContact(UUID.fromString(contactId));
        }
        contactDetailView.showContactsList();
    }

    @Override
    public void saveContact(String firstName, String lastName, String middleInitial,
                            String phoneNumber, String birthDate, String dateFirstContact) {
        if (isNewTask()) {
            createContact(firstName, lastName, middleInitial, phoneNumber, birthDate, dateFirstContact);
        }
        else {
            updateContact(firstName, lastName, middleInitial, phoneNumber, birthDate, dateFirstContact);
        }

    }


    private void updateContact(String firstName, String lastName, String middleInitial,
                            String phoneNumber, String birthDate, String dateFirstContact) {

        if (firstName.length() < 1) {
            contactDetailView.showEmptyContactError(
                    ContactDetailContract.ErrorMessage.FIRST_NAME_MISSING);
        } else if (lastName.length() < 1) {
            contactDetailView.showEmptyContactError(
                    ContactDetailContract.ErrorMessage.LAST_NAME_MISSING);
        } else {
            Contact contact = new Contact(UUID.fromString(contactId));
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setMiddleInitial(middleInitial);
            try {
                contact.setPhoneNumber(phoneNumber);
            } catch (IllegalArgumentException ex) {
                contactDetailView.showEmptyContactError(
                        ContactDetailContract.ErrorMessage.PHONE_FORMAT_ERROR);
                return;
            }
            contact.setBirthDate(birthDate);
            contact.setDateFirstContact(dateFirstContact);
            contactsRepository.saveContact(contact);
            contactDetailView.showContactsList();

        }
    }

    private void createContact(String firstName, String lastName, String middleInitial,
                               String phoneNumber, String birthDate, String dateFirstContact) {

        if (firstName.length() < 1) {
            contactDetailView.showEmptyContactError(
                    ContactDetailContract.ErrorMessage.FIRST_NAME_MISSING);
        }
        else if (lastName.length() < 1) {
            contactDetailView.showEmptyContactError(
                    ContactDetailContract.ErrorMessage.LAST_NAME_MISSING);
        }
        else {
            Contact contact = new Contact();
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setMiddleInitial(middleInitial);
            try {
                contact.setPhoneNumber(phoneNumber);
            } catch(IllegalArgumentException ex) {
                contactDetailView.showEmptyContactError(
                        ContactDetailContract.ErrorMessage.PHONE_FORMAT_ERROR);
                return;
            }
            contact.setBirthDate(birthDate);
            contact.setDateFirstContact(dateFirstContact);
            contactsRepository.saveContact(contact);
            contactDetailView.showContactsList();
        }
    }

    @Override
    public boolean isDataMissing() {

        return true;
    }


    private boolean isNewTask() {
        return contactId == null;
    }


}
