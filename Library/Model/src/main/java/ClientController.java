import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClientController extends UnicastRemoteObject implements ILibraryObserver, Serializable {
    private ILibraryService srv;
    private ClientWindow cw;
    private LoginWindow lw;

    private ObservableList<Book> books = FXCollections.observableArrayList();


    public ClientController() throws RemoteException {
    }

    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> rentStatusColumn;

    @FXML
    private TableColumn<Book, String> bookStatusColumn;

    @FXML
    private Button rentBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private ComboBox<Book> comboBox;

    @FXML
    private Button logoutBtn;

    private Integer clientId;

    private String username;

        public void setId(Integer id){
        this.clientId = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setService(ILibraryService srv){
        this.srv = srv;
    }

    public void setLoginWindow(LoginWindow lw){
        this.lw = lw;
    }

    private void loadCombo(){
        ObservableList<Book> obList = FXCollections.observableArrayList(srv.findForOne(clientId));
        comboBox.setItems(obList);
        comboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void initialize() throws Exception{
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        rentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rentalStatus"));
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));
        loadCombo();
        idColumn.setVisible(false);


    }

    public void populate(){
        this.books.clear();
        books.setAll(srv.findAllAvailable());
        this.table.getItems().setAll(books);
        loadCombo();
    }

    public Book getFromCombo(){
        return comboBox.getSelectionModel().getSelectedItem();

    }

    public ClientWindow initWindow(){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("ClientXML.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Stage clientStage = new Stage();
            clientStage.setTitle("Client");
            clientStage.setScene(new Scene(root));
            this.cw = new ClientWindow(clientStage);
            return cw;
        } catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR,e.getMessage());
            a.show();
        }
        return null;
    }

    public Book returnSelected(){
        if (table.getSelectionModel().getSelectedItem() != null) {
            Book book = table.getSelectionModel().getSelectedItem();
            return book;
        }
        return null;
    }

    @FXML
    void handleLogout() {
        try {
            cw.hide();
            srv.logout(username);
            lw.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.WARNING,e.getMessage());
            a.show();
        }

    }

    @FXML
    void handleReturn(ActionEvent event) {
        Book book = getFromCombo();
        if (book != null) {
            srv.delRent(book.getId(), clientId, 1);
            loadCombo();
        }
        else{
            Alert a = new Alert(Alert.AlertType.WARNING,"Nu ai ales nicio carte");
            a.show();
        }

    }

    @FXML
    void handleRent(ActionEvent event) {
        Book book = returnSelected();
        if (book != null) {
            srv.addRent(book.getId(), clientId, new Date(), book.getBookCondition());
            loadCombo();
        }

        else{
            Alert a = new Alert(Alert.AlertType.WARNING,"Nu ai ales nicio Book");
            a.show();
        }

    }

    @Override
    public void modifiedBook(List<List<Book>> carti) {
        this.books.clear();
        books.setAll(carti.get(1));
        this.table.getItems().setAll(books);
    }
}

