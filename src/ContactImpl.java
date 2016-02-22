
public class ContactImpl implements Contact {
    private int contactId;
    private String contactName;
    private String contactNotes;

    public ContactImpl(int id, String name, String notes) {
        contactId = id;
        contactName = name;
        contactNotes = notes;
    }

    public ContactImpl(int id, String name) {
        contactId = id;
        contactName = name;
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
        return null;
    }

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    public void addNotes(String note) {
        return;
    }
}
