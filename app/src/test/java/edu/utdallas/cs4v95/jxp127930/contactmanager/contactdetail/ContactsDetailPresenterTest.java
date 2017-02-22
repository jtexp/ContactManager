package edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsDataSource;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsRepository;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by john on 2/18/17.
 */

public class ContactsDetailPresenterTest {

    public static final String FIRST_NAME_TEST = "first";
    public static final String LAST_NAME_TEST = "last";
    public static final String MIDDLE_INITIAL_TEST = "m";
    public static final String PHONE_TEST = "214-555-5555";
    public static final String BIRTH_DATE_TEST = "03-03-2000";
    public static final String DATE_FIRST_CONTACT_TEST = "02-03-2001";

    public static final Contact CONTACT = new Contact(FIRST_NAME_TEST, MIDDLE_INITIAL_TEST,
            LAST_NAME_TEST, PHONE_TEST, BIRTH_DATE_TEST, DATE_FIRST_CONTACT_TEST);

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private ContactDetailContract.View contactDetailView;

    @Captor
    private ArgumentCaptor<ContactsDataSource.GetContactCallback> getContactCallbackCaptor;

    private ContactDetailPresenter contactDetailPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(contactDetailView.isActive()).thenReturn(true);
    }

    @Test
    public void  getContactFromRepositoryAndLoadIntoView() {
        contactDetailPresenter = new ContactDetailPresenter(contactsRepository, contactDetailView, CONTACT.getId().toString());;
        contactDetailPresenter.start();

        verify(contactsRepository).getContact(eq(CONTACT.getId()), getContactCallbackCaptor.capture());

        getContactCallbackCaptor.getValue().onContactLoaded(CONTACT);

        verify(contactDetailView).setFirstName(FIRST_NAME_TEST);
        verify(contactDetailView).setLastName(LAST_NAME_TEST);
        verify(contactDetailView).setMiddleInitial(MIDDLE_INITIAL_TEST);
        verify(contactDetailView).setPhoneNumber(PHONE_TEST);
        verify(contactDetailView).setBirthDate(BIRTH_DATE_TEST);
        verify(contactDetailView).setDateFirstContact(DATE_FIRST_CONTACT_TEST);


    }

}
