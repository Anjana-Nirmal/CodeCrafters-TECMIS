package tecmis.src;

public class Undergraduate extends User {

    private String department;

    public Undergraduate(String userId, String name, String email, String contact, String role, String department) {
        super(userId, name, email, contact, role);
        this.department = department;
    }

    @Override
    public void viewProfile() {
        System.out.println("Undergraduate: " + name + ", Email: " + email + ", Contact: " + contact);
    }

    public String getDepartment() {
        return department;
    }
}
