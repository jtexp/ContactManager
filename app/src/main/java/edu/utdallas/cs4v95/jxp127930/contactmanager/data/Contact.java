package edu.utdallas.cs4v95.jxp127930.contactmanager.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.UUID;

/**
 * Created by john on 2/15/17.
 */

public class Contact implements Comparable<Contact> {

    private UUID uuid;
    private String firstName = "";
    private String lastName = "";
    private String middleInitial = "";
    private String phoneNumber = "";
    private String birthDate = "";
    private String dateFirstContact = "";

    public Contact(UUID uuid, String firstName, String middleInitial, String lastName, String phoneNumber, String birthDate, String dateFirstContact) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.phoneNumber = storePhoneNumber(phoneNumber);
        this.birthDate = birthDate;
        this.dateFirstContact = dateFirstContact;
    }

    public Contact(String firstName, String middleInitial, String lastName, String phoneNumber, String birthDate, String dateFirstContact) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.phoneNumber = storePhoneNumber(phoneNumber);
        this.birthDate = birthDate;
        this.dateFirstContact = dateFirstContact;
    }


    public Contact(UUID id) {
        this.uuid = id;
    }

    public Contact() {
        uuid = UUID.randomUUID();
        dateFirstContact = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance().getTime());
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = storePhoneNumber(phoneNumber);
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDateFirstContact() {
        return dateFirstContact;
    }

    public void setDateFirstContact(String dateFirstContact) {
        this.dateFirstContact = dateFirstContact;
    }

    @Override
    public int compareTo(Contact other) {
        return lastName.toUpperCase().compareTo(other.getLastName().toUpperCase());
    }

    private String storePhoneNumber(String phoneNumber) {
        String rawNumbers = phoneNumber.replaceAll("[^0-9]", "");
        if (rawNumbers.length() == 10) {
            return rawNumbers.substring(0, 3) + "-" + rawNumbers.substring(3, 6) + "-" + rawNumbers.substring(6,10);
        }
        else {
            throw new IllegalArgumentException("Wrong Phone Format: " + phoneNumber);
        }

    }

}
