import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRpcClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        ILibraryService ts=(ILibraryService) factory.getBean("libraryService");

        LoginController loginCtrl= new LoginController();
        loginCtrl.setService(ts);

        LoginWindow lw = loginCtrl.initWindow(primaryStage);
        lw.show();

    }
}
