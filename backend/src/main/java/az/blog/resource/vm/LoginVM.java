package az.blog.resource.vm;

public class LoginVM {
    private String username;
    private String password;

    public LoginVM() {

    }

    public LoginVM(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
