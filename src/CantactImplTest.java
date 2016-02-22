import org.junit.Before;
import org.testng.annotations.Test;

import static org.junit.Assert.*;


public class CantactImplTest {
    Contact jim, ben, sam, will, jen, max, dan, jane, kay, lee;

    @Before
    public void setUp() throws Exception {
        jim = new ContactImpl(1, "Jim", "test contact 1");
        ben = new ContactImpl(2, "Ben", "test contact 2");
        sam = new ContactImpl(3, "Sam", "test contact 3");
        will = new ContactImpl(4, "Will", "test contact 4");
        jen = new ContactImpl(5, "Jen", "test contact 5");
        max = new ContactImpl(6, "Max");
        dan = new ContactImpl(7, "Dan");
        jane = new ContactImpl(8, "Jane");
        kay = new ContactImpl(9, "Kay");
        lee = new ContactImpl(10, "Lee");
    }

    @Test
    public void testsGetIDforJim() {
        assertEquals(1, jim.getId());
    }
    @Test
    public void testsGetIDforBen() {
        assertEquals(2, ben.getId());
    }
    @Test
    public void testsGetIDforSam() {
        assertEquals(3, sam.getId());
    }
    @Test
    public void testsGetIDforWill() {
        assertEquals(4, will.getId());
    }
    @Test
    public void testsGetIDforJen() {
        assertEquals(5, jen.getId());
    }
    @Test
    public void testsGetIDforMax() {
        assertEquals(6, max.getId());
    }
    @Test
    public void testsGetIDforDan() {
        assertEquals(7, dan.getId());
    }
    @Test
    public void testsGetIDforJane() {
        assertEquals(8, jane.getId());
    }
    @Test
    public void testsGetIDforKay() {
        assertEquals(9, kay.getId());
    }
    @Test
    public void testsGetIDforLee() {
        assertEquals(10, lee.getId());
    }

}