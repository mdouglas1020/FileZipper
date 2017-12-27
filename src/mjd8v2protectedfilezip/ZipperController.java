/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class ZipperController extends Switch implements Initializable {

    private zipModel zipper;
    private File source;
    private File dest;
    private Stage stage;
    
    @FXML
    private Label sourceLabel;
    @FXML
    private TextArea message;
    @FXML
    private Label destLabel;
    @FXML
    private ProgressBar progBar;
    
    
    @FXML
    private void doLogout(Event event){
        Switch.switchTo("LoginFXML");
        
    }
    @FXML
    private void selectFileToZip(Event event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File dir = dirChooser.showDialog(stage);
        if(dir != null) {
            sourceLabel.setText(dir.getPath());
            source = dir;
        }
    }
    @FXML
    private void selectZipDestination(Event event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File dir = dirChooser.showDialog(stage);
        if(dir != null) {
            destLabel.setText(dir.getPath());
            dest = dir;
        }
    }
    @FXML
    private void zipThisFile(Event event) {
        progBar.setProgress(0);
        message.setText("Beginning zip process...");
        
        if (source == null) {
            message.setText("You have not selected a file to zip!\n");
            return;
        }
        if(dest == null) {
            message.setText("You have not selected a location to save the zip file!\n");
            return;
        }
        //Thread creation....please god work
        zipper = new zipModel(source, dest);
        
        zipper.setOnNotif(new Notify() {
            @Override
            public void handle(double percDone, zipStage stage, String msg) {
                if(stage.equals(zipStage.RUNNING)) {
                    progBar.setProgress(percDone);
                    msg = msg + "\n" + message.getText();
                    message.setText(msg);
                } else if(stage.equals(zipStage.ENDED)) {
                    progBar.setProgress(percDone);
                    msg = msg + "\n" + message.getText();
                    message.setText(msg);
                }
            }
        });
        
        zipper.start();
    }
    
     private void printExceptionAlert(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
         alert.setContentText(ex.getMessage());
        alert.setHeaderText(ex.getClass().getCanonicalName());
       
        
        StringWriter writer = new StringWriter();
        PrintWriter printer = new PrintWriter(writer);
        ex.printStackTrace(printer);
        String exceptionText = writer.toString();
        
        Label label = new Label("The exception stacktrace was: ");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setEditable(true);
        textArea.setWrapText(true);
        
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane exContent = new GridPane();
        exContent.add(label, 0, 0);
        exContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(exContent);
        
        alert.showAndWait();
    }
    
     @FXML
      private void doSave(Event event) {
          FileChooser choseFile = new FileChooser();
          Stage stage = (Stage) root.getScene().getWindow();
          File file = choseFile.showSaveDialog(stage);
          FileWriter writer = null;
          
          if(file != null){
              try {
                  writer = new FileWriter(file + ".txt");
                  writer.write(message.getText());
              } catch (IOException ex) {
                  printExceptionAlert(ex);
              } catch (Exception ex) {
                  printExceptionAlert(ex);
              } finally {
                  if(writer != null) {
                      try {
                          writer.close();
                          
                      } catch (IOException ex) {
                          printExceptionAlert(ex);
                      } catch (Exception ex) {
                          printExceptionAlert(ex);
                      }
                  }
              }
          }
      }
      
      @FXML
      private void doOpen(Event event) {
          FileChooser choseFile = new FileChooser();
          Stage stage = (Stage) root.getScene().getWindow();
          choseFile.getExtensionFilters().add (
          new FileChooser.ExtensionFilter("Text files", "*.txt")
          );
          choseFile.showOpenDialog(stage);
          File file = choseFile.showOpenDialog(stage);
          
          if(file != null){
              BufferedReader buffRead = null;
              try {
                  buffRead = new BufferedReader(new FileReader(file));
                  String doc = "";
                  String line = "";
                  while((line = buffRead.readLine()) != null) {
                      doc += line + "\n";
                  }
                  message.setText(doc);
              } catch (FileNotFoundException ex) {
                  printExceptionAlert(ex);
              } catch (IOException ex) {
                printExceptionAlert(ex); 
              } finally {
                  if(buffRead != null) {
                      try {
                          buffRead.close();
                      } catch (IOException ex) {
                          printExceptionAlert(ex);
                      }
                  }
              } 
                      
          }
      }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
