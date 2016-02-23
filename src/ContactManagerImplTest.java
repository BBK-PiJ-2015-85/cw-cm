import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class ContactManagerImplTest {
    ContactManager cm;

    @Before
    public void setUp() {
        cm = new ContactManagerImpl();
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
        cm.addNewContact("Ben", "noetes");
        cm.getContacts(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testsTwoValidIdsButOneInvalid() {
        cm.addNewContact("Ben", "noetes");
        cm.addNewContact("Tim", "noetes1");
        cm.getContacts(1, 2, 3);
    }

    @Test
    public void testsGetOneContact() {
        cm.addNewContact("Ben", "myNotes");
        Set<Contact> testSet = new HashSet<>();
        testSet.add(new ContactImpl(1, "Ben", "myNotes"));
        assertEquals(testSet, cm.getContacts(1));
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
        cm.addNewContact("Ben", "myNotes");
        cm.addNewContact("Tim", "myNotes");
        Set<Contact> testSet = new HashSet<>();
        testSet.add(new ContactImpl(1, "Ben", "myNotes"));
        testSet.add(new ContactImpl(2, "Tim", "myNotes"));
        assertEquals(2, cm.getContacts(1, 2).size());
        assertEquals(testSet, cm.getContacts(1, 2));
    }

    @Test
    public void testsGetTwoOutofThree() {
        cm.addNewContact("Ben", "myNotes");
        cm.addNewContact("Tim", "myNotes");
        cm.addNewContact("Ell", "myNotes");
        Set<Contact> testSet = new HashSet<>();
        testSet.add(new ContactImpl(1, "Ben", "myNotes"));
        testSet.add(new ContactImpl(3, "Ell", "myNotes"));
        assertEquals(2, cm.getContacts(1, 3).size());
        assertEquals(testSet, cm.getContacts(1, 3));
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







}