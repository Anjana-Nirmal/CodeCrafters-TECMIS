package tecmis.src;

public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String contact;
    protected String role;

    public User(String userId, String name, String email, String contact, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.role = role;
    }

    public abstract void viewProfile();

    public void updateContact(String newContact) {
        this.contact = newContact;
    }

    public String getUserId() {
        return userId;
    }
}

