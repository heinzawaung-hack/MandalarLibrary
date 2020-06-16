package sample.LogIn;

public class LogInModel {
    private int id;
    private String email;
    private String password;
    private String address;
    private String pinNumber;
    private String gender;

    public LogInModel(int id, String email, String password, String address, String pinNumber, String gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.pinNumber = pinNumber;
        this.gender = gender;
    }

    //to register user
    public LogInModel(String email, String password, String address, String pinNumber, String gender) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.pinNumber = pinNumber;
        this.gender = gender;
    }

    //to LogIn Check
    public LogInModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}