import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by James Pickles on 23/02/2016.
 */

public class ContactManagerImpl implements ContactManager {
    private int contactIdCount;
    private int meetingIdCount;
    private Set<Contact> myContacts;
    private Set<FutureMeeting> futureMeetings;
    private Set<PastMeeting> pastMeetings;
    private static final int START = 0;
    private static final int NEXT = 1;
    private DateInstance currentDate;
    private static final String fileName = "contacts.txt";


    @SuppressWarnings("unchecked")

    public ContactManagerImpl() {
        File savedData = new File(fileName);
        if (savedData.exists() && savedData.length() > 0) {
            try (ObjectInputStream
                         decode = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(fileName)))){
                myContacts = (Set<Contact>) decode.readObject();
                futureMeetings = (Set<FutureMeeting>) decode.readObject();
                pastMeetings = (Set<PastMeeting>) decode.readObject();
                contactIdCount = myContacts.size();
                meetingIdCount = futureMeetings.size() + pastMeetings.size();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            contactIdCount = 0;
            meetingIdCount = 0;
            myContacts = new HashSet<>();
            futureMeetings = new HashSet<>();
            pastMeetings = new HashSet<>();
        }
        currentDate = new DateInstanceImpl();
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (contacts == null || date == null) {
            throw new NullPointerException("Contacts and date must not be null.");
        }
        if (!date.after(currentDate.getDateInstance())) {
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
        updateMeetings();
        for (PastMeeting m : pastMeetings) {
            if (id == m.getId()) {
                return m;
            }
        }
        if (id > 0 && id <= meetingIdCount) {
            throw new IllegalStateException("ID provided must be for a past meeting.");
        }
        return null;
    }


    public FutureMeeting getFutureMeeting(int id) {
        updateMeetings();
        for (FutureMeeting m : futureMeetings) {
            if (id == m.getId()) {
                return m;
            }
        }
        if (id > 0 && id <= meetingIdCount) {
            throw new IllegalArgumentException("ID provided must be for a future meeting.");
        }
        return null;
    }


    public Meeting getMeeting(int id) {
        Optional<FutureMeeting> fmStream = futureMeetings.stream().filter((s) -> s.getId() == id).findFirst();
        if (fmStream.isPresent()) {
            return fmStream.get();
        }
        Optional<PastMeeting> pmStream = pastMeetings.stream().filter((s) -> s.getId() == id).findFirst();
        return (pmStream.isPresent()) ? pmStream.get() : null;
    }


    public List<Meeting> getFutureMeetingList(Contact contact) {
        updateMeetings();
        if (contact == null) {
            throw new NullPointerException("Contact must not be null.");
        }
        if (!myContacts.contains(contact)) {
            throw new IllegalArgumentException("Unknown contact cannot used.");
        }
        List<Meeting> resultList = futureMeetings.stream().filter((s) -> s.getContacts().contains(contact))
                                                          .collect(Collectors.toList());
        sortMeetingList(resultList);
        return resultList;
    }


    public List<Meeting> getMeetingListOn(Calendar date) {
        if (date == null) {
            throw new NullPointerException("Date must not be null.");
        }

        //need to search through both past and future lists for occasions when date is today and
        //so may contain both.
        List<Meeting> fmResultList = futureMeetings.stream()
                            .filter((s) -> s.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)
                                        && s.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
                            .collect(Collectors.toList());
        List<Meeting> resultList = pastMeetings.stream()
                            .filter((s) -> s.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR)
                                        && s.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
                            .collect(Collectors.toList());
        resultList.addAll(fmResultList);
        sortMeetingList(resultList);
        return resultList;
    }


    public List<PastMeeting> getPastMeetingListFor(Contact contact) {
        updateMeetings();
        if (contact == null) {
            throw new NullPointerException("Contact must not be null.");
        }
        if (!myContacts.contains(contact)) {
            throw new IllegalArgumentException("Unknown contact cannot be used.");
        }
        List<PastMeeting> resultList = pastMeetings.stream().filter((s) -> s.getContacts().contains(contact))
                                                            .collect(Collectors.toList());
        sortMeetingList(resultList);
        return resultList;
    }


    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (contacts == null || date == null || text == null) {
            throw new NullPointerException("Null parameters cannot be passed as arguments.");
        }
        if (contacts.isEmpty()) {
            throw new IllegalArgumentException("Contact set must not be empty.");
        }
        if (!date.before(currentDate.getDateInstance())) {
            throw new IllegalArgumentException("Date must be in the past.");
        }
        if (!myContacts.containsAll(contacts)) {
            throw new IllegalArgumentException("Unknown contacts cannot be added to meetings.");
        }
        meetingIdCount++;
        pastMeetings.add(new PastMeetingImpl(meetingIdCount, date, contacts, text));
    }


    public PastMeeting addMeetingNotes(int id, String text) {
        updateMeetings();
        if (id <= 0 || id > meetingIdCount) {
            throw new IllegalArgumentException("Meeting ID does not exist.");
        }
        if (text == null) {
            throw new NullPointerException("Notes must not be null.");
        }
        if (!getMeeting(id).getDate().before(currentDate.getDateInstance())) {
            throw new IllegalStateException("Cannot add notes to meetings that are yet to occur.");
        }
        //create new past meeting with same details but updated notes
        PastMeeting updatedPM = new PastMeetingImpl(id, getPastMeeting(id).getDate(), getPastMeeting(id).getContacts(), text);

        //find and remove the now outdated original
        Optional<PastMeeting> oldPM = pastMeetings.stream().filter((s) -> s.getId() == id).findFirst();
        if (oldPM.isPresent()) {
            pastMeetings.remove(oldPM.get());
        }

        //add the updated version and return it
        pastMeetings.add(updatedPM);
        return updatedPM;
    }


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
        return myContacts.stream().filter((s) -> s.getName().toLowerCase()
                                  .contains(name.toLowerCase()))
                                  .collect(Collectors.toSet());
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
            myContacts.stream().filter((s) -> s.getId() == id).anyMatch(matchingIds::add);
        }
        return matchingIds;
    }


    public void flush() {
        try (ObjectOutputStream
                     encode = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(fileName)))) {
            encode.writeObject(myContacts);
            encode.writeObject(futureMeetings);
            encode.writeObject(pastMeetings);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Helper method to remove duplicate meetings and sort
     * the list into chronological order.
     *
     * It is assumed that a meeting can be of the same hour but
     * different minute (e.g. to allow for half hour meetings)
     * and also that it is possible to "double book" i.e. you
     * can have two meetings at the same time but with different
     * set of contacts.
     *
     * @param list the list that needs to be sorted.
     * @return the sorted list of meetings.
     */
    private List<? extends Meeting> sortMeetingList(List<? extends Meeting> list) {

        //first sort list into chronological order using lambda
        list.sort((x, y) -> x.getDate().compareTo(y.getDate()));

        //now iterate through list and remove any duplicate meetings
        for(int i = START; i < list.size() - NEXT; i++) {
            for (int j = i + NEXT; j < list.size(); j++) {
                if (list.get(i).getDate().get(Calendar.YEAR) == list.get(j).getDate().get(Calendar.YEAR)
                        && list.get(i).getDate().get(Calendar.DAY_OF_YEAR) == list.get(j).getDate().get(Calendar.DAY_OF_YEAR)
                        && list.get(i).getDate().get(Calendar.HOUR_OF_DAY) == list.get(j).getDate().get(Calendar.HOUR_OF_DAY)
                        && list.get(i).getDate().get(Calendar.MINUTE) == list.get(j).getDate().get(Calendar.MINUTE)
                        && list.get(i).getContacts().equals(list.get(j).getContacts())) {
                    list.remove(j);
                    j--;
                }
            }
        }
        return list;
    }

    /**
     * This is a method to update any future meetings that have now happened
     * and turns them into past meetings (and updates both meeting sets accordingly).
     *
     * It will be called before the execution of methods that specifically retrieve
     * either a past meeting or a future meeting, or a list of either type. This will make sure that when
     * requesting date specific meeting information the results are accurate.
     */
    private void updateMeetings() {

        //create a list of all Future Meetings that have now happened
        List<FutureMeeting> occurred = futureMeetings.stream()
                                                     .filter((s) -> s.getDate().before(currentDate.getDateInstance()))
                                                     .collect(Collectors.toList());

        //use the list to change each instance from a future to a past meeting
        occurred.stream().forEach((s) -> {
            pastMeetings.add(new PastMeetingImpl(s.getId(), s.getDate(), s.getContacts(), ""));
            futureMeetings.remove(s);
        });
    }
}
