import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 22/02/2016.
 */
public abstract class MeetingImpl implements Meeting {
    private int meetingId;
    private Calendar meetingDate;
    private Set<Contact> meetingAttendees;

    public MeetingImpl(int id, Calendar date, Set<Contact> attendees) {
        if (id <= 0) {
            throw new IllegalArgumentException("Meeting ID must be a non zero positive integer.");
        }
        if (date == null || attendees == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        if (attendees.isEmpty()) {
            throw new IllegalArgumentException("Set of contacts must not be empty.");
        }
        meetingId = id;
        meetingDate = date;
        meetingAttendees = attendees;
    }

    /**
     * Returns the id of the meeting.
     *
     * @return the id of the meeting.
     */
    public int getId() {
        return meetingId;
    }

    /**
     * Return the date of the meeting.
     *
     * @return the date of the meeting.
     */
    public Calendar getDate() {
        return meetingDate;
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
        return meetingAttendees;
    }

}
