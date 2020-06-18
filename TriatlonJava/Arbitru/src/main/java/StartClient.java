import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.rmi.runtime.Log;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
public class StartClient extends Application {

    private static int defaultTriatlonPort=5555;
    private static String defaultServer="localhost";

    @Override
    public void start(Stage primaryStage) throws IOException {


        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        ITriatlonService ts=(ITriatlonService) factory.getBean("triatlonService");

        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("LoginXml.fxml"));
        Parent root=loader.load();

        LoginController loginCtrl=loader.getController();
        loginCtrl.setService(ts);
        LoginController.curentStage = primaryStage;

        FXMLLoader cloader=new FXMLLoader(getClass().getClassLoader().getResource("ArbitruXML.fxml"));
        Parent cRoot=cloader.load();

        ArbitruController arbCtrl=cloader.getController();
        loginCtrl.setCtrl(arbCtrl);
        arbCtrl.setService(ts);

        loginCtrl.setArbController(arbCtrl);
        loginCtrl.setParent(cRoot);

        primaryStage.setTitle("Triatlon MPP");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
