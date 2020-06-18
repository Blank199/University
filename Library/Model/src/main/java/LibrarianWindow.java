import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianWindow  {
    private Stage stage;
    public LibrarianWindow(Stage stage){
        this.stage = stage;
    }

    public void show(){
        this.stage.show();
    }

    public void hide(){
        this.stage.hide();
    }

}
