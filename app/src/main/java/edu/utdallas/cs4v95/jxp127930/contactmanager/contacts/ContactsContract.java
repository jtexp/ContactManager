package edu.utdallas.cs4v95.jxp127930.contactmanager.contacts;

import java.util.List;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.BasePresenter;
import edu.utdallas.cs4v95.jxp127930.contactmanager.BaseView;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;

/**
 * Created by john on 2/15/17.
 *
 * This interface are the public methods that connect the view and presenter.
 *
 */

public interface ContactsContract {
    interface View extends BaseView<Presenter> {

        void showContacts(List<Contact> contacts);

        void showContactDetails(String id);

    }

    interface Presenter extends BasePresenter {

        void loadContacts();

        void addNewContact();

        void openContactDetails(Contact requestedContact);

    }

}
