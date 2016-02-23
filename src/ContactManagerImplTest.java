import org.junit.Before;
import org.junit.Test;

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
}