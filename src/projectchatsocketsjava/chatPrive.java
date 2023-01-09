
package projectchatsocketsjava;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class chatPrive extends javax.swing.JFrame {

    /**
     * Creates new form ChatInterface
     */
       public String sender, receiver; 
    public String serverHost;
    public BufferedWriter bw;
    public BufferedReader br;
    HTMLEditorKit htmlKit;
    HTMLDocument htmlDoc;  
    public chatPrive() {
        initComponents();
        htmlKit = new HTMLEditorKit();
        htmlDoc = new HTMLDocument();
        affichageMsg.setEditorKit(htmlKit);
        affichageMsg.setDocument(htmlDoc);
    }

    public chatPrive(String sender, String receiver, String serverHost, BufferedWriter bw, BufferedReader br) {
        initComponents();
        this.sender = sender;
        this.receiver = receiver;
        this.serverHost = serverHost;
        this.bw = bw;
        this.br = br;
        
        htmlKit = new HTMLEditorKit();
        htmlDoc = new HTMLDocument();
        affichageMsg.setEditorKit(htmlKit);
        affichageMsg.setDocument(htmlDoc);
    }

    public JLabel getLbReceiver() {
     return sendeLabel;
    }
   
    
    
    
    public void sendToServer(String line) {
        try {
            this.bw.write(line);
            this.bw.newLine();   
            this.bw.flush();
        } catch (java.net.SocketException e) {
            JOptionPane.showMessageDialog(this, "Server is closed, can't send message!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.NullPointerException e) {
            System.out.println("[sendToServer()] Server is not open yet, or already closed!");
        } catch (IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String recieveFromServer() {
        try {
            return this.br.readLine();  
        } catch (java.lang.NullPointerException e) {
            System.out.println("[recieveFromServer()] Server is not open yet, or already closed!");
        } catch (IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
  
    public void appendMessage(String msg1, String msg2, Color c1, Color c2) {  
    
        int len = affichageMsg.getDocument().getLength();
        StyledDocument doc = (StyledDocument) affichageMsg.getDocument();
        
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Tahoma");
        StyleConstants.setBold(sas, true);
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c1);
        //StyleConstants.setAlignment(sas, StyleConstants.ALIGN_RIGHT);
        
        try {
            doc.insertString(len, msg1, sas);
        } catch (BadLocationException ex) {
           // Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        doc = (StyledDocument) affichageMsg.getDocument();
        len = len+msg1.length();
        
        sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, "Arial");
        StyleConstants.setFontSize(sas, 14);
        StyleConstants.setForeground(sas, c2);
        //StyleConstants.setAlignment(sas, StyleConstants.ALIGN_RIGHT);
        
        try {
            doc.insertString(len, msg2+"\n", sas);  
        } catch (BadLocationException ex) {
           // Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        affichageMsg.setCaretPosition(len+msg2.length());
    }
    
    public void appendMessage_Left(String msg1, String msg2) {     
        try {
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\" color:black; padding: 3px; margin-top: 4px; margin-right:35px; text-align:left; font:normal 12px Tahoma;\"><span style=\"background-color:#f3f3f3;\"><b>" + msg1 + "</b><span style=\"color:black;\">" + msg2 + "</span></span></p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength());
    }
    
    public void appendMessage_Left(String msg1, String msg2, String color1, String color2) {    
        try {
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:" + color1 + "; padding: 3px; margin-top: 4px; margin-right:35px; text-align:left; font:normal 12px Tahoma;\"><span><b>" + msg1 + "</b><span style=\"color:" + color2 + ";\">" + msg2 + "</span></span></p><br/>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength());
    }
    
   public void appendMessage_Right(String msg1, String msg2) {   
        try { 
            //htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:blue; margin-left:30px; text-align:right; font:normal 12px Tahoma;\"><b>" + msg1 + "</b><span style=\"color:black;\">" + msg2 + "</span></p>", 0, 0, null);
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"border-radius: 50px 20px; \n" +
"" +
";\n" +
"overflow: hidden;color:black;border: 0px; padding: 3px; margin-top: 4px; margin-left:35px; text-align:right;background-color: #00ccff;  font:normal 12px Tahoma;width: 300px;\">" + msg2 + "</p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength());
    }
    
    public void appendMessage_Right(String msg1) {   
        try { 
            //htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"color:blue; margin-left:30px; text-align:right; font:normal 12px Tahoma;\"><b>" + msg1 + "</b><span style=\"color:black;\">" + msg2 + "</span></p>", 0, 0, null);
            htmlKit.insertHTML(htmlDoc, htmlDoc.getLength(), "<p style=\"border-radius:50px 20px; \n" +
"\n" +
"\n" +
"overflow: hidden;color:black; border: 0px ;padding: 3px; margin-top: 4px; margin-left:35px;background-color:#00ccff; text-align:right; font:normal 12px Tahoma;width: 300px; \">" + msg1 + "</p>", 0, 0, null);
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength());
    }
//"<p style=\"color:black; padding: 10px; margin-top: 20px; margin-left:35px; text-align:right; font:normal 16px Tahoma;\"><span style=\"background-color: #00cc00;border-color: red;-webkit-border-radius: 1em;border-top-width: 20Px ;\">" + msg1 + "</span></p>", 0, 0, null);
        
    public void insertButton(String sender, String fileName) {
        JButton bt = new JButton(fileName);
        bt.setName(fileName);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                downloadFile(fileName);
            }
        });
        appendMessage_Left(sender, " a envoyé un fichier ");
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength() - 1);
        affichageMsg.insertComponent(bt);
    }
    
    public void insertButtonLeft(String sender, String fileName) {
        JButton bt = new JButton(fileName);
        bt.setName(fileName);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                downloadFile(fileName);
            }
        });
        appendMessage_Left(sender, " a envoyé un fichier ");
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength() - 1);
        affichageMsg.insertComponent(bt);
    }
    //droit
    public void insertButtonRight(String sender, String fileName) {
        JButton bt = new JButton(fileName);
        bt.setName(fileName);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                downloadFile(fileName);
            }
        });
        appendMessage_Right(sender, " sends a file ");
        affichageMsg.setCaretPosition(affichageMsg.getDocument().getLength() - 1);
        affichageMsg.insertComponent(bt);
    }

    private void downloadFile(String buttonName) {
        String myDownloadFolder;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int kq = chooser.showSaveDialog(this);
        if(kq == JFileChooser.APPROVE_OPTION) {
            myDownloadFolder = chooser.getSelectedFile().getAbsolutePath();
        } else {
            myDownloadFolder = "D:";
            JOptionPane.showMessageDialog(this, "The default folder to save file is in D:\\", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            Socket socketOfReceiver = new Socket(serverHost, 9999);    
            new recevoirFichier(socketOfReceiver, myDownloadFolder, buttonName, this).start();    
            System.out.println("start receiving file");
        } catch (IOException ex) {
            Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sendeLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        affichageMsg = new javax.swing.JTextPane();
        btSend_pc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        tfInput_pc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        sendeLabel.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        sendeLabel.setText("jLabel1");

        jScrollPane2.setViewportView(affichageMsg);

        btSend_pc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_paper_plane_50px_1.png"))); // NOI18N
        btSend_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSend_pcActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Male_User_64px_2.png"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_file_48px.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tfInput_pc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tfInput_pc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfInput_pcActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Blanc Bleu Icône de Flèche Voyage Logo (3) (2)_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfInput_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSend_pc, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sendeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(113, 113, 113))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(sendeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSend_pc)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tfInput_pc, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSend_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSend_pcActionPerformed
           try {
               // TODO add your handling code here:
               sendMessage();
           } catch (Exception ex) {
               Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
           }
    }//GEN-LAST:event_btSend_pcActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
openSendFileFrame();        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tfInput_pcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfInput_pcActionPerformed
           try {
               // TODO add your handling code here:
               sendMessage();
           } catch (Exception ex) {
               Logger.getLogger(chatPrive.class.getName()).log(Level.SEVERE, null, ex);
           }
    }//GEN-LAST:event_tfInput_pcActionPerformed
    private void sendMessage() throws Exception {
        String msg = tfInput_pc.getText();
        if(msg.equals("")) return;
        appendMessage_Right(msg);
        AES AES = new AES();
        SecretKey AESKey;
         AESKey =AES.getKey();
            System.out.println("hahia key d AES"+ AESKey);
            String encrypted_msg= AES.encryption(msg,AESKey);
        
        
        sendToServer("CMD_PRIVATECHAT|" + this.sender + "|" + this.receiver + "|" + msg);
        tfInput_pc.setText("");
        
        
        
        
    }
    
    private void openSendFileFrame() {
             envoyerFichier sendFileFrame = new envoyerFichier(serverHost, sender);
        
        sendFileFrame.thePersonIamChattingWith = receiver;
        sendFileFrame.getTfReceiver().setText(receiver);
        sendFileFrame.setVisible(true);
        sendFileFrame.setLocation(450, 250);
        sendFileFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chatPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chatPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chatPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new chatPrive().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane affichageMsg;
    private javax.swing.JButton btSend_pc;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel sendeLabel;
    private javax.swing.JTextField tfInput_pc;
    // End of variables declaration//GEN-END:variables
}
