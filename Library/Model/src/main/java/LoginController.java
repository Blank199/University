import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject implements  Serializable {
    private ILibraryService srv;
    private LoginWindow lw;

    public LoginController() throws RemoteException {
    }

    public void setService(ILibraryService srv){
        this.srv = srv;
    }

    public LoginWindow initWindow(Stage lgnStage) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("LoginXml.fxml"));
        loader.setController(this);
        Parent root=loader.load();

        lgnStage.setTitle("Library");
        lgnStage.setScene(new Scene(root));
        this.lw = new LoginWindow(lgnStage);
        return lw;

    }

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passText;

    @FXML
    private Button loginBtn;

    @FXML
    void handleLogin(ActionEvent event)  {
        try {
            LibrarianController librarianController = new LibrarianController();
            librarianController.setService(srv);
            librarianController.setLoginWindow(this.lw);
            Librarian bibl = srv.checkLibrarian(usernameText.getText(),passText.getText(),librarianController);
            if(bibl != null){
                librarianController.setUsername(bibl.getUsername());
               LibrarianWindow bw = librarianController.initWindow();
               this.lw.hide();
                librarianController.populate();
               bw.show();
            }

            else {
                ClientController clientController = new ClientController();
                clientController.setService(srv);
                clientController.setLoginWindow(lw);
                Client client = srv.checkUser(usernameText.getText(),passText.getText(),clientController);
                if(client != null){
                    clientController.setId(client.getId());
                    clientController.setUsername(client.getUsername());
                    ClientWindow cw = clientController.initWindow();
                    this.lw.hide();
                    clientController.populate();
                    cw.show();
                }
                else{
                    Alert a = new Alert(Alert.AlertType.WARNING,"Numele sau parola sunt gresite");
                    a.show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR,e.getMessage());
            a.show();
        }

    }

}
