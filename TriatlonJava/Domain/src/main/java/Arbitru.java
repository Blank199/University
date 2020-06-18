import java.io.Serializable;

public class Arbitru implements Serializable {
    private Integer id;
    private String nume;
    private String parola;
    private String tip;

    public Arbitru(Integer id,String nume, String parola,String tip)  {
        this.id=id;
        this.nume=nume;
        this.parola = parola;
        this.tip = tip;
    }
    public Arbitru(){}

    @Override
    public String toString() {
        return "Arbitru{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }


}
