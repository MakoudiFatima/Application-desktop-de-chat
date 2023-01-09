
package projectchatsocketsjava;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import projectchatsocketsjava.chatPrive;

public class recevoirFichier extends Thread {

    private final int BUFFER_SIZE = 1024;
    StringTokenizer tokenizer;
    BufferedWriter bw;
    BufferedReader br;
    Socket socketOfReceiver;
    String myDownloadFolder;
    String fileName;
    chatPrive frameToDisplayDialog;

    public recevoirFichier(Socket socketOfReceiver, String myDownloadFolder, String fileName, chatPrive pc) {
        this.socketOfReceiver = socketOfReceiver;
        this.myDownloadFolder = myDownloadFolder;
        this.fileName = fileName;
        this.frameToDisplayDialog = pc;
        
        try {
            br = new BufferedReader(new InputStreamReader(socketOfReceiver.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socketOfReceiver.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(recevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToServer(String line) {
        try {
            this.bw.write(line);
            this.bw.newLine();  
            this.bw.flush();
        } catch (java.net.SocketException e) {
            JOptionPane.showMessageDialog(null, "Server is close, can't send message!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            this.sendToServer("CMD_DOWNLOADFILE|"+fileName);
            bis = new BufferedInputStream(socketOfReceiver.getInputStream());   
            fos = new FileOutputStream(myDownloadFolder + "\\" + fileName);   

            byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            while((count = bis.read(buffer)) > 0) { 
                fos.write(buffer, 0, count);       
            }

            JOptionPane.showMessageDialog(frameToDisplayDialog, "File downloaded to\n"+myDownloadFolder + "\\" + fileName, "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(recevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
           
        } finally {
            try {
                if(bis != null) bis.close();
                if(fos != null) fos.close();
                socketOfReceiver.close();
            } catch (IOException ex) {
                Logger.getLogger(recevoirFichier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
