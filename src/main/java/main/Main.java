package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;
import View.View;



public class Main extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {


            View view = new View();
            Model model = new Model();
            Controller controller= new Controller();
            //view.addController(controller);
            controller.link(view,model);





            primaryStage.setTitle("Music Player");
            primaryStage.setScene(new Scene(view, 900, 500));
            primaryStage.show();

        }

        public static void main(String[] args) throws Exception{

            Application.launch();

        }
}

