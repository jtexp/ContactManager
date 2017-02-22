package edu.utdallas.cs4v95.jxp127930.contactmanager.contacts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsDataSource;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsRepository;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Created by john on 2/17/17.
 */

public class ContactsPresenterTest {

    private static List<Contact> CONTACTS;

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private ContactsContract.View contactsView;

    @Captor
    private ArgumentCaptor<ContactsDataSource.LoadContactsCallback> loadContactsCallCaptor;

    private ContactsPresenter contactsPresenter;

    @Before
    public void setupContactsPresenter() {
        MockitoAnnotations.initMocks(this);

        contactsPresenter = new ContactsPresenter(contactsRepository, contactsView);

        CONTACTS = new ArrayList<>();

        CONTACTS.add(new Contact("FirstName1", "LastName1", "MI1", "214-555-1000", "03-03-2000", "03-03-2000"));
        CONTACTS.add(new Contact("FirstName2", "LastName2", "MI2", "214-555-1001", "03-03-2001", "03-03-2001"));
        CONTACTS.add(new Contact("FirstName3", "LastName3", "MI3", "214-555-1002", "03-03-2002", "03-03-2002"));


    }

    @Test
    public void loadAllContactsFromRepositoryAndLoadIntoView() {
        contactsPresenter.loadContacts();

        verify(contactsRepository).getContacts(loadContactsCallCaptor.capture());
        loadContactsCallCaptor.getValue().onContactsLoaded(CONTACTS);

        InOrder inOrder = inOrder(contactsView);

        ArgumentCaptor<List> showContactsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(contactsView).showContacts(showContactsArgumentCaptor.capture());
        assertTrue(showContactsArgumentCaptor.getValue().size() == 3);



    }

    @Test
    public void clickOnActionBarPlus_ShowsContactDetailUi() {
        contactsPresenter.addNewContact();

        verify(contactsView).showContactDetails(null);
    }

    @Test
    public void clickOnTask_ShowContactDetailUi() {

        Contact requestedContact = new Contact("FirstName1", "LastName1", "MI1", "214-555-1000", "03-03-2000", "03-03-2000");
        contactsPresenter.openContactDetails(requestedContact);
        verify(contactsView).showContactDetails(requestedContact.getId().toString());

    }




}
