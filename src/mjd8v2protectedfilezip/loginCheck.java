/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;

/**
 *
 * @author Michael
 */
public interface loginCheck {
    String user = "testLogin";
    String pass = "test";
    String wrongUser = "Incorrect username";
    String wrongPass = "Incorrect password";
    
    public int checkUsername(String input);
    public int checkPass(String input);
    
}
