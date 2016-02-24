import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class ContactManagerImplTest {
    ContactManager cm, cm1, cm2, cm3;
    Set<Contact> testSet1, testSet2, testSet3;
    Contact con1, con2, con3;
    Calendar current, plus1Sec, plus1Min, plus1Hour, plus1Day, plus1Month, plus1Year,
             minus1Min, minus1Sec, minus1Hour, minus1Day, minus1Month, minus1Year;

    @Before
    public void setUp() {

        //create 3 test contacts
        con1 = new ContactImpl(1, "Con1", "test contact 1");
        con2 = new ContactImpl(2, "Con2", "test contact 2");
        con3 = new ContactImpl(3, "Con3", "test contact 3");

        //create 3 test contact sets
        //one test set with one contact
        testSet1 = new HashSet<>();
        testSet1.add(con1);
        //one test set with two contacts
        testSet2 = new HashSet<>();
        testSet2.add(con1);
        testSet2.add(con2);
        //one test set with three contacts
        testSet3 = new HashSet<>();
        testSet3.add(con1);
        testSet3.add(con2);
        testSet3.add(con3);

        //create four test ContactManagers
        //one with no contacts
        cm = new ContactManagerImpl();
        //one with one contact
        cm1 = new ContactManagerImpl();
        cm1.addNewContact("Con1", "test contact 1");
        //one with two contacts
        cm2 = new ContactManagerImpl();
        cm2.addNewContact("Con1", "test contact 1");
        cm2.addNewContact("Con2", "test contact 2");
        //one with three contacts
        cm3 = new ContactManagerImpl();
        cm3.addNewContact("Con1", "test contact 1");
        cm3.addNewContact("Con2", "test contact 2");
        cm3.addNewContact("Con3", "test contact 3");

        //create a time in sync with system time to use during testing
        //so that tests will also work in the future
        current = new GregorianCalendar();
        plus1Sec = new GregorianCalendar();
        minus1Sec = new GregorianCalendar();
        plus1Min = new GregorianCalendar();
        plus1Hour = new GregorianCalendar();
        plus1Day = new GregorianCalendar();
        plus1Month = new GregorianCalendar();
        plus1Year = new GregorianCalendar();
        minus1Min = new GregorianCalendar();
        minus1Hour = new GregorianCalendar();
        minus1Day = new GregorianCalendar();
        minus1Month = new GregorianCalendar();
        minus1Year = new GregorianCalendar();
        plus1Sec.roll(Calendar.SECOND, true);
        plus1Min.roll(Calendar.MINUTE, true);
        plus1Hour.roll(Calendar.HOUR, true);
        plus1Day.roll(Calendar.DAY_OF_WEEK, true);
        plus1Month.roll(Calendar.MONTH, true);
        plus1Year.roll(Calendar.YEAR, true);
        minus1Sec.roll(Calendar.SECOND, false);
        minus1Min.roll(Calendar.MINUTE, false);
        minus1Hour.roll(Calendar.HOUR, false);
        minus1Day.roll(Calendar.DAY_OF_WEEK, false);
        minus1Month.roll(Calendar.MONTH, false);
        minus1Year.roll(Calendar.YEAR, false);
    }

    /**
     * Test addNewContact()
     */

    @Test(expected = NullPointerException.class)
    public void testsAddNullName() {
        cm.addNewContact(null, "test");
    }

    @Test(expected = NullPointerException.class)
    public void testsAddNullNotes() {
        cm.addNewContact("Bob Smith", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddEmptyName() {
        cm.addNewContact("", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsAddEmptyNotes() {
        cm.addNewContact("Bob", "");
    }

    @Test
    public void testsAddOneNewContact() {
        assertEquals(1, cm.addNewContact("Bob", "First Contact"));
    }

    @Test
    public void testsAddTwoNewContacts() {
        cm.addNewContact("Tim", "First Contact");
        assertEquals(2, cm.addNewContact("Bob", "Second Contact"));
    }

    @Test
    public void testsAddThreeNewContacts() {
        cm.addNewContact("Tim", "First Contact");
        cm.addNewContact("Jo", "Second Contact");
        assertEquals(3, cm.addNewContact("Bob", "Third Contact"));
    }

    @Test
    public void testsAddOneHundredContacts() {
        for (int i = 0; i < 99; i++) {
            cm.addNewContact("Contact " + i, "test");
        }
        assertEquals(100, cm.addNewContact("Dan", "test"));
    }

    /**
     * Test getContact(int)
     */

    @Test(expected = IllegalArgumentException.class)
    public void testsNoIdsProvided() {
        cm.addNewContact("Ben", "notes");
        cm.getContacts();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsIdProvidedDoesNotExist() {
        cm.addNewContact("Ben", "notes");
        cm.getContacts(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsTwoValidIdsButOneInvalid() {
        cm.addNewContact("Ben", "notes");
        cm.addNewContact("Tim", "notes1");
        cm.getContacts(1, 2, 3);
    }

    @Test
    public void testsGetOneContact() {
        cm.addNewContact("Ben", "myNotes");
        Set<Contact> testSet = cm.getContacts(1);
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm.getContacts(1)));
    }

    @Test
    public void testsGetOneContactComparesName() {
        cm.addNewContact("Ben", "myNotes");
        Set<Contact> testSet = cm.getContacts(1);
        Object[] ob = testSet.toArray();
        Contact c = (Contact) ob[0];
        assertEquals("Ben", c.getName());
    }

    @Test
    public void testsGetTwo() {
        Set<Contact> testSet = cm2.getContacts(1, 2);
        assertEquals(2, testSet.size());
        assertTrue(testSet.containsAll(cm2.getContacts(1, 2)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2));
        }
    }

    @Test
    public void testsGetTwoOutOfThree() {
        Set<Contact> testSet = cm3.getContacts(1, 3);
        assertEquals(2, cm3.getContacts(1, 3).size());
        assertEquals(2, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3, 1)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con3));
        }
    }

    @Test
    public void testsGetThreeContacts() {
        cm.addNewContact("Ben", "myNotes");
        cm.addNewContact("Tim", "myNotes");
        cm.addNewContact("Ell", "myNotes");
        Set<Contact> testSet = cm.getContacts(1, 2, 3);
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm.getContacts(1, 2, 3)));
    }

    @Test
    public void testsGetThreeContactsDifferentOrder() {
        Set<Contact> testSet = cm3.getContacts(2, 3, 1);
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(1)));
        assertTrue(testSet.containsAll(cm3.getContacts(2)));
        assertTrue(testSet.containsAll(cm3.getContacts(3)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsAddOneFromGroupOfThree() {
        Set<Contact> testSet = cm3.getContacts(3);
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3)));
        assertFalse(testSet.containsAll(cm3.getContacts(1)));
        assertFalse(testSet.containsAll(cm3.getContacts(2)));
        assertFalse(testSet.containsAll(cm3.getContacts(1, 2, 3)));
        for (Contact c : testSet) {
            assertTrue(c.equals(con3));
        }
    }

    /**
     * Test getContact(String)
     */


    @Test(expected = NullPointerException.class)
    public void testsNullAsArgument() {
        String strNull = null;
        cm1.getContacts(strNull);
    }

    @Test
    public void testGetOneContact() {
        Set<Contact> testSet = cm3.getContacts("Con3");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("Con3")));
        for (Contact c : testSet) {
            assertEquals("Con3", c.getName());
            assertEquals(c, con3);
        }
    }

    @Test
    public void testsGetOneContactLowerCase() {
        Set<Contact> testSet = cm2.getContacts("con2");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm2.getContacts("con2")));
        for (Contact c : testSet) {
            assertEquals("Con2", c.getName());
            assertEquals(c, con2);
        }
    }

    @Test
    public void testsGetAllContacts() {
        Set<Contact> testSet = cm3.getContacts("");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("")));
        for (Contact c : testSet) {
            assertTrue(c.getId() == 1 || c.getId() == 2 || c.getId() == 3);
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsPartialName() {
        Set<Contact> testSet = cm3.getContacts("con");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("con")));
        for (Contact c : testSet) {
            assertTrue(c.getNotes().equals("test contact 1") || c.getNotes().equals("test contact 2") || c.getNotes().equals("test contact 3"));
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    @Test
    public void testsFirstLetterOnly() {
        Set<Contact> testSet = cm3.getContacts("c");
        assertEquals(3, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("c")));
        for (Contact c : testSet) {
            assertTrue(c.equals(con1) || c.equals(con2) || c.equals(con3));
        }
    }

    //for now assuming that we return an empty list if no matches are found
    @Test
    public void testsNameNotInContacts() {
        Set<Contact> testSet = cm3.getContacts("fake contact");
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testsGettingContactWithSpaces() {
        cm3.addNewContact("Joe Bloggs", "test");
        Set<Contact> testSet = cm3.getContacts("joe b");
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts("joe b")));
        for (Contact c : testSet) {
            assertEquals("Joe Bloggs", c.getName());
            assertEquals(4, c.getId());
        }
    }

    //assuming that when there are no contacts an empty list is returned
    @Test
    public void testsSearchingForContactButNoContacts() {
        Set<Contact> testSet = cm.getContacts("Mike");
        assertTrue(testSet.isEmpty());
    }

    @Test
    public void testsSearchForAllContactsButNoContacts() {
        Set<Contact> testSet = cm.getContacts("");
        assertTrue(testSet.isEmpty());
    }

    /**
     * Test addFutureMeeting()
     */

    @Test(expected = NullPointerException.class)
    public void testsNullDatePassed() {
        cm1.addFutureMeeting(testSet1,null);
    }

    @Test(expected = NullPointerException.class)
    public void testsNullContactSetPassed() {
        cm1.addFutureMeeting(null, plus1Day);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Second() {
        cm1.addFutureMeeting(testSet1, minus1Sec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Minute() {
        cm1.addFutureMeeting(testSet1, minus1Min);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Hour() {
        cm1.addFutureMeeting(testSet1, minus1Hour);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Day() {
        cm1.addFutureMeeting(testSet1, minus1Day);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Month() {
        cm1.addFutureMeeting(testSet1, minus1Month);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsPastDate1Year() {
        cm1.addFutureMeeting(testSet1, minus1Year);
    }










}