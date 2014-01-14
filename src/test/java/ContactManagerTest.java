import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

import org.junit.*;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ContactManagerTest {
    private IdGenerator mockIdGenerator;
    private ContactManager cm;
    private Set<Contact> mockContacts;
    private Contact mockContact;
    private Meeting mockMeeting;
    private FutureMeeting mockFutureMeeting;
    private PastMeeting mockPastMeeting;
    private Calendar mockDate = mockDate(1);
    private int knownId;
    private String knownName;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() throws InvalidMeetingException {
        mockContacts = new HashSet<Contact>();
        mockContact = mock(Contact.class);
        mockContacts.add(mockContact);
        mockDate = mockDate(1);

        mockMeeting = mock(Meeting.class);
        mockFutureMeeting = mock(FutureMeeting.class);
        mockPastMeeting = mock(PastMeeting.class);
        mockIdGenerator = mock(IdGenerator.class);
        cm = new ContactManagerImpl(mockMeetingFactory(), mockContactFactory(), mockIdGenerator);
	knownId = 4;
	knownName = "Ryan";
	addContact();
    }

    @Test
    public void shouldBeAbleToAddaMeetingForTheFuture() {
	when(mockFutureMeeting.getDate()).thenReturn(mockDate);
        int id = cm.addFutureMeeting(mockContacts, mockDate);
        Meeting meeting = cm.getFutureMeeting(id);

        assertThat(meeting, is(instanceOf(FutureMeeting.class)));
        assertThat(mockFutureMeeting, is(equalTo(meeting)));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfUserTryToAddPastMeetingToFutureDate() {
        thrown.expect(IllegalArgumentException.class);

        cm.addFutureMeeting(mockContacts, mockDate(-1));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfAContactIsUnknown() {
        thrown.expect(IllegalArgumentException.class);

        int unknownId = 42;
        Contact unknownContact = mock(Contact.class);
        mockContacts.add(unknownContact);
        when(unknownContact.getId()).thenReturn(unknownId);
        cm.addFutureMeeting(mockContacts, mockDate(1));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfUserTyToGetPastMeetingFromFutureMeeting() {
        thrown.expect(IllegalArgumentException.class);

        when(mockFutureMeeting.getDate()).thenReturn(mockDate(-1));
        int id = cm.addFutureMeeting(mockContacts, mockDate);
        cm.getFutureMeeting(id);
    }

    @Test
    public void shouldBeAbleToAddANewContact() {
        Contact contact = cm.getContacts(knownName).toArray(new Contact[0])[0];

        assertThat(contact, is(instanceOf(Contact.class)));
        assertThat(mockContact, is(equalTo(contact)));
    }

    @Test
    public void shouldThrowNullPointerExceptionIfNoNameIsGivenToAdd() {
        thrown.expect(NullPointerException.class);

        String name = null;
        String note = "note";
        cm.addNewContact(name, note);
    }

    @Test
    public void shouldThrowNullPointerExceptionIfNoNotesAreGivenToAdd() {
        thrown.expect(NullPointerException.class);

        String name = "Ryan";
        String note = null;
        cm.addNewContact(name, note);
    }

    @Test
    public void shouldThrowNullPointerExceptionIfNoNameIsGivenToGet() {
        thrown.expect(NullPointerException.class);

        String name = null;
        cm.getContacts(name);
    }

    @Test
    public void shouldBeAbleToGetGetContactsById() {
        Contact contact = cm.getContacts(knownId).toArray(new Contact[0])[0];

        assertThat(contact, is(instanceOf(Contact.class)));
        assertThat(mockContact, is(equalTo(contact)));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfAnyOfTheIdsDoNotCorrespondToARealContact() {
        thrown.expect(IllegalArgumentException.class);

        int unknownId = 50;
        cm.getContacts(knownId, unknownId);
    }

    private void addContact() {
        String note = "note";
	when(mockContact.getName()).thenReturn(knownName);
        when(mockContact.getId()).thenReturn(knownId);
        cm.addNewContact(knownName, note);
    }

    private Calendar mockDate(int days) {
        Calendar date = new GregorianCalendar();
        date.add(Calendar.DATE, days);
        return date;
    }

    private MeetingFactory mockMeetingFactory() throws InvalidMeetingException {
        MeetingFactory mockMeetingFactory = mock(MeetingFactory.class);
        when(mockMeetingFactory.createMeeting(anyInt(), eq(mockContacts), eq(mockDate))).thenReturn(mockMeeting);
        when(mockMeetingFactory.createFutureMeeting(anyInt(), eq(mockContacts), eq(mockDate))).thenReturn(mockFutureMeeting);
        when(mockMeetingFactory.createPastMeeting(eq(mockMeeting), anyString())).thenReturn(mockPastMeeting);
        return mockMeetingFactory;
    }

    private ContactFactory mockContactFactory() {
        ContactFactory mockContactFactory = mock(ContactFactory.class);
        when(mockContactFactory.createContact(anyInt(), anyString())).thenReturn(mockContact);
        return mockContactFactory;
    }
}
