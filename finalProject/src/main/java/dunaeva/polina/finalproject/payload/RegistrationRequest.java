package dunaeva.polina.finalproject.payload;

import javax.validation.constraints.NotEmpty;

public class RegistrationRequest {

    public RegistrationRequest() {
    }

    public RegistrationRequest(@NotEmpty String email, @NotEmpty String password,
                               @NotEmpty String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
