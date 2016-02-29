import java.util.*;

/**
 * Created by James Pickles on 23/02/2016.
 */

public class ContactManagerImpl implements ContactManager {
    private int contactIdCount;
    private int meetingIdCount;
    private Set<Contact> myContacts;
    private Set<FutureMeeting> futureMeetings;
    private Set<PastMeeting> pastMeetings;

    public ContactManagerImpl() {
        contactIdCount = 0;
        meetingIdCount = 0;
        myContacts = new HashSet<>();
        futureMeetings = new HashSet<>();
        pastMeetings = new HashSet<>();
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (contacts == null || date == null) {
            throw new NullPointerException("Contacts and date must not be null.");
        }
        Calendar currentDate = new GregorianCalendar();
        if (!date.after(currentDate)) {
            throw new IllegalArgumentException("Date must be in the future.");
        }
        if (!myContacts.containsAll(contacts)) {
            throw new IllegalArgumentException("Unknown contacts cannot be added to meetings.");
        }
        meetingIdCount++;
        futureMeetings.add(new FutureMeetingImpl(meetingIdCount, date, contacts));
        return meetingIdCount;
    }


    public PastMeeting getPastMeeting(int id) {
        for (PastMeeting m : pastMeetings) {
            if (id == m.getId()) {
                return m;
            }
        }
        for (FutureMeeting m : futureMeetings) {
            if (id == m.getId()) {
                throw new IllegalStateException("ID provided must be for a past meeting.");
            }
        }
        return null;
    }

    /**
     * Returns the FUTURE meeting with the requested ID, or null if there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening
     * in the past
     */
    public FutureMeeting getFutureMeeting(int id) {
        for (FutureMeeting m : futureMeetings) {
            if (id == m.getId()) {
                return m;
            }
        }
        for (PastMeeting m : pastMeetings) {
            if (id == m.getId()) {
                throw new IllegalArgumentException("ID provided must be for a future meeting.");
            }
        }
        return null;
    }

    /**
     * Returns the meeting with the requested ID, or null if it there is none.
     *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     */
    public Meeting getMeeting(int id) {return null;}

    /**
     * Returns the list of future meetings scheduled with this contact.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the users contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     * @throws NullPointerException if the contact is null
     */
    public List<Meeting> getFutureMeetingList(Contact contact) {return null;}

    /**
     * Returns the list of meetings that are scheduled for, or that took
     * place on, the specified date
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param date the date
     * @return the list of meetings
     * @throws NullPointerException if the date are null
     */
    public List<Meeting> getMeetingListOn(Calendar date) {return null;}

    /**
     * Returns the list of past meetings in which this contact has participated.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the users contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     * @throws NullPointerException if the contact is null
     */
    public List<PastMeeting> getPastMeetingListFor(Contact contact) {return null;}


    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (contacts == null || date == null || text == null) {
            throw new NullPointerException("Null parameters cannot be passed as arguments.");
        }
        if (contacts.isEmpty()) {
            throw new IllegalArgumentException("Contact set must not be empty.");
        }
        Calendar currentDate = new GregorianCalendar();
        if (!date.before(currentDate)) {
            throw new IllegalArgumentException("Date must be in the past.");
        }
        if (!myContacts.containsAll(contacts)) {
            throw new IllegalArgumentException("Unknown contacts cannot be added to meetings.");
        }
        meetingIdCount++;
        pastMeetings.add(new PastMeetingImpl(meetingIdCount, date, contacts, text));
    }

    /**
     * Add notes to a meeting.
     *
     * This method is used when a future meeting takes place, and is
     * then converted to a past meeting (with notes) and returned.
     *
     * It can be also used to add notes to a past meeting at a later date.
     *
     * @param id the ID of the meeting
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the meeting does not exist
     * @throws IllegalStateException if the meeting is set for a date in the future
     * @throws NullPointerException if the notes are null
     */
    public PastMeeting addMeetingNotes(int id, String text) {return null;}


    public int addNewContact(String name, String notes) {
        if (name == null || notes == null) {
            throw new NullPointerException("Arguments must not be null.");
        }
        if (name.equals("") || notes.equals("")) {
            throw new IllegalArgumentException("Name and notes must not be empty strings.");
        }
        contactIdCount++;
        myContacts.add(new ContactImpl(contactIdCount, name, notes));
        return contactIdCount;
    }


    public Set<Contact> getContacts(String name) {
        if (name == null) {
            throw new NullPointerException("String name cannot be null.");
        }
        Set<Contact> matchingNames = new HashSet<>();
        for (Contact c : myContacts) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                matchingNames.add(c);
            }
        }
        return matchingNames;
    }


    public Set<Contact> getContacts(int... ids) {
        if (ids.length == 0) {
            throw new IllegalArgumentException("At least one ID must be provided.");
        }
        Set<Contact> matchingIds = new HashSet<>();
        for (int id : ids) {
            if (id <= 0 || id > contactIdCount) {
                throw new IllegalArgumentException("ID " + id + " does not correspond to any contact.");
            }
            for (Contact c : myContacts) {
                if (id == c.getId()) {
                    matchingIds.add(c);
                }
            }
        }
        return matchingIds;
    }

    /**
     * Save all data to disk.
     *
     * This method must be executed when the program is
     * closed and when/if the user requests it.
     */
    public void flush() {}
}
