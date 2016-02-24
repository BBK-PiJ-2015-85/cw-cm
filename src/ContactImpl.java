/**
 * Created by James Pickles on 22/02/2016.
 */
public class ContactImpl implements Contact {
    private int contactId;
    private String contactName;
    private String contactNotes;

    public ContactImpl(int id, String name, String notes) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a non zero positive integer.");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("Contact name must not be empty.");
        }
        if (name == null || notes == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        contactId = id;
        contactName = name;
        contactNotes = notes;
    }

    public ContactImpl(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a non zero positive integer.");
        }
        if (name.equals("")) {
            throw new IllegalArgumentException("Contact name must not be empty.");
        }
        if (name == null) {
            throw new NullPointerException("Null parameters cannot be passed to constructor.");
        }
        contactId = id;
        contactName = name;
        contactNotes = "";
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact.
     */
    public int getId() {
        return contactId;
    }

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact.
     */
    public String getName() {
        return contactName;
    }

    /**
     * Returns our notes about the contact, if any.
     *
     * If we have not written anything about the contact, the empty
     * string is returned.
     *
     * @return a string with notes about the contact, maybe empty.
     */
    public String getNotes() {
        return contactNotes;
    }

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    public void addNotes(String note) {
        if (contactNotes.equals("")) {
            contactNotes = note;
        } else {
            contactNotes += "; " + note;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ContactImpl)) {
            return false;
        }
        ContactImpl that = (ContactImpl) other;
        return this.getId() == that.getId() &&
               this.getName().equals(that.getName()) &&
               this.getNotes().equals(that.getNotes());
    }
}
