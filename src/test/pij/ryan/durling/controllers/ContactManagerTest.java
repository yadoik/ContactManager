package pij.ryan.durling.controllers;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pij.ryan.durling.models.Contact;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ContactManagerTest {
  Contacts contacts = mock(Contacts.class);
  Meetings meetings = mock(Meetings.class);
  ContactManager cm;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    cm = new ContactManagerImpl(contacts, meetings);
  }

  @Test
  public void shouldBeAbleToAddAContact() {
    cm.addNewContact("Ryan", "notes");
    verify(contacts).add(anyString(), anyString());
  }

  @Test
  public void shouldThrowNullPointerExceptionIfAddNewContactsNameIsNull() {
    thrown.expect(NullPointerException.class);

    cm.addNewContact(null, "notes");
  }

  @Test
  public void shouldThrowNullPointerExceptionIfAddNewContactsNotesAreNull() {
    thrown.expect(NullPointerException.class);

    cm.addNewContact("Ryan", null);
  }

  @Test
  public void shouldBeAbleToGetAContactByName() {
    cm.getContacts("Ryan");
    verify(contacts).get(anyString());
  }

  @Test
  public void shouldThrowNullPointerExceptionIfGetContactsNameIsNull() {
    thrown.expect(NullPointerException.class);

    String name = null;
    cm.getContacts(name);
  }

  @Test
  public void shouldBeAbleToGetAContactById() {
    cm.getContacts(0, 1, 4);
    verify(contacts, times(3)).get(anyInt());
  }

  @Test
  public void shouldCheckIfIdsExistInTheContacts() {
    cm.getContacts(0, 1, 4);
    verify(contacts).notValidContactId((int[]) anyVararg());
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionIfAnyOfTheIDsDoNotCorrespondToARealContact() {
    thrown.expect(IllegalArgumentException.class);

    when(contacts.notValidContactId((int[]) anyVararg())).thenReturn(true);
    cm.getContacts(10);
  }

  @Test
  public void shouldBeAbleToAddAFutureMeeting() {
    Set<Contact> contactSet = new HashSet<>();
    contactSet.add(mock(Contact.class));
    Calendar date = Calendar.getInstance();
    cm.addFutureMeeting(contactSet, date);

    verify(meetings).addFutureMeeting(contactSet, date);
  }

  @Test
  public void shouldThrowAnIllegalArgumentExceptionIfAContactDoesNotExist() {
    thrown.expect(IllegalArgumentException.class);

    Set<Contact> contactSet = new HashSet<>();
    contactSet.add(mock(Contact.class));
    Calendar date = Calendar.getInstance();
    when(contacts.notValidContactSet(contactSet)).thenReturn(true);
    cm.addFutureMeeting(contactSet, date);
  }

  @Test
  public void shouldThrowAnIllegalArgumentExceptionIfATheDateIsInThePast() {
    thrown.expect(IllegalArgumentException.class);

    Set<Contact> contactSet = new HashSet<>();
    contactSet.add(mock(Contact.class));
    Calendar date = Calendar.getInstance();
    date.add(Calendar.DATE, -1);
    when(contacts.notValidContactSet(contactSet)).thenReturn(false);
    cm.addFutureMeeting(contactSet, date);
  }
}