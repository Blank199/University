import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class ArbitruController extends UnicastRemoteObject implements ITriatlonObserver, Serializable {
    public static Stage curentStage;
    public static Stage loginStage;
    public static final String TITLE = "Arbitru";
    public static final String NAME = "/ArbitruXML.fxml";


    private ITriatlonService srv;
    private Arbitru arbitru;
    private ObservableList<Concurent> totiConcurentii = FXCollections.observableArrayList();
    private ObservableList<Concurent> concurentiProba = FXCollections.observableArrayList();


    @FXML
    private TableView<Concurent> tabelParticipanti;
    @FXML
    private TableColumn<Concurent, Integer> idConcurentiC;

    @FXML
    private TableColumn<Concurent, String> numeConcurentiC;

    @FXML
    private TableColumn<Concurent, Double> punctajConcurentiC;

    @FXML
    private TextField idConcurentText;

    @FXML
    private TextField punctajText;

    @FXML
    private TableView<Concurent> tabelRaport;

    @FXML
    private TableColumn<Concurent, Integer> idConcurentP;

    @FXML
    private TableColumn<Concurent, String> numeConcurentP;

    @FXML
    private TableColumn<Concurent, Double> puncteConcurentP;


    public ArbitruController()throws RemoteException { }

    @FXML
    public void initialize(){
        idConcurentiC.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeConcurentiC.setCellValueFactory(new PropertyValueFactory<>("nume"));
        punctajConcurentiC.setCellValueFactory(new PropertyValueFactory<>("punctaj"));
        idConcurentP.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeConcurentP.setCellValueFactory(new PropertyValueFactory<>("nume"));
        puncteConcurentP.setCellValueFactory(new PropertyValueFactory<>("punctaj"));


    }

    public void setService(ITriatlonService srv){
        this.srv = srv;
    }

    public void setArb(Arbitru arb){
        this.arbitru=arb;
        populateConcurenti();
        populateProba();
    }

    public void populateConcurenti(){
        Iterable<Concurent> concurent = null;
        try {
            concurent = this.srv.sortConcurenti();
            this.totiConcurentii.clear();
            concurent.forEach(x->this.totiConcurentii.add(x));
            tabelParticipanti.setItems(totiConcurentii);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void populateProba(){
        Iterable<Concurent> concurent = null;
        try {
            concurent = this.srv.participantiProba(arbitru.getTip());
            this.concurentiProba.clear();
            concurent.forEach(x->this.concurentiProba.add(x));
            tabelRaport.setItems(concurentiProba);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @FXML
    void handleAdaugaRezultat() {
        Rezultat aux = new Rezultat(arbitru.getId(),Integer.valueOf(idConcurentText.getText()),arbitru.getTip(),Double.valueOf(punctajText.getText()));
        try {
            srv.adaugaRezultat(aux);
            populateConcurenti();
            populateProba();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void handleLogout() {
        try{
            this.srv.logout(arbitru,this);
        }catch (Exception e){
            System.out.println("Logout Error "+e);
        }
        curentStage.hide();
        loginStage.show();

    }


    @Override
    public void resultAdded(List<List<Concurent>> list) throws Exception {
        int index;
        this.totiConcurentii.clear();
        this.totiConcurentii.addAll(list.get(0));
        tabelParticipanti.setItems(totiConcurentii);

        if(arbitru.getTip().equals("Natatie")){
            index = 1;
        }
        else if(arbitru.getTip().equals("Ciclism")){
            index = 2;
        }
        else{
            index = 3;
        }

        this.concurentiProba.clear();
        this.concurentiProba.addAll(list.get(index));
        tabelRaport.setItems(concurentiProba);
    }
}
