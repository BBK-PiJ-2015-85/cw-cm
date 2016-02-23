import org.junit.Before;
import org.junit.Test;

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
    }

    @Test
    public void testsGetTwoOutofThree() {
        Set<Contact> testSet = cm3.getContacts(1, 3);
        assertEquals(2, cm3.getContacts(1, 3).size());
        assertEquals(2, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3, 1)));
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
    }

    @Test
    public void testsAddOneFromGroupOfThree() {
        Set<Contact> testSet = cm3.getContacts(3);
        assertEquals(1, testSet.size());
        assertTrue(testSet.containsAll(cm3.getContacts(3)));
        assertFalse(testSet.containsAll(cm3.getContacts(1)));
        assertFalse(testSet.containsAll(cm3.getContacts(2)));
        assertFalse(testSet.containsAll(cm3.getContacts(1, 2, 3)));
    }








}