import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class PastMeetingImplTest {
    Set<Contact> meetingContacts;
    Meeting testMeeting;
    Contact jim, ben;
    Calendar testDate;

    @Before
    public void setUp() {
        meetingContacts = new HashSet<>();
        jim = new ContactImpl(1, "Jim", "test");
        ben = new ContactImpl(2, "Ben", "test2");
        meetingContacts.add(jim);
        meetingContacts.add(ben);
        testDate = new GregorianCalendar(2014, 3, 11);
        testMeeting = new PastMeetingImpl(2, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullDate() {
        Meeting testMeet = new PastMeetingImpl(2, null, meetingContacts, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullContacts() {
        Meeting testMeet = new PastMeetingImpl(2, testDate, null, "testNotes");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddingNullNotes() {
        Meeting testMeet = new PastMeetingImpl(2, testDate, meetingContacts, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingZeroID() {
        Meeting tesMeet = new PastMeetingImpl(0, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingNegativeID() {
        Meeting tesMeet = new PastMeetingImpl(-3, testDate, meetingContacts, "testNotes");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingEmptyContactSet() {
        Set<Contact> emptySet = new HashSet<>();
        Meeting tesMeet = new PastMeetingImpl(1, testDate, emptySet, "testNotes");
    }

    @Test
    public void testsGetMeetingId() {
        assertEquals(2, testMeeting.getId());
    }

    @Test
    public void testsWrongId() {
        assertFalse(1 == testMeeting.getId());
    }
    /*
    @Test
    public void testGetNotes() throws Exception {

    }
    */
}
