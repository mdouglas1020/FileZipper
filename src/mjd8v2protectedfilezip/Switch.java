/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 *
 * @author Michael
 */
public abstract class Switch {
    public static Scene scene;
    public Parent root;
    public static final HashMap<String, Switch> controllers = new HashMap<>();
    
    public void setRoot(Parent root)
    {
        this.root = root;
    }
    public Parent getRoot()
    {
        return root;
    }
    public static Switch getController(String name)
    {
        return controllers.get(name);
    }
    
    public static Switch add(String name)
    {
        Switch controller;
        controller = controllers.get(name);
        
        
        try{
            FXMLLoader loader = new FXMLLoader(Switch.class.getResource(name + ".fxml"));
            
            Parent root = (Parent) loader.load();
            controller = (Switch) loader.getController();
            controller.setRoot(root);
            controllers.put(name, controller);
        } catch(IOException ioex){
            System.out.println("Cannot load " + name + ".fxml \n" + ioex);
            controller = null;
        } catch(Exception ex) {
            System.out.println("Cannot load " + name + ".fxml \n" + ex);
            controller = null;
        }
        return controller;
    }
    
    public static void switchTo(String name)
    {
        Switch controller = controllers.get(name);
        if(controller == null)
        {
            controller = add(name);
        }
        if(controller != null)
        {
            if(scene != null)
            {
                scene.setRoot(controller.getRoot());
            }
        }
    }
}
