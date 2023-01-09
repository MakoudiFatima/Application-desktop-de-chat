
package projectchatsocketsjava;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;



public class traitementFichier extends Thread {

    String sender, receiver;
    String filePath;
    Socket socketOfSender;
    BufferedWriter bw;
    BufferedReader br;
    JProgressBar progressBar;
    envoyerFichier frameToDisplayDialog;

    private final int BUFFER_SIZE = 1024;
    
    public traitementFichier(String sender, String receiver, String filePath, Socket socket, envoyerFichier frameToDisplayDialog, JProgressBar progressBar) {
        this.sender = sender;
        this.receiver = receiver;
        this.filePath = filePath;
        this.socketOfSender = socket;
        this.frameToDisplayDialog = frameToDisplayDialog;
        this.progressBar = progressBar;
        
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socketOfSender.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfSender.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(traitementFichier.class.getName()).log(Level.SEVERE, null, ex);
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
        FileInputStream fis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(filePath);     
            int leng = (int) file.length();     
            
            this.sendToServer("CMD_SENDFILETOSERVER|"+sender+"|"+receiver+"|"+file.getName()+"|"+leng);
            
            System.out.println("[SendingFileThread.java] CMD_SENDFILETOSERVER|"+sender+"|"+receiver+"|"+file.getName()+"|"+leng);   //dòng này thì có hiển thị ra
            
            fis = new FileInputStream(file);
            bos = new BufferedOutputStream(socketOfSender.getOutputStream());
            
            byte []buffer = new byte[BUFFER_SIZE];
            int count=0, percent=0;
            while((count = fis.read(buffer)) > 0) {
                //percent = percent + count;
                bos.write(buffer, 0, count);    
                //progressBar.setValue(percent/leng);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(traitementFichier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(fis != null) fis.close();
                if(bos != null) bos.close();
                socketOfSender.close();
            } catch (IOException ex) {
                Logger.getLogger(traitementFichier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(frameToDisplayDialog, "File successfully sent!", "Sucess", JOptionPane.INFORMATION_MESSAGE);  
    }
    
}

