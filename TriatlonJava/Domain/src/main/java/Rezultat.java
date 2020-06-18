import java.io.Serializable;
import java.security.PublicKey;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Rezultat implements Serializable {
    private Integer arbitru_id;
    private Integer concurent_id;
    private String proba;
    private Double punctaj;

    public Integer getArbitru_id() {
        return arbitru_id;
    }
    public Rezultat(){}

    @Override
    public String toString() {
        return "Rezultat{" +
                "arbitru_id='" + arbitru_id + '\'' +
                ", concurent_id='" + concurent_id + '\'' +
                ", proba='" + proba + '\'' +
                ", punctaj=" + punctaj +
                '}';
    }

    public String getProba() {
        return proba;
    }

    public void setProba(String proba) {
        this.proba = proba;
    }

    public void setArbitru_id(Integer arbitru_id) {
        this.arbitru_id = arbitru_id;
    }

    public Integer getConcurent_id() {
        return concurent_id;
    }

    public void setConcurent_id(Integer concurent_id) {
        this.concurent_id = concurent_id;
    }

    public Double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(Double punctaj) {
        this.punctaj = punctaj;
    }

    public Rezultat(Integer arbitru_id, Integer concurent_id,String proba, Double punctaj) {
        this.arbitru_id = arbitru_id;
        this.concurent_id = concurent_id;
        this.punctaj = punctaj;
        this.proba=proba;
    }
}
