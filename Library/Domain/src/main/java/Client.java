import java.io.Serializable;

public class Client implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String cnp;
    private String name;
    private String adress;
    private String phone;

    public Client(){};

    public Client( String username, String password, String cnp, String name, String adress, String phone) {
        this.username = username;
        this.password = password;
        this.cnp = cnp;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cnp='" + cnp + '\'' +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
