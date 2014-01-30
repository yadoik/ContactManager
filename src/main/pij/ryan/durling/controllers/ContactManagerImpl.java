package pij.ryan.durling.controllers;

import pij.ryan.durling.models.Contact;
import pij.ryan.durling.models.FutureMeeting;
import pij.ryan.durling.models.Meeting;
import pij.ryan.durling.models.PastMeeting;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
  Contacts contacts;

  public ContactManagerImpl(Contacts contacts) {
      this.contacts = contacts;
  }

  @Override
  public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
    return 0;  //TODO
  }

  @Override
  public PastMeeting getPastMeeting(int id) {
    return null;  //TODO
  }

  @Override
  public FutureMeeting getFutureMeeting(int id) {
    return null;  //TODO
  }

  @Override
  public Meeting getMeeting(int id) {
    return null;  //TODO
  }

  @Override
  public List<Meeting> getFutureMeetingList(Contact contact) {
    return null;  //TODO
  }

  @Override
  public List<Meeting> getFutureMeetingList(Calendar date) {
    return null;  //TODO
  }

  @Override
  public List<PastMeeting> getPastMeetingList(Contact contact) {
    return null;  //TODO
  }

  @Override
  public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
    //TODO
  }

  @Override
  public void addMeetingNotes(int id, String text) {
    //TODO
  }

  @Override
  public void addNewContact(String name, String notes) throws NullPointerException {
    if (name == null || notes == null) throw new NullPointerException();
    contacts.add(name, notes);
  }

  @Override
  public Set<Contact> getContacts(int... ids) throws IllegalArgumentException {
    if (contacts.notValidContactId(ids)) throw new IllegalArgumentException();
    Set<Contact> contactSet = new HashSet<>();
    for (int id : ids) {
      contactSet.add(contacts.get(id));
    }
    return contactSet;
  }

  @Override
  public Set<Contact> getContacts(String name) {
    if (name == null) throw new NullPointerException();
    return contacts.get(name);
  }

  @Override
  public void flush() {
    //TODO
  }
}
