import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting  {

    public FutureMeetingImpl(int id, Calendar date, Set<Contact> attendees) {
        super(id, date, attendees);
    }
}
