/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Michael
 */
public class LoginController extends Switch implements Initializable, loginCheck {
  
    
     @FXML
     private Button login_btn;
     @FXML
     private TextField userName;
     @FXML
     private PasswordField userPass;
     @FXML
     private Label message;
     
     @Override
      public int checkUsername(String input){
          if(input.equals(loginCheck.user)){
              return 1;
          } else {
             
              return 0;
          }
      }
      
      @Override
      public int checkPass(String input) {
          if(input.equals(loginCheck.pass)){
              return 1;
          } else {
             
              return 0;
          }
      }
      @FXML
      private void viewAppInfo(ActionEvent event) {
          Switch.switchTo("AboutPageFXML");
      }
      @FXML
      private void validate_Login(ActionEvent event) {
          String inputUser = userName.getText();
          String inputPass = userPass.getText();
          
          
          if(checkUsername(inputUser) == 1)
          {
              if(checkPass(inputPass) == 1){
                  Switch.switchTo("zipperFXML");
              } else {
                  message.setText(loginCheck.wrongPass);
              }
          } else {
              message.setText(loginCheck.wrongUser);
          }
          
          
      }
      
      @Override
      public void initialize(URL url, ResourceBundle rb){
          
      }
}