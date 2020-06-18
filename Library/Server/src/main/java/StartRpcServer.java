import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class StartRpcServer {
    //private static int defaultPort=5555;

    public StartRpcServer() {
    }

    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");
        System.out.println("Waiting for clients...");


    }

}

