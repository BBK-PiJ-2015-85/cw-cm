import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 22/02/2016.
 */
public abstract class MeetingImpl implements Meeting {

    public MeetingImpl(int meetingId, Calendar date, Set<Contact> attendees) {

    }
    /**
     * Returns the id of the meeting.
     *
     * @return the id of the meeting.
     */
    public int getId() {
        return 0;
    }

    /**
     * Return the date of the meeting.
     *
     * @return the date of the meeting.
     */
    public Calendar getDate() {
        return null;
    }

    /**
     * Return the details of people that attended the meeting.
     *
     * The list contains a minimum of one contact (if there were
     * just two people: the user and the contact) and may contain an
     * arbitrary number of them.
     *
     * @return the details of people that attended the meeting.
     */
    public Set<Contact> getContacts() {
        return null;
    }
}
