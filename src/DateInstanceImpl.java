import java.util.Calendar;

/**
 * Created by James Pickles on 02/03/2016.
 */
public class DateInstanceImpl implements DateInstance {
    private static boolean dateChanged;
    private static Calendar fakeDate;

    public Calendar getDateInstance() {
        if (!dateChanged) {
            return Calendar.getInstance();
        } else {
            return fakeDate;
        }
    }

    public void changeDate(Calendar newDate) {
        fakeDate = newDate;
        dateChanged = true;
    }

    public void reset() {
        dateChanged = false;
    }
}