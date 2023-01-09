
package projectchatsocketsjava;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import view.ClientPanel;
import projectchatsocketsjava.Login;
import projectchatsocketsjava.chatPrive;
import projectchatsocketsjava.OnlineOfflineContacts;


import projectchatsocketsjava.accueil;
import projectchatsocketsjava.SignUp;


public class SocketClient extends JFrame implements Runnable {
    String serverHost;
   public static  String NICKNAME_EXIST = "Ce pseudo est déjà connecté à un autre endroit ! Veuillez utiliser un autre pseudo";
    public static String NICKNAME_VALID = "Nom d'utilisateur est valide";
    public static  String NICKNAME_INVALID = "Nom d'utilisateur ou mot de passe est  incorrect";
    public static  String SIGNUP_SUCCESS = "L'inscription avec succés ! Merci";
    public static  String ACCOUNT_EXIST = "Ce nom a été utilisé ! Veuillez utiliser un autre nom !";
    private static String Base64Private;
      private static String Base64PUBLIC;
    private static SecretKey originalKey;
    private static SecretKey originalKeyy;
     HashMap<String, String> Base64Public = new HashMap<>();
     private static String decryptedString;
     private static String AESSTRING;
      private static Key DESKey ;
      
     
      String AESKey2;
      private static SecretKey decodedKeyy,AESKey;
    String name;
    String room;
    Socket socketOfClient;
    BufferedWriter bw;
    BufferedReader br;
    
    JPanel mainPanel;
    Login loginPanel;
    //ClientPanel clientPanel;
    accueil welcomePanel;
    SignUp signUpPanel;
    OnlineOfflineContacts roomPanel;
    chatPrive PrivateChat;
    Désactiver desactiverPanel;
    
     Gestion gestionPanel;
    GestionUser gestioninfoPanel;
    Thread clientThread;
    boolean isRunning;
    
    JMenuBar menuBar;
    JMenu menuShareFile;
    JMenuItem itemSendFile;
    JMenu menuAccount;
    JMenuItem itemLeaveRoom, itemLogout, itemChangePass;
    
    envoyerFichier sendFileFrame;
    
    StringTokenizer tokenizer;
    String myDownloadFolder;
    
    Socket socketOfSender, socketOfReceiver;
    
    DefaultListModel<String> listModel, listModelThisRoom, listModel_rp,listModel1,listModel_rp1;
        
    boolean isConnectToServer;
    
    int timeClicked = 0;   
    
    Hashtable<String, chatPrive> listReceiver;
    
    public SocketClient(String name)  {
        this.name = name;
         
        socketOfClient = null;
        bw = null;
        br = null;
        isRunning = true;
        listModel = new DefaultListModel<>();
         listModel1 = new DefaultListModel<>();
        listModelThisRoom = new DefaultListModel<>();
        listModel_rp = new DefaultListModel<>();
         listModel_rp1 = new DefaultListModel<>();
        isConnectToServer = false;
        listReceiver = new Hashtable<>();
        mainPanel = new JPanel();
        loginPanel = new Login();
        //clientPanel = new ClientPanel();
        gestionPanel=new Gestion();
        desactiverPanel=new Désactiver();
        gestioninfoPanel=new GestionUser();
        welcomePanel = new accueil();
        signUpPanel = new SignUp();
        roomPanel = new OnlineOfflineContacts();
        PrivateChat =new chatPrive();
        //mainPanel.setSize(570, 450);
        welcomePanel.setVisible(true);
        signUpPanel.setVisible(false);
        loginPanel.setVisible(false);
        roomPanel.setVisible(false);
        //clientPanel.setVisible(false);
       
        mainPanel.add(welcomePanel);
        mainPanel.add(signUpPanel);
        mainPanel.add(loginPanel);
        mainPanel.add(roomPanel);
        //mainPanel.add(clientPanel);
        
        addEventsForWelcomePanel();
        addEventsForSignUpPanel();
        addEventsForLoginPanel();
        addEventsForRoomPanel();
        addEventsForGestionPanel();
        addEventsForModifierPanel();
        addEventsForDesactiverPaanel();
        menuBar = new JMenuBar();   
        menuShareFile = new JMenu();    
        menuAccount = new JMenu();
        itemLeaveRoom = new JMenuItem();
        itemLogout = new JMenuItem();
        itemChangePass = new JMenuItem();
        itemSendFile = new JMenuItem();    
        
        menuAccount.setText("Account");
        itemLogout.setText("Logout");
        itemLeaveRoom.setText("Leave room");
        itemChangePass.setText("Change password");
        menuAccount.add(itemLeaveRoom);
        menuAccount.add(itemChangePass);
        menuAccount.add(itemLogout);
        
        menuShareFile.setText("File sharing");
        itemSendFile.setText("Send a file");
        menuShareFile.add(itemSendFile);
        
        menuBar.add(menuAccount);
        menuBar.add(menuShareFile);
       
      
      
        itemChangePass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(SocketClient.this, "", "", JOptionPane.ERROR_MESSAGE);
            }
        });
        itemLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int kq = JOptionPane.showConfirmDialog(SocketClient.this, "Vous confirmer votre déconnexion?", "Notice", JOptionPane.YES_NO_OPTION);
                if(kq == JOptionPane.YES_OPTION) {
                    try {
                        isConnectToServer = false;
                        socketOfClient.close();
                        SocketClient.this.setVisible(false);
                    } catch (IOException ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    new SocketClient(null).setVisible(true);
                }
            }
        });
        itemSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //openSendFileFrame();
                JOptionPane.showMessageDialog(SocketClient.this, "Veuillez essayer dans la partie chat", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.setVisible(false);
        
        setJMenuBar(menuBar);
        pack();
        
        add(mainPanel);
        setSize(570, 520);
        setLocation(400, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(name);
    }
private void addEventsForModifierPanel(){
       gestioninfoPanel.getexit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
                 welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(false);
                gestionPanel.setVisible(true);
               gestioninfoPanel.setVisible(false);
            
                
            
            }
        
        });
         
     
        
        gestioninfoPanel.getBtConfirmer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 
                try {
                    btConfirmer();
                    isConnectToServer = false;
                    socketOfClient.close();
                    welcomePanel.setVisible(true);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
                
                roomPanel.setVisible(false);
               gestionPanel.setVisible(false);
               gestioninfoPanel.setVisible(false);
                } catch (IOException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
               
                     
                  
            }
        });
      
       //gestioninfoPanel .getBtConfirmer().addActionListener(new ActionListener() {
         ///   @Override
          //  public void actionPerformed(ActionEvent ae) {
             
            //   welcomePanel.setVisible(false);
           //     signUpPanel.setVisible(false);
            //    loginPanel.setVisible(false);
            //    clientPanel.setVisible(false);
             //   roomPanel.setVisible(true);
            //   gestionPanel.setVisible(false);
              // gestioninfoPanel.setVisible(true);
          //  }
       // });
       
    }



 private void addEventsForDesactiverPaanel() {
       //To change body of generated methods, choose Tools | Templates.
       desactiverPanel.getBtoui() .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               try {
                   // welcomePanel.setVisible(true);
                   
                    isConnectToServer = false;
                    socketOfClient.close();
                    welcomePanel.setVisible(true);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
                desactiverPanel.setVisible(false);
                roomPanel.setVisible(false);
               gestionPanel.setVisible(false);
               gestioninfoPanel.setVisible(false);
                 btoui();
                } catch (IOException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
         
       desactiverPanel.getBtnon().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(false);
                gestionPanel.setVisible(true);
               gestioninfoPanel.setVisible(false);

            }
        } );
       desactiverPanel .getexit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
                 welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(false);
                gestionPanel.setVisible(true);
               gestioninfoPanel.setVisible(false);
            
                
            
            }
        
        });
       
       
    }
      private void btoui() {
         
           
          String name = loginPanel.getTfNickname().getText().trim();
            String hostName = "localhost";
              if(!isConnectToServer) {
                isConnectToServer = true;   
                this.connectToServer(hostName);
            } 
            this.sendToServer("CMD_DESACTIVER|"+name ); 
            
           // String response = this.recieveFromServer();
          
           
        
        welcomePanel.setVisible(true);
     
        
       }

    private void btConfirmer() {
           
           
        
      
            String pass3 = this.gestioninfoPanel.getTfPass().getText();
            String n = loginPanel.getTfNickname().getText().trim();   
            System.out.println(n);

            String nickname3 = gestioninfoPanel.getTfID().getText().trim();
            String hostName = "localhost";
              if(!isConnectToServer) {
                isConnectToServer = true;  
                this.connectToServer(hostName);
            } 
            this.sendToServer("CMD_EDITER|"+n+"|" +nickname3+"|"+pass3); 
            
           // String response = this.recieveFromServer();
          
       
        }
    
    private void addEventsForWelcomePanel() {
        
        welcomePanel.getBtLogin_welcome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(true);
                //clientPanel.setVisible(false);
                roomPanel.setVisible(false);
            }
        });
        welcomePanel.getBtSignUp_welcome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                welcomePanel.setVisible(false);
                
                signUpPanel.setVisible(true);
                loginPanel.setVisible(false);
                //clientPanel.setVisible(false);
                roomPanel.setVisible(false);
            }
        });
        
    }

    private void addEventsForLoginPanel() {
        loginPanel.getTfNickname().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER) try {
                    btOkEvent();
                } catch (Exception ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        loginPanel.getTfPass().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER) try {
                    btOkEvent();
                } catch (Exception ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        loginPanel.getBtOK().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    btOkEvent();
                } catch (Exception ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        loginPanel.getLbBack_login().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                welcomePanel.setVisible(true);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
                //clientPanel.setVisible(false);
                roomPanel.setVisible(false);
            }
        });
    }
private void addEventsForGestionPanel(){
        gestionPanel.getBtModifiervosinfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(false);
                gestionPanel.setVisible(true);
               gestioninfoPanel.setVisible(true);

            }
        } );
        gestionPanel .getexit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
                 welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(true);
                gestionPanel.setVisible(false);
               gestioninfoPanel.setVisible(false);
                desactiverPanel.setVisible(false);
            
                
            
            }
        
        });
                
                
                
                
                
                
                
                
        gestionPanel.getBtDESACTIVER().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of generated methods, choose Tools | Templates.
                welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               
                roomPanel.setVisible(false);
                gestionPanel.setVisible(false);
               gestioninfoPanel.setVisible(false);
              desactiverPanel.setVisible(true);

            }
        } );
        
    
    }

    
    private void addEventsForRoomPanel() {
 roomPanel.getBtGestionCOMPTE().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                welcomePanel.setVisible(false);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
                roomPanel.setVisible(true);
                gestionPanel.setVisible(true);
            }
        });
        
        roomPanel.getOnlineList_rp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                openPrivateChatOutsideRoom();
                
            }
            
        }
        
        
        );
        
        roomPanel.getOfflineList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                openPrivateChatOutsideRoom1();
                
            }
            
        }
                
        
        
        );
        
     roomPanel.getexit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                
                int kq = JOptionPane.showConfirmDialog(SocketClient.this, "Vous confirmer cotre deconnexion?", "Notice", JOptionPane.YES_NO_OPTION);
                if(kq == JOptionPane.YES_OPTION) {
                    try {
                        isConnectToServer = false;
                        socketOfClient.close();
                        SocketClient.this.setVisible(false);
                      
                    } catch (IOException ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    new SocketClient(null).setVisible(true);
                }
            
                
            
            }
        
        }
        
        );   
           
    }
    
     private void addEventsForSignUpPanel() {
        signUpPanel.getLbBack_signup().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                welcomePanel.setVisible(true);
                signUpPanel.setVisible(false);
                loginPanel.setVisible(false);
               //clientPanel.setVisible(false);
                roomPanel.setVisible(false);
            }
        });
        signUpPanel.getBtSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btSignUpEvent();
            }
        });
    }
    
   /* private void addEventsForChatPanel() {
 
        
        PrivateChat.getOnlineList_rp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                openPrivateChatOutsideRoom();
            }
            
        }
        
        
        );
        
           
    }*/
         /////////////////////////////////////////
            public void KeysRSA() throws Exception {
        try {
           
            
            RSA keyPairGenerator = new RSA();
            //coding pulic key de Rsa en 64
            Base64PUBLIC = Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded());
            Base64Private = Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded());
            
          
            
            
            System.out.println("les deux keys de RSA sont crée");
            
            sendToServer("CMD_PUBLICRSA|"+name+"|"+Base64PUBLIC );
            System.out.println(" la clé  public de RSA a été envoyé au server \n");
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
         
    private void openPrivateChatOutsideRoom() {
        timeClicked++;
        if(timeClicked == 1) {
            Thread countingTo500ms = new Thread(counting);
            countingTo500ms.start();
        }

        if(timeClicked == 2) {  
            String privateReceiver = roomPanel.getOnlineList_rp().getSelectedValue();
            chatPrive pc = listReceiver.get(privateReceiver);
            if(pc == null) {   
                pc = new chatPrive(name, privateReceiver, serverHost, bw, br);
               // LoadOldPrivateMessages(name,privateReceiver);
              JLabel label = new JLabel("active");
              pc.add(label);
try {
            java.sql.Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306"
                    + "/chat_db?useSSL=false", "root", "");
            Statement st1 = conn1.createStatement();
            String fil1 = "SELECT `id_message`, `id_emetteur`, `id_recepteur`, `message_text`, `date`,`filename` FROM `messages` WHERE `id_emetteur` = '"+name+"' And  `id_recepteur`= '"+privateReceiver+"' OR  `id_emetteur`=  '"+privateReceiver+"' AND `id_recepteur`='"+name+"' ORDER BY `date` ASC";
            ResultSet rs1 = st1.executeQuery(fil1);
                while (rs1.next()) {
                String msgtxt = rs1.getString("message_text");
                 String file = rs1.getString("filename");
                String senderr = rs1.getString("id_emetteur");
                String reciever = rs1.getString("id_recepteur");
               
                       
                
                       if (senderr.equals(this.name)){
                           if ( !file.equals("")){
                       
                               pc.insertButtonRight(senderr, file);
                       }else{
                                pc.appendMessage_Right("", msgtxt);
                               
                           }
                }else{
                        if ( !file.equals("")){
                            pc.insertButton(senderr, file);

                       }else{
                              pc.appendMessage_Left("", msgtxt);
                           }
                                }}
        } catch (Exception e1) {
            e1.printStackTrace();
        }
               
                pc.getLbReceiver().setText(""+pc.receiver+"");
                pc.setTitle(pc.receiver);
                pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pc.setVisible(true);
                 //LoadOldPrivateMessages(name,privateReceiver);
                listReceiver.put(privateReceiver, pc);
                
            } else {
                pc.setVisible(true);
            }
        }
    }
    
    
    
    
    
     private void openPrivateChatOutsideRoom1() {
        timeClicked++;
        if(timeClicked == 1) {
            Thread countingTo500ms = new Thread(counting);
            countingTo500ms.start();
        }

        if(timeClicked == 2) {  
            String privateReceiver = roomPanel.getOfflineList().getSelectedValue();
            chatPrive pc = listReceiver.get(privateReceiver);
            if(pc == null) {   
                pc = new chatPrive(name, privateReceiver, serverHost, bw, br);
               // LoadOldPrivateMessages(name,privateReceiver);
               
               
               
               try {
            java.sql.Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_db?useSSL=false", "root", "");
            Statement st1 = conn1.createStatement();
            String fil1 = "SELECT `id_message`, `id_emetteur`, `id_recepteur`, `message_text`, `date`,`filename` FROM `messages` WHERE `id_emetteur` = '"+name+"' And  `id_recepteur`= '"+privateReceiver+"' OR  `id_emetteur`=  '"+privateReceiver+"' AND `id_recepteur`='"+name+"' ORDER BY `date` ASC";
            ResultSet rs1 = st1.executeQuery(fil1);
                while (rs1.next()) {
                String msgtxt = rs1.getString("message_text");
                 String file = rs1.getString("filename");
                String senderr = rs1.getString("id_emetteur");
                String reciever = rs1.getString("id_recepteur");
                       if (senderr.equals(this.name)){
                           if ( !file.equals("")){
                       
                               pc.insertButtonRight(senderr, file);
                       }else{
                                pc.appendMessage_Right("", msgtxt);
                               
                           }
                }else{
                        if ( !file.equals("")){
                            pc.insertButtonLeft(senderr, file);

                       }else{
                              pc.appendMessage_Left("", msgtxt);
                           }
                                }}
        } catch (Exception e1) {
            e1.printStackTrace();
        }
               
                pc.getLbReceiver().setText(""+pc.receiver+"");
                pc.setTitle(pc.receiver);
                pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pc.setVisible(true);
                 //LoadOldPrivateMessages(name,privateReceiver);
                listReceiver.put(privateReceiver, pc);
                
            } else {
                pc.setVisible(true);
            }
        }
    }
//    private PrivateChat createNewPC() {    //create new PrivateChat Frame
//        PrivateChat pc = new PrivateChat
//    }
    
    Runnable counting = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeClicked = 0;
        }
    };
    
  
    
    ////////////////////////Events////////////////////////////
     private void btOkEvent() throws Exception {
        String hostname = "localhost";
        String nickname = loginPanel.getTfNickname().getText().trim();
        String pass = loginPanel.getTfPass().getText().trim();
        
        this.serverHost = hostname;
        this.name = nickname;
        
        if(nickname.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(this, "Pour se bénéficier de notre service Mex, Veuillez remplir tout le formulaire", "Notice!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!isConnectToServer) {
            isConnectToServer = true;   //ce qui signifie que lors de l'exécution de ce fichier ClientFrame.java, il ne se connecte qu'une seule fois au serveur,
                                         // alors vous n'avez plus besoin de vous connecter car vous avez déjà une connexion au serveur. Si vous continuez à vous connecter plusieurs fois (en raison de la saisie d'un mauvais compte)
                                         // créera de nombreuses sockets pour se connecter au serveur à chaque fois que je cliquerai sur OK, puis la socket se fermera arbitrairement, entraînant une erreur !
            this.connectToServer(hostname);
        }    
        this.sendToServer("CMD_CHECK_NAME|" +this.name+"|"+pass);      

        String response = this.recieveFromServer();
        if(response != null) {
            if (response.equals(NICKNAME_EXIST) || response.equals(NICKNAME_INVALID)) {
                JOptionPane.showMessageDialog(this, response, "Error", JOptionPane.ERROR_MESSAGE);
                //loginPanel.getBtOK().setText("OK");
            } else {
                
                loginPanel.setVisible(false);
                roomPanel.setVisible(true);

                this.setTitle("\""+name+"\"");

                menuBar.setVisible(true);

               
                clientThread = new Thread(this);
                clientThread.start();
                this.sendToServer("CMD_ROOM|"+this.room);     
                 
                System.out.println("this is \""+name+"\"");
                //loginPanel.getBtOK().setText("OK");
                KeysRSA();
            }
        } else System.out.println("[btOkEvent()] Server is not open yet, or already closed!");
    }
    
       private void btSignUpEvent() {
        String pass = this.signUpPanel.getTfPass().getText();
        String pass2 = this.signUpPanel.getTfPass2().getText();
        if(!pass.equals(pass2)) {
            JOptionPane.showMessageDialog(this, "Mots de passe incompatible", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String nickname = signUpPanel.getTfID().getText().trim();
            String hostName = "localhost";
            if( nickname.equals("") || pass.equals("") || pass2.equals("")) {
                JOptionPane.showMessageDialog(this, "Pour se bénéficier de notre service MEX, veuillez remplir les champs du formulaire", "Notice!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!isConnectToServer) {
                isConnectToServer = true;   
                this.connectToServer(hostName); 
            }    
            this.sendToServer("CMD_SIGN_UP|" +nickname+"|"+pass);     
        
            String response = this.recieveFromServer();
            if(response != null) {
                if(response.equals(NICKNAME_EXIST) || response.equals(ACCOUNT_EXIST)) {
                    JOptionPane.showMessageDialog(this, response, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, response+"\nVous pouvez communiquer dés maintenant grace a notre service MEX !!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    signUpPanel.clearTf();
                }
            }
        }
       }
            
   /* private void btSendEvent() {
        String message = clientPanel.getTaInput().getText().trim();
        if(message.equals("")) clientPanel.getTaInput().setText("");
        else {
            this.sendToServer("CMD_CHAT|" + message);       //gửi data tới server
            this.btClearEvent();
        }
       
    }*/

    private void btClearEvent() {
      //  clientPanel.getTaInput().setText("");
    }

    private void btExitEvent() {
        try {
            isRunning = false;
            //this.disconnect();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    private void openSendFileFrame() {
//        sendFileFrame = new SendFileFrame();
//        
//        //gửi tất cả thông tin của client này sang frame đó:
//        //sendFileFrame.name = this.name;
//        //sendFileFrame.socketOfClient = this.socketOfClient;
////        sendFileFrame.bw = this.bw;
////        sendFileFrame.br = this.br;
//        
//        sendFileFrame.setVisible(true);
//        sendFileFrame.setLocation(450, 250);
//        sendFileFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//    }
    
    ////////////////////////End of Events////////////////////////////   
    
    public void connectToServer(String hostAddress) {  
        try {
            socketOfClient = new Socket(hostAddress, 9999);
            bw = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            
        } catch (java.net.UnknownHostException e) {
            JOptionPane.showMessageDialog(this, "Host IP is not correct.\nPlease try again!", "Failed to connect to server", JOptionPane.ERROR_MESSAGE);
        } catch (java.net.ConnectException e) {
            JOptionPane.showMessageDialog(this, "Server is unreachable, maybe server is not open yet, or can't find this host.\nPlease try again!", "Failed to connect to server", JOptionPane.ERROR_MESSAGE);
        } catch(java.net.NoRouteToHostException e) {
            JOptionPane.showMessageDialog(this, "Can't find this host!\nPlease try again!", "Failed to connect to server", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            
        }
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
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String recieveFromServer() {
        try {
            return this.br.readLine();  
        } catch (java.lang.NullPointerException e) {
            System.out.println("[recieveFromServer()] Server is not open yet, or already closed!");
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void disconnect() {
        System.out.println("disconnect()");
        try {
            if(br!=null) this.br.close();
            if(bw!=null) this.bw.close();
            if(socketOfClient!=null) this.socketOfClient.close();
           
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static String getPrivateKeyRSA() {
        return Base64Private;
    }

    public static SecretKey getKey() {
        return originalKeyy;
    }
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        SocketClient client = new SocketClient(null);
        client.setVisible(true);
    }
  /*  private void LoadOldPrivateMessages(String sender, String receiver) {
        try {
         
            java.sql.Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_db", "root", "");
            Statement st1 = conn1.createStatement();
            String fil1 = "SELECT `id_message`, `id_emetteur`, `id_recepteur`, `message_text`, `date` FROM `messages` WHERE `id_emetteur` = '"+name+"' And  `id_recepteur`= '"+sender+"'  ORDER BY `date` ASC";
            ResultSet rs1 = st1.executeQuery(fil1);
                while (rs1.next()) {
                String msgtxt = rs1.getString("message_text");
                String senderr = rs1.getString("id_emetteur");
                String reciever = rs1.getString("id_recepteur");
                       if (senderr.equals(this.name))
                                        PrivateChat.appendMessage_Right(senderr + ": ", msgtxt);
                                    else
                                        PrivateChat.appendMessage_Left(senderr + ": ", msgtxt);
                                }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }   */

    @Override
    public void run() {
        String response;
        String sender, receiver, fileName, thePersonIamChattingWith, thePersonSendFile;
        String msg;
        String cmd;
        chatPrive pc;
        
        
        while(isRunning) {
            response = this.recieveFromServer(); 
            tokenizer = new StringTokenizer(response, "|");
            cmd = tokenizer.nextToken();
            switch (cmd) {
               case "CMD_ENCREPTEDKEY":
                       
                        String AESSTRING = tokenizer.nextToken();
                         
                        byte[] decodedKeyyy = Base64.getDecoder().decode(AESSTRING);
                        decodedKeyy = new SecretKeySpec(decodedKeyyy, 0, decodedKeyyy.length, "AES");
                       
                       
                        //System.out.println("key encrepted : " + encryptedKEYAES);
                        String base64private = getPrivateKeyRSA();
                        System.out.println(" GETTING RSA PRIVATE KEY : " + base64private);
                        
                {
                    try {
                        decryptedString = RSA.decryptRSA(AESSTRING, base64private);
                    } catch (Exception ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                            
                          try {  
                            byte[] decodedKey = Base64.getDecoder().decode(decryptedString);
                            originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                            System.out.println(" VOICI LA CLé AES : " + originalKey);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                       
                        break;

 
                case "CMD_PRIVATECHAT":   
                    //System.out.println(originalKeyy);
                    sender = tokenizer.nextToken();
                    String encrepthMsg = tokenizer.nextToken();
                    msg = tokenizer.nextToken();
                    
                     
                     System.out.println("Le message crypté "+encrepthMsg);
                {
                    try {
                        //***********************************************************
                       String mdecryptemsg= AES.decryption(encrepthMsg, originalKey);
                       
                        System.out.println("voici le message décrypté"+mdecryptemsg);
                    } catch (Exception ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                     
                    pc = listReceiver.get(sender);
                    
                    if(pc == null) {
                        pc = new chatPrive(name, sender, serverHost, bw, br);
                        pc.sender = name;
                        pc.receiver = sender;
                        pc.serverHost = this.serverHost;
                        pc.bw = SocketClient.this.bw;
                        pc.br = SocketClient.this.br;
                        pc.getLbReceiver().setText(""+pc.receiver+"\"");
                        pc.setTitle(pc.receiver);
                        pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        pc.setVisible(true);   
                        listReceiver.put(sender, pc);
                    } else {
                        pc.setVisible(true);
                    }
                    //pc.appendMessage(sender+": ", msg, Color.CYAN, Color.GREEN);
                    pc.appendMessage_Left(" ", msg);
                    //LoadOldPrivateMessages(sender,name);
                    break;

                    
                    
                    //***
                    
                    case "CMD_PRIVATECHATT":    
                    sender = tokenizer.nextToken();
                    msg = response.substring(cmd.length()+sender.length()+2, response.length());
                     /* String decrepthMsgT = "";
                    try {
                            decrepthMsgT = AES.decrypt(msg, originalKey);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                    pc = listReceiver.get(sender);
                    
                    if(pc == null) {
                        pc = new chatPrive(name, sender, serverHost, bw, br);
                        pc.sender = name;
                        pc.receiver = sender;
                        pc.serverHost = this.serverHost;
                        pc.bw = SocketClient.this.bw;
                        pc.br = SocketClient.this.br;

                        pc.getLbReceiver().setText(""+pc.receiver+"\"");
                        pc.setTitle(pc.receiver);
                        pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        pc.setVisible(true);   

                        listReceiver.put(sender, pc);
                    } else {
                        pc.setVisible(true);
                    }
                    //pc.appendMessage(sender+": ", msg, Color.CYAN, Color.GREEN);
                    pc.appendMessage_Left(" ", msg);
                    //LoadOldPrivateMessages(sender,name);
                    break;
                    
                    
                    
                case "CMD_ONLINE_USERS":
                    listModel.clear();
                    
                   // listModel_rp.clear();
                    while(tokenizer.hasMoreTokens()) {
                        cmd = tokenizer.nextToken();
                       // listModel.addElement(cmd);
                        listModel_rp.addElement(cmd);
                         
                    }
                    //clientPanel.getOnlineList().setModel(listModel);
                    listModel_rp.removeElement(this.name);
                    roomPanel.getOnlineList_rp().setModel(listModel_rp);
                    //PrivateChat.getOnlineList_rp().setModel(listModel_rp);
                    break;
                    
                     
                    //****************
                   case "CMD_OFFLINE_USERS":
                    //clientPanel.getOnlineList().setModel(listModel);
                        listModel1.clear();
                        listModel_rp1.clear();
                    
                     while(tokenizer.hasMoreTokens()) {
                        cmd = tokenizer.nextToken();
                       // listModel.addElement(cmd);
                        listModel_rp1.addElement(cmd);
                         
                    }
                    listModel_rp1.removeElement(this.name);
                    roomPanel.getOfflineList().setModel(listModel_rp1);
                    //PrivateChat.getOfflineList().setModel(listModel_rp);
                break;
                
                
                //***
                
                
               
          
//                case "CMD_SERVERISBUSY":
//                    JOptionPane.showMessageDialog(this, "Server is busy, please try to send file later", "Info", JOptionPane.INFORMATION_MESSAGE);
//                    break;
                    
                case "CMD_FILEAVAILABLE":
                    System.out.println("file available");
                    fileName = tokenizer.nextToken();
                    thePersonIamChattingWith = tokenizer.nextToken();
                    thePersonSendFile = tokenizer.nextToken();
                    
                    pc = listReceiver.get(thePersonIamChattingWith);
                    
                    if(pc == null) {
                        sender = this.name;
                        receiver = thePersonIamChattingWith;
                        pc = new chatPrive(sender, receiver, serverHost, bw, br);
                        
                        pc.getLbReceiver().setText(""+pc.receiver+"\"");
                        pc.setTitle(pc.receiver);
                        pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
                        listReceiver.put(receiver, pc);
                    }
                    
                    pc.setVisible(true);    
                    //pc.insertButton(thePersonSendFile, fileName);
                    if(thePersonSendFile.equals(this.name)){
                     pc.insertButtonRight(thePersonSendFile, fileName);
                    }else
                    pc.insertButtonLeft(thePersonSendFile, fileName);
            
                    break;
                    

                    
                default:
                    if(!response.startsWith("CMD_")) {    
                        if(response.equals("Warnning: Server has been closed!")) {
                            
                        }
                        
                    }
                    
                    
            }
        }
        System.out.println("Disconnected to server!");
    }


}
