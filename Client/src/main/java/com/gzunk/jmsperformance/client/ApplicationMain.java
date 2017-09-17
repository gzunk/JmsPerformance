package com.gzunk.jmsperformance.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import java.net.URL;
import java.util.concurrent.ExecutorService;


public class ApplicationMain extends Application{

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {

        LOG.info("Starting Client");
        launch(args);

    }

    ApplicationContext ac;

    @Override
    public void start(Stage stage) throws Exception {

        // Load application context
        ac = new AnnotationConfigApplicationContext(AppConfig.class);

        FXMLLoader loader = new FXMLLoader(ApplicationMain.class.getResource("/mainForm.fxml"));
        loader.setControllerFactory(ac::getBean);
        Parent root = loader.load();

        stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void stop() {
        LOG.info("We really need to close now");

        // Close the Executor Service
        ExecutorService executorService = (ExecutorService)ac.getBean("executorService");
        executorService.shutdown();

        // Close the connection
        CachingConnectionFactory cf = (CachingConnectionFactory)ac.getBean("connectionFactory");
        cf.destroy();
    }
}
