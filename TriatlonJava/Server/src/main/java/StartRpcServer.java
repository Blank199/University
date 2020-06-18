import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartRpcServer {
    private static int defaultPort=5555;

    public StartRpcServer() {
    }
     private static SessionFactory factory;
    public static void main(String[] args) {

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

    }
}
