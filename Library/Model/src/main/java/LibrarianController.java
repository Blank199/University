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
import java.util.List;

public class LibrarianController extends UnicastRemoteObject implements ILibraryObserver, Serializable {

    private ObservableList<Book> books = FXCollections.observableArrayList();

    public LibrarianController() throws RemoteException {}
    @FXML
    private TableView<Book> table;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TextField bookStatusText;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> rentStatusColumn;

    @FXML
    private TableColumn<Book, String> bookStatusColumn;

    @FXML
    private TextField nameText;

    @FXML
    private TextField authorText;

    @FXML
    private Button addBtn;

    @FXML
    private Button delBtn;

    @FXML
    private Button modBtn;

    @FXML
    private Button logoutBtn;


    private LibrarianWindow bw;
    private LoginWindow lw;

    private String username;

    private ILibraryService srv;

    public void setService(ILibraryService srv){
        this.srv = srv;
    }

    public void setLoginWindow(LoginWindow lw){
        this.lw = lw;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void populate(){
        this.books.clear();
        books.setAll(srv.findAll());
        this.table.getItems().setAll(books);
    }

    public LibrarianWindow initWindow(){
        try {
        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("LibrarianXML.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Stage blibStage = new Stage();
        blibStage.setTitle("Librarian");
        blibStage.setScene(new Scene(root));
        this.bw = new LibrarianWindow(blibStage);
        return bw;
        } catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR,e.getMessage());
            a.show();
        }
        return null;
    }

    @FXML
    public void initialize() throws Exception{
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        rentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("rentalStatus"));
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookCondition"));
        this.table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onEdit());
        idColumn.setVisible(false);

    }

    @FXML
    public void handleAdd(){
        srv.addBook(nameText.getText(),authorText.getText(),bookStatusText.getText());

    }

    @FXML
    public void handleDel(){
        Book book = returnSelected();
        if (book != null) {
            try {
                srv.delBook(book.getId());
            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.WARNING,e.getMessage());
                a.show();
            }

        }

        else {
            Alert a = new Alert(Alert.AlertType.WARNING,"Nu ai ales nicio Book");
            a.show();

        }
    }

    @FXML
    public void handleUpdate(){
        Book book = returnSelected();
        if (book != null) {
            String test =  nameText.getText();
            String test1 =  authorText.getText();

            srv.updateBook(book.getId(), nameText.getText(), authorText.getText(), book.getRentalStatus(), bookStatusText.getText());

        }

        else {
            Alert a = new Alert(Alert.AlertType.WARNING,"Nu ai ales nicio Book");
            a.show();

        }
    }

    @FXML
    public void handleLogout(){
        try {
            bw.hide();
            srv.logout(username);
            lw.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.WARNING,e.getMessage());
            a.show();
        }
    }

    public void onEdit() {
        // check the table's selected item and get selected item
        if (table.getSelectionModel().getSelectedItem() != null) {
            Book book = table.getSelectionModel().getSelectedItem();
            nameText.setText(""+book.getTitle());
            authorText.setText(""+book.getAuthor());
            bookStatusText.setText(""+book.getBookCondition());

        }

    }

    public Book returnSelected(){
        if (table.getSelectionModel().getSelectedItem() != null) {
            Book book = table.getSelectionModel().getSelectedItem();
            return book;
        }
        return null;
    }


    @Override
    public void modifiedBook(List<List<Book>> carti) {
        this.books.clear();
        books.setAll(carti.get(0));
        this.table.getItems().setAll(books);
    }
}
