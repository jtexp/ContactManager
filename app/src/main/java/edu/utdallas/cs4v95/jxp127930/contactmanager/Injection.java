package edu.utdallas.cs4v95.jxp127930.contactmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.ContactsRepository;
import edu.utdallas.cs4v95.jxp127930.contactmanager.data.source.local.ContactsLocalDataSource;

/**
 * Created by john on 2/15/17.
 */


public class Injection {

    public static ContactsRepository provideContactsRepository(@NonNull Context context) {
        return ContactsRepository.getInstance(ContactsLocalDataSource.getInstance(context.getApplicationContext()));
    }
}