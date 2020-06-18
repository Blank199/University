import javafx.stage.Stage;

import java.io.IOException;

public class ClientWindow  {
    private Stage stage;
    public ClientWindow(Stage stage){
        this.stage = stage;
    }

    public void show(){
        this.stage.show();
    }

    public void hide(){
        this.stage.hide();
    }
}
