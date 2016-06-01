package cmpe277.termproj_rentapp;

/**
 * Created by ivanybma on 4/25/16.
 */
public class LoginData {

    private String fb_id;
    private String fb_name;
    private String phone;
    private String email;
    private String role;

    public LoginData(){}
    public LoginData(String fb_id,String fb_name,String phone,String email, String role){
        this.fb_id=fb_id;
        this.fb_name=fb_name;
        this.phone=phone;
        this.email=email;
        this.role=role;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFb_name() {
        return fb_name;
    }

    public void setFb_name(String fb_name) {
        this.fb_name = fb_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
