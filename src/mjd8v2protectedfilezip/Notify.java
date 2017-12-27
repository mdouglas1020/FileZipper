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
@FunctionalInterface
public interface Notify {
    public void handle(double percDone, zipStage stage, String msg);
}
