import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    public static Stage curentStage;
    public static Stage arbitruStage;
    private ArbitruController arbCtrl;
    public static final String TITLE = "Login";
    public static final String NAME = "LoginXml.fxml";

    Parent p;

    private ITriatlonService srv;

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passText;

    @FXML
    private Button loginBtn;

    @FXML
    private Label loginLable;

    public LoginController() {
    }

    public void setService(ITriatlonService srv) {
        this.srv = srv;
    }

    public void setParent(Parent p){
        this.p=p;
    }

    public void setArbController(ArbitruController a){
        this.arbCtrl = a;
    }

    public void setCtrl(ArbitruController arb){
        this.arbCtrl = arb;
    }

    public void handleLogin(){
        String username = usernameText.getText();
        String password = passText.getText();

        Arbitru arb = null;

        try {
            arb = srv.autentificare(username,password,this.arbCtrl);

            if(arb != null){
                if(LoginController.arbitruStage==null) {
                    Stage stage = new Stage();
                    stage.setTitle(arb.getNume());
                    stage.setScene(new Scene(p));


                    LoginController.arbitruStage = stage;
                    ArbitruController.curentStage = arbitruStage;
                    ArbitruController.loginStage = LoginController.curentStage;
                }

                curentStage.hide();
                this.arbCtrl.setArb(arb);

                arbitruStage.setTitle(arb.getNume());

                arbitruStage.show();
            }

            else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Nume sau parola gresita");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
