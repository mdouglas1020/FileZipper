/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mjd8v2protectedfilezip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.application.Platform;


/**
 *
 * @author Michael
 */
public class zipModel extends Thread {
    private File destinationDirect;
    private File sourceDirect;
    private File[] files;
    private Notify notif;
    public Boolean stop = false;
    static final int BUFFER = 2048;
    
    
    public zipModel(File sourceDirect, File destinationDirect) {
        this.sourceDirect = sourceDirect;
        this.destinationDirect = destinationDirect;
        files = sourceDirect.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File directory, String filename){
                return true;
            }
        });
    }
    
    public File[] getFiles() {
        return this.files;
    }
    public int getNumFiles() {
        if(files == null)
        {
            return 0;
        }
        return files.length;
    }
    private void getAllFiles(File destination, List<File> fileList) {
        try {
            File[] files = destination.listFiles();
            for (File oneFile : files) {
                fileList.add(oneFile);
                if (oneFile.isDirectory()) {
                    System.out.println("directory:" + oneFile.getCanonicalPath());
                    getAllFiles(oneFile, fileList);
                } else {
                    System.out.println("    file:" + oneFile.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addToZip(File dirToZip, File file, ZipOutputStream zipOut) throws IOException {
        FileInputStream input = new FileInputStream(file);
        
        String zippedPath = file.getCanonicalPath().substring(dirToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());
        
        System.out.println("Zipping the file " + zippedPath);
        ZipEntry zipEntry = new ZipEntry(zippedPath);
        zipOut.putNextEntry(zipEntry);
        
        byte[] bytes = new byte[1024];
        int len;
        
        while((len = input.read(bytes)) >= 0) {
            zipOut.write(bytes,0,len);
        }
        
        zipOut.closeEntry();
        input.close();
    }
    private double calcPerc(int count, int sum) {
        if (sum == 0) return 0;
        return (double)count/(double)sum;
    }
    public void setOnNotif(Notify notif) {
        this.notif = notif;
    }
    private void runNotify(double percDone, zipStage stage, String msg) {
        if(notif != null) {
            Platform.runLater(() -> {
               notif.handle(percDone, stage, msg); 
            });
        }
    }
    
    private void writeZipFile(File dirToZip, List<File> fileList) {
        try {
            FileOutputStream fos = new FileOutputStream(this.destinationDirect.getPath() + "/" + dirToZip.getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            int doneFiles = 0;
            
            
            for( File file : fileList) {
                if (!file.isDirectory()) {
                    addToZip(dirToZip, file, zos);
                    doneFiles++;
                    int all = (int) (fileList.size() * .85);
                    runNotify(calcPerc(doneFiles, all), zipStage.RUNNING, "Extracting " + file.getPath() + " to " + this.destinationDirect.getPath() + "/" + dirToZip.getName() + ".zip");
                    
                }
            }
            String msg = "Done! Your file has been zipped and saved!";
            runNotify(1, zipStage.ENDED, msg);
            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void run() {
        try {
            File sourceDir = new File(this.sourceDirect.getPath());
            List<File> filesToZip = new ArrayList<File>();
            System.out.println("Preparing to zip " + sourceDir.getCanonicalPath());
            getAllFiles(sourceDir, filesToZip);
            System.out.println("Creating the destination directory for your zip file...");
            writeZipFile(sourceDir, filesToZip);
            System.out.println("File compression complete!");
        } catch (IOException ex) {
            Logger.getLogger(zipModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
