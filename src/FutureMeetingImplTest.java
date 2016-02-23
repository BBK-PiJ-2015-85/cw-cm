import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by James Pickles on 22/02/2016.
 */
public class FutureMeetingImplTest {
    Set<Contact> meetingContacts;
    Meeting testMeeting;
    Contact jim, ben;


    @Before
    public void setUp() {
        jim = new ContactImpl(1, "Jim", "test");
        ben = new ContactImpl(2, "Ben", "test2");
        meetingContacts = new HashSet<>();
        meetingContacts.add(jim);
        meetingContacts.add(ben);
        Calendar testDate = new GregorianCalendar(2015, 02, 10);
        testMeeting = new FutureMeetingImpl(5, testDate, meetingContacts);
    }

    @Test(expected = NullPointerException.class)
    public void testsNullParameter() {
        Meeting meeting1 = new FutureMeetingImpl(3, null, meetingContacts);
    }
    @Test(expected = NullPointerException.class)
    public void testsDifferentNullParameter() {
        Calendar date = new GregorianCalendar(2015, 01, 22);
        Meeting meeting1 = new FutureMeetingImpl(3, date, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddingZeroAsId() {
        Calendar date = new GregorianCalendar(2015, 05, 02);
        Meeting meeting1 = new FutureMeetingImpl(0, date, meetingContacts);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testsAddingNegativeAsId() {
        Calendar date = new GregorianCalendar(2015, 04, 20);
        Meeting meeting2 = new FutureMeetingImpl(-5, date, meetingContacts);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testsAddingEmptySet() {
        Calendar date = new GregorianCalendar(2016, 12, 11);
        Set<Contact> emptyTest = new HashSet<>();
        Meeting meeting3 = new FutureMeetingImpl(4, date, emptyTest);
    }

    /*
    @Test
    public void testGetDate() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }
    */
}