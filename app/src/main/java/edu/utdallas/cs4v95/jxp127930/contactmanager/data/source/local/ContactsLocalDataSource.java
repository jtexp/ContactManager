package edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.local;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.Contact;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsDataSource;

/**
 * Created by john on 2/16/17.
 * todo: refactor this mess!!!
 */

public class ContactsLocalDataSource implements ContactsDataSource {

    final private static String TAG = "CONTACTSLOCALDATASOURCE";

    private static ContactsLocalDataSource INSTANCE;

    final private String FILENAME = "storage.tsv";

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private Context context;

    private ContactsLocalDataSource(Context context) {
        this.context = context.getApplicationContext();

    }

    public static ContactsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ContactsLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getContacts(LoadContactsCallback callback) {

        List<Contact> contacts = new ArrayList<>();
        Contact contact;

        String[] out;

        lock.readLock().lock();
        try {
            FileInputStream is = context.openFileInput(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            CSVReader reader = new CSVReader(br, '\t');
            while((out = reader.readNext()) != null) {
                for(int i = 0; i < out.length; i++) {
                    Log.d(TAG, "CSV Read " + out[i]);
                }
                try {
                    contact = new Contact(UUID.fromString(out[0]), out[1], out[2], out[3], out[4], out[5], out[6]);

                    contacts.add(contact);
                } catch(Exception ex) {

                }
            }
            reader.close();
            br.close();
            is.close();



        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            lock.readLock().unlock();
        }

        if (contacts.isEmpty()) {
            callback.onDataNotAvailable();
        }
        else {
            Log.d(TAG, "Contacts: " + contacts.size());
            callback.onContactsLoaded(contacts);
        }

    }

    @Override
    public void getContact(final UUID contactId, final GetContactCallback callback) {
        this.getContacts(new LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getId() == contactId) {
                        callback.onContactLoaded(contacts.get(i));
                        break;
                    }
                }
                callback.onDataNotAvailable();

            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();

            }
        });

    }

    @Override
    public void deleteContact(final UUID contactId) {
        this.getContacts(new LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getId().equals(contactId)) {
                        contacts.remove(i);
                        break;
                    }
                }


                lock.writeLock().lock();
                try {
                    Log.d(TAG, "writing" );
                    FileOutputStream os = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write("");
                    bw.close();
                    os.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }

                for (Contact contact2: contacts) {

                    String[] out = {contact2.getId().toString(), contact2.getFirstName(),
                            contact2.getMiddleInitial(), contact2.getLastName(),
                            contact2.getPhoneNumber(), contact2.getBirthDate(),
                            contact2.getDateFirstContact()};


                    lock.writeLock().lock();
                    try {
                        Log.d(TAG, "writing" );
                        FileOutputStream os = context.openFileOutput(FILENAME, Context.MODE_APPEND);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                        CSVWriter writer = new CSVWriter(bw, '\t');
                        writer.writeNext(out);
                        writer.close();
                        bw.close();
                        os.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }
                }




            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void saveContact(final Contact contact) {


        this.getContacts(new LoadContactsCallback() {
            @Override
            public void onContactsLoaded(List<Contact> contacts) {

                Log.d(TAG, "loaded");

                List<Contact> newList = new ArrayList<>();
                for (int i = 0; i < contacts.size(); i++) {
                    Log.d(TAG, contacts.get(i).getId().toString() + " ---- " +  contact.getId().toString());
                    if (!contacts.get(i).getId().equals(contact.getId())) {
                        newList.add(contacts.get(i));

                    }
                }
                newList.add(contact);


                lock.writeLock().lock();
                try {
                    Log.d(TAG, "writing" );
                    FileOutputStream os = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write("");
                    bw.close();
                    os.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }

                for (Contact contact2: newList) {
                     String[] out = {contact2.getId().toString(), contact2.getFirstName(),
                            contact2.getMiddleInitial(), contact2.getLastName(),
                            contact2.getPhoneNumber(), contact2.getBirthDate(),
                            contact2.getDateFirstContact()};


                    lock.writeLock().lock();
                    try {
                        Log.d(TAG, "writing" );
                        FileOutputStream os = context.openFileOutput(FILENAME, Context.MODE_APPEND);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                        CSVWriter writer = new CSVWriter(bw, '\t');
                        writer.writeNext(out);
                        writer.close();
                        bw.close();
                        os.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }
                }
            }


            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "empty, data not loaded");
                String[] out = {contact.getId().toString(), contact.getFirstName(),
                        contact.getMiddleInitial(), contact.getLastName(),
                        contact.getPhoneNumber(), contact.getBirthDate(),
                        contact.getDateFirstContact()};

                lock.writeLock().lock();
                try {
                    FileOutputStream os = context.openFileOutput(FILENAME, Context.MODE_APPEND);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    CSVWriter writer = new CSVWriter(bw, '\t');
                    writer.writeNext(out);
                    writer.close();
                    bw.close();
                    os.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    lock.writeLock().unlock();
                }

            }
        });

    }




    @Override
    public void deleteAllContacts() {

    }



}
