package edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail;

import edu.utdallas.cs4v95.jxp127930.contactmanager.BasePresenter;
import edu.utdallas.cs4v95.jxp127930.contactmanager.BaseView;

/**
 * Created by john on 2/15/17.
 *
 * This interface are the public methods that connect the view and presenter.
 *
 */

public interface ContactDetailContract {

    interface View extends BaseView<Presenter> {

        void setFirstName(String firstName);
        void setLastName(String lastName);
        void setMiddleInitial(String middleInitial);
        void setPhoneNumber(String phoneNumber);
        void setBirthDate(String birthDate);
        void setDateFirstContact(String dateFirstContact);
        void showEmptyContactError(ErrorMessage errorMsg);
        void showContactsList();
        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void populateContact();
        void deleteContact();
        void saveContact(String firstName, String lastName, String middleInitial, String phoneNumber,
                         String birthDate, String dateFirstContact);
        boolean isDataMissing();


    }

    enum ErrorMessage {
        FIRST_NAME_MISSING,
        LAST_NAME_MISSING,
        PHONE_FORMAT_ERROR;
    }


}
