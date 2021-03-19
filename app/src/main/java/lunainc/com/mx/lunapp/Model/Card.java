package lunainc.com.mx.lunapp.Model;

public class Card {


    private String uuid;

    private String client_uuid;

    private String date;

    private int size;

    public Card() {
    }

    public Card(String uuid, String client_uuid, String date, int size) {
        this.uuid = uuid;
        this.client_uuid = client_uuid;
        this.date = date;
        this.size = size;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
