package edu.utdallas.cs4v95.jxp127930.contactmanager.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.utdallas.cs4v95.jxp127930.contactmanager.R;
import edu.utdallas.cs4v95.jxp127930.contactmanager.contactdetail.ContactDetailActivity;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;

/**
 * Created by john on 2/15/17.
 *
 * The fragment implements the View which displays the data from the presenter.
 * When a user clicks on a listenener, it calls into the presenter.
 *
 */

public class ContactsFragment extends Fragment implements ContactsContract.View{
    static private final String TAG = "ContactsFragment";
    private RecyclerView summaryRecyclerView;
    private ContactAdapter adapter;

    private ContactsContract.Presenter presenter;


    public ContactsFragment() {

    }

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
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
    public void setPresenter(@NonNull ContactsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:
                presenter.addNewContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        summaryRecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler_view);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        Log.d(TAG, "Size of contacts " + contacts.size());

        if (adapter == null) {


            adapter = new ContactAdapter(contacts);
            summaryRecyclerView.setAdapter(adapter);
        }
        else {
            adapter.setContacts(contacts);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showContactDetails(String id) {
        Intent intent = new Intent(getActivity(), ContactDetailActivity.class);
        intent.putExtra(ContactDetailActivity.EXTRA_CONTACT_ID, id);
        startActivity(intent);
    }



    private class ContactHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView firstNameTextView;

        private Contact contact;

        public ContactHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            firstNameTextView = (TextView) itemView.findViewById(R.id.list_item_contact_first_name);
        }

        public void bindContact(Contact contact) {
            this.contact = contact;
            firstNameTextView.setText(contact.getFirstName() + " " + contact.getLastName());

        }

        @Override
        public void onClick(View v) {
            presenter.openContactDetails(contact);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

        private List<Contact> contacts;

        public ContactAdapter(List<Contact> contacts) {
            this.contacts = contacts;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_contact, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = contacts.get(position);
            holder.bindContact(contact);
        }

        @Override
        public int getItemCount() {
            return this.contacts.size();
        }

        public void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
        }
    }
}
