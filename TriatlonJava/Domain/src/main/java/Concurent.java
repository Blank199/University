import java.io.Serializable;

public class Concurent implements Serializable {
    private Integer id;
    private String nume;
    private Double punctaj;

    public Concurent(Integer id, String nume, Double punctaj) {
        this.nume = nume;
        this.id=id;
        this.punctaj=punctaj;
    }
    public Concurent(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int de_ce_nu(){
        return 2;
    }

    public Double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(Double punctaj) {
        this.punctaj = punctaj;
    }



    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    @Override
    public String toString() {
        return "Concurent{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", punctaj=" + punctaj +
                '}';
    }

    public void adaugaPunctaj(Double x){
        this.punctaj += x;
    }

}
