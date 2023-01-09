
package projectchatsocketsjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class serveur extends JFrame implements Runnable {   
      
    JButton btStart, btStop;
    JTextArea taInfo;
    ServerSocket serverSocket;
    JLabel jLabel7 ;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    SocketServeur serverThread;
    
public serveur() {
        JPanel panel = new JPanel(new BorderLayout());
         //setBounds(250, 100,800, 540);
       JLabel jLabel7 = new JLabel();
       jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Capture.png")));
       
        //lbStateServer.setFont(new java.awt.Font("SansSerif", 1, 18));
        //lbStateServer. setLocation(125, 0);

        taInfo = new JTextArea();
        taInfo.setEditable(false);
        taInfo.setFont(new java.awt.Font("SansSerif", 0, 20));
        
        
        JScrollPane scroll = new JScrollPane();
        
        scroll.setViewportView(taInfo);
        scroll.setPreferredSize(new Dimension(400, 400));
     
        // strat button 
        
        btStart = new JButton("Start server");
        btStart.setBackground(new java.awt.Color(0,204,51));
        btStart.setFont(new java.awt.Font("SansSerif", 0, 17)); // NOI18N
        btStart.setForeground(new java.awt.Color(0,204,51));
        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btStartEvent(ae);
            }
        });
        
        // stop button
        
        btStop = new JButton("Stop server");
        btStop.setBackground(new java.awt.Color(255, 102, 102));
        btStop.setFont(new java.awt.Font("SansSerif", 0, 17)); // NOI18N
        btStop.setForeground(new java.awt.Color(255, 102, 102));
        btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btStopEvent(ae);
            }
        });
        btStart.setEnabled(true);
        btStop.setEnabled(false);
        
        JPanel panelBtn = new JPanel();
        panelBtn.add(btStart);
        panelBtn.add(btStop);
        
        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(30, 30));
        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(30, 30));
        panel.add(jLabel7, BorderLayout.NORTH);
        panel.add(scroll);
        
        panel.add(panelBtn, BorderLayout.SOUTH);
       // panel.add(p1, BorderLayout.WEST);
       // panel.add(p2, BorderLayout.EAST);
        
       ;
        this.add(panel);
        
        setSize(680,680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void appendMessage(String message) {
        taInfo.append(message);
        taInfo.setCaretPosition(taInfo.getText().length() - 1);
    }
    
    private void startServer() throws Exception {
        try {
            serverSocket = new ServerSocket(9999); 
            appendMessage("--"+sdf.format(new Date())+"-- Le serveur est en cours d'exécution");
            appendMessage("\n --"+sdf.format(new Date())+"-- aucun personne conncté\n");
            
            while(true) {
                Socket socketOfServer = serverSocket.accept();    
                //ServerThread serverThread = new ServerThread(socketOfServer);
                serverThread = new SocketServeur(socketOfServer);
                serverThread.taServer = this.taInfo;
                serverThread.start();
            }
            
        } catch (java.net.SocketException e) {
            System.out.println("Server is closed");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Une erreur s'est produite, il se peut que ce port soit occupé par un serveur!");
            System.out.println("Ou le serveur est fermé");
            JOptionPane.showMessageDialog(this, "Ce port est occupé par le serveur", "Error", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            System.exit(0);
        }
    }

    private void btStartEvent(ActionEvent ae) {
        Connection conn = new baseDonnees().connect();
        if(conn == null) {
            JOptionPane.showMessageDialog(this, "Please open MySQL first", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new Thread(this).start();
        this.btStart.setEnabled(false);
        this.btStop.setEnabled(true);
    }
    
    private void btStopEvent(ActionEvent ae) {
        int kq = JOptionPane.showConfirmDialog(this, "Are you sure to close server?", "Notice", JOptionPane.YES_NO_OPTION);
        if(kq == JOptionPane.YES_OPTION) {
            try {
                //notify to all clients that server is closed:
                //code here
                //serverThread.notifyToAllUsers("Warnning: Server has been closed!");

                //then close server:
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
        serveur serverFrame = new serveur();
        serverFrame.setVisible(true);
        //serverFrame.startServer();
        
    }

    @Override
    public void run() {
        try {
            this.startServer();
        } catch (Exception ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}