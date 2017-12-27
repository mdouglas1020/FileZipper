/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx.application.Application.STYLESHEET_MODENA;

/**
 *
 * @author Michael
 */
public class Mjd8v2ProtectedFileZip extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      HBox root = new HBox();
      root.setPrefSize(600, 400);
      root.setAlignment(Pos.CENTER);
      Text alert = new Text("Failed to launch!");
      alert.setFont(Font.font(STYLESHEET_MODENA, 32));
      root.getChildren().add(alert);
      
     Scene scene = new Scene(root);
     
     
     Switch.scene = scene;
     Switch.switchTo("LoginFXML");
     stage.setScene(scene);
     
     stage.show();
    // stage.setResizable(false);
              
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
