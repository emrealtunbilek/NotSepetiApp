package emrealtunbilek.com.notsepetiapp.data;

/**
 * Created by Emre Altunbilek on 12.10.2017.
 */

public class Notlar {

    private int id;
    private String notIcerik;
    private String notTarih;
    private int tamamlandi;

    public Notlar(String notIcerik) {
        this.notIcerik = notIcerik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotIcerik() {
        return notIcerik;
    }

    public void setNotIcerik(String notIcerik) {
        this.notIcerik = notIcerik;
    }

    public String getNotTarih() {
        return notTarih;
    }

    public void setNotTarih(String notTarih) {
        this.notTarih = notTarih;
    }

    public int getTamamlandi() {
        return tamamlandi;
    }

    public void setTamamlandi(int tamamlandi) {
        this.tamamlandi = tamamlandi;
    }
}
