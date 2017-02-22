package edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.support.design.widget.Snackbar;

import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.R;

import static edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail.ContactDetailContract.ErrorMessage.PHONE_FORMAT_ERROR;

/**
 * Created by john on 2/15/17.
 *
 * This fragment implements the View
 *
 */

public class ContactDetailFragment extends Fragment implements ContactDetailContract.View {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextMiddleInitial;
    private EditText editTextPhoneNumber;
    private EditText editTextBirthDate;
    private EditText editTextDateFirstContact;


    private ContactDetailContract.Presenter presenter;


    public ContactDetailFragment() {

    }

    public static ContactDetailFragment newInstance() {
        return new ContactDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull ContactDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_contact:
                presenter.saveContact(editTextFirstName.getText().toString(),
                  editTextLastName.getText().toString(), editTextMiddleInitial.getText().toString(),
                  editTextPhoneNumber.getText().toString(), editTextBirthDate.getText().toString(),
                  editTextDateFirstContact.getText().toString());
                return true;
            case R.id.menu_item_delete_contact:
                presenter.deleteContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        editTextFirstName = (EditText) view.findViewById(R.id.edit_text_first_name);
        editTextLastName = (EditText) view.findViewById(R.id.edit_text_last_name);
        editTextMiddleInitial = (EditText) view.findViewById(R.id.edit_text_middle_initial);
        editTextPhoneNumber = (EditText) view.findViewById(R.id.edit_text_phone_number);
        editTextBirthDate = (EditText) view.findViewById(R.id.edit_text_birth_date);
        editTextDateFirstContact = (EditText) view.findViewById(R.id.edit_text_date_first_contact);



        return view;
    }

    @Override
    public void setFirstName(String firstName) {
        editTextFirstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        editTextLastName.setText(lastName);
    }

    @Override
    public void setMiddleInitial(String middleInitial) {
        editTextMiddleInitial.setText(middleInitial);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        editTextPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void setBirthDate(String birthDate) {
        editTextBirthDate.setText(birthDate);
    }

    @Override
    public void setDateFirstContact(String dateFirstContact) {
        editTextDateFirstContact.setText(dateFirstContact);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showContactsList() {
        getActivity().finish();
    }

    @Override
    public void showEmptyContactError(ContactDetailContract.ErrorMessage errorType) {

        int errorMsg;

        switch(errorType) {
            case PHONE_FORMAT_ERROR:
                errorMsg = R.string.phone_wrong_format;
                break;
            case FIRST_NAME_MISSING:
                errorMsg = R.string.first_name_missing;
                break;
            case LAST_NAME_MISSING:
                errorMsg = R.string.last_name_missing;
                break;
            default:
                errorMsg = R.string.empty_contact_message;
        }
        Snackbar.make(editTextFirstName, getString(errorMsg), Snackbar.LENGTH_LONG).show();
    }


}
