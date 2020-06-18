import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow  {
    private Stage stage;
    public LoginWindow(Stage stage){
        this.stage = stage;
    }

    public void show(){
        this.stage.show();
    }

    public void hide(){
        this.stage.hide();
    }
}
