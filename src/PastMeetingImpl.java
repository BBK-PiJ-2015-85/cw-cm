import java.util.Calendar;
import java.util.Set;

/**
 * Created by James Pickles on 23/02/2016.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
    private String meetingNotes;

    public PastMeetingImpl(int id, Calendar date, Set<Contact> attendees, String notes) {
        super(id, date, attendees);
        if (notes == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        meetingNotes = notes;
    }

    /**
     * Returns the notes from the meeting.
     *
     * If there are no notes, the empty string is returned.
     *
     * @return the notes from the meeting.
     */
    public String getNotes() {
        return meetingNotes;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PastMeetingImpl && this.hashCode() == other.hashCode();
    }
}
