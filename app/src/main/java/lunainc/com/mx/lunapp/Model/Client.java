package lunainc.com.mx.lunapp.Model;

public class Client {


    private String uuid;

    private String name;

    private String phone;

    public Client() {
    }

    public Client(String uuid, String name, String phone) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
