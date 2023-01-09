
package projectchatsocketsjava;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.Key;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JTextArea;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;


public class SocketServeur extends Thread {
    Socket socketOfServer;     
    BufferedWriter bw;
    BufferedReader br;
    String clientName, clientPass, clientRoom;
  //static Vector<ServerThread> ar = new Vector<>();
    public static Hashtable<String, SocketServeur> listUser = new Hashtable<>();
     public static Hashtable<String, SocketServeur> listUseroff = new Hashtable<>();
     public static Vector<String> as = new Vector<>();
     public static Vector<String> vec = new Vector<>();
     public static final String DESACTIVER_SUCCESS = "DESACTIVER successful!";
     public static  String NICKNAME_EXIST = "Ce pseudo est déjà connecté à un autre endroit ! Veuillez utiliser un autre pseudo";
    public static String NICKNAME_VALID = "Nom d'utilisateur est valide";
    public static  String NICKNAME_INVALID = "Nom d'utilisateur ou mot de passe est  incorrect";
    public static  String SIGNUP_SUCCESS = "L'inscription avec succés ! Merci";
    public static  String ACCOUNT_EXIST = "Ce nom a été utilisé ! Veuillez utiliser un autre nom !";
     public static final String EDITER_SUCCESS = "EDITER successful!";
     public JTextArea taServer;
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static Key DESKey;
    public String base64publicRSA;
    
    
    private static final HashMap<String, SecretKey> Keys = new HashMap<String, SecretKey>();
    public String encryptedKEYAES;
    StringTokenizer tokenizer;
    private final int BUFFER_SIZE = 1024;
    
    String senderName, receiverName;  
    static Socket senderSocket, receiverSocket;  
    baseDonnees userDB;
   
    
     
    /**
     *
     */
    private  SecretKey AESKey;
       
    static boolean isBusy = false; 
    
    public SocketServeur(Socket socketOfServer) throws Exception {
        this.socketOfServer = socketOfServer;
        this.bw = null;
        this.br = null;
        
        clientName = "";
        clientPass = "";
        clientRoom = "";
        
        userDB = new baseDonnees();
        userDB.connect();
        
        ///////////////////
        AES AES = new AES();
        AESKey =AES.getKey();
        System.out.println(clientName+"voici la key AES "+ AESKey);
       
     
       
        
    }
    
    public void appendMessage(String message) {
        taServer.append(message);
        taServer.setCaretPosition(taServer.getText().length() - 1);    
    }
    
    public String recieveFromClient() throws ClassNotFoundException, SQLException {
        try {
            return br.readLine();
            
                
                
            
        } catch (IOException ex) {
            
            System.out.println(clientName+" is disconnected!");
            
        }
       return null;
    }
    
    public void sendToClient(String response) {    
        try {
            bw.write(response);
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToSpecificClient(SocketServeur socketOfClient, String response) {     
        try {
            BufferedWriter writer = socketOfClient.bw;
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendToSpecificClient(Socket socket, String response) {    
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void notifyToAllUsers(String message) {
        Enumeration<SocketServeur> clients = listUser.elements();
        SocketServeur st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            writer = st.bw;

            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void notifyToUsersInRoom(String message) {
        Enumeration<SocketServeur> clients = listUser.elements();
        SocketServeur st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            if(st.clientRoom.equals(this.clientRoom)) {  
                writer = st.bw;

                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void notifyToUsersInRoom(String room, String message) {    
        Enumeration<SocketServeur> clients = listUser.elements();
        SocketServeur st;
        BufferedWriter writer;
        
        while(clients.hasMoreElements()) {
            st = clients.nextElement();
            if(st.clientRoom.equals(room)) {
                writer = st.bw;

                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException ex) {
                    Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void closeServerThread() {
        try {
            br.close();
            bw.close();
            socketOfServer.close();
        } catch (IOException ex) {
            Logger.getLogger(serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAllUsers() {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        
        Enumeration<String> keys = listUser.keys();
        if(keys.hasMoreElements()) {
            String str = keys.nextElement();
            kq.append(str);
        }
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            kq.append("|").append(temp);
        }
        
        return kq.toString();
    }
    
    public String getAllUsers1() {
        StringBuffer k = new StringBuffer();
        String temp = null;
        
        Enumeration<String> keys = listUseroff.keys();
        if(keys.hasMoreElements()) {
            String str = keys.nextElement();
            k.append(str);
        }
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            k.append("|").append(temp);
        }
        
        return k.toString();
    }
    public String getUsersThisRoom() {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        SocketServeur st;
        Enumeration<String> keys = listUser.keys();
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            st = listUser.get(temp);
            if(st.clientRoom.equals(this.clientRoom))  kq.append("|").append(temp);
        }
        
        if(kq.equals("")) return "|";
        return kq.toString();   
    }
    
    public String getUsersAtRoom(String room) {
        StringBuffer kq = new StringBuffer();
        String temp = null;
        SocketServeur st;
        Enumeration<String> keys = listUser.keys();
        
        while(keys.hasMoreElements()) {
            temp = keys.nextElement();
            st = listUser.get(temp);
            if(st.clientRoom.equals(room))  kq.append("|").append(temp);
        }
        
        if(kq.equals("")) return "|";
        return kq.toString();   
    }
    
    public void clientQuit() {
      
        if(clientName != null) {
            
            this.appendMessage("\n["+sdf.format(new Date())+"] Client \""+clientName+"\" is disconnected!");
            try {
                userDB.UpdateState1(clientName);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            listUser.remove(clientName);
            if(listUser.isEmpty()) this.appendMessage("\n["+sdf.format(new Date())+"] Now there's no one is connecting to server\n");
            notifyToAllUsers("CMD_ONLINE_USERS|"+getAllUsers());
            notifyToUsersInRoom("CMD_ONLINE_THIS_ROOM"+getUsersThisRoom());
            notifyToUsersInRoom(clientName+" has quitted");
        }
    }
    
    public void changeUserRoom() {   
        SocketServeur st = listUser.get(this.clientName);
        st.clientRoom = this.clientRoom;
        listUser.put(this.clientName, st);   
   
    }
    
    public void removeUserRoom() {
        SocketServeur st = listUser.get(this.clientName);
        st.clientRoom = this.clientRoom;
        listUser.put(this.clientName, st);
       
    }
    
    
    @Override
    public void run() {
        try {
            
            bw = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            
            boolean isUserExist = true;
            String message, sender, receiver, fileName;
            StringBuffer str;
            String cmd, icon;
            while(true) {   
                try {
                    message = recieveFromClient();
                    tokenizer = new StringTokenizer(message, "|");
                    cmd = tokenizer.nextToken();
                    
                    switch (cmd) {
                        case "CMD_CHAT":
                            str = new StringBuffer(message);
                            str = str.delete(0, 9);
                            notifyToUsersInRoom("CMD_CHAT|" + this.clientName+"|"+str.toString());    
                            break;
                            
                        case "CMD_PRIVATECHAT":
                            String privateSender = tokenizer.nextToken();
                            String privateReceiver = tokenizer.nextToken();
                            String messageContent = message.substring(cmd.length()+privateSender.length()+privateReceiver.length()+3, message.length());
                           
                            
                            SecretKey keyy = Keys.get(privateSender);
                            String encreptedSenderMsg = AES.encryption(messageContent, AESKey);
                            
                            
                            
                            
                            try {
                            userDB.StockerMessage(privateSender, privateReceiver,messageContent,"");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            SocketServeur st_receiver = listUser.get(privateReceiver);
                            //sendToSpecificClient(st_sender, "CMD_PRIVATECHAT|" + privateSender + "|" + messageContent);
                            sendToSpecificClient(st_receiver, "CMD_PRIVATECHAT|" + privateSender + "|" + encreptedSenderMsg+"|"+messageContent);
                            
                            System.out.println("[SocketServeur] message = "+messageContent);
                            System.out.println("[SocketServeur] message crépte = "+encreptedSenderMsg);
                             
                            
                            
                           
                           
                            
                            break;
                            
                            //**
                            
                             case "CMD_PUBLICRSA":
                           
                            senderName = tokenizer.nextToken();
                            base64publicRSA = tokenizer.nextToken();
                            
                           /* String AESkey2=tokenizer.nextToken();
                             byte[] decodedKey = Base64.getDecoder().decode(base64publicRSA);
                              SecretKey publicRSA = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
                             */
                            
                           System.out.println("le serveur a recoit la clé public de RSA");
                            
                           String AESkey64 = Base64.getEncoder().encodeToString(AESKey.getEncoded());
                           
                           encryptedKEYAES = Base64.getEncoder().encodeToString(RSA.encryptRSA(AESkey64, base64publicRSA));
                           
                            System.out.println("la clé AES AVEC LE CRYPTAGE DE RSA "+encryptedKEYAES);
                           
                            sendToClient("CMD_ENCREPTEDKEY|" + encryptedKEYAES);
                            
                            System.out.println("(la clé AES CRYPTé EST ENVOYER à LE CLIENT");
                              
                            
                            
                            break;
                            
                            
                            
                            case "CMD_DESACTIVER":
                                   
                            clientName = tokenizer.nextToken();
                           userDB.DESACTIVERUser(clientName);
                          sendToClient(DESACTIVER_SUCCESS);
  
               
                
                            
                            break;
                            case "CMD_PRIVATECHATT":
                            String privateSenderOff = tokenizer.nextToken();
                            String privateReceiverOff = tokenizer.nextToken();
                            String messageContentOff = message.substring(cmd.length()+privateSenderOff.length()+privateReceiverOff.length()+3, message.length());
                            try {
                            userDB.StockerMessage(privateSenderOff, privateReceiverOff,messageContentOff,"");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            SocketServeur st_receiver1 = listUseroff.get(privateReceiverOff);
                            //sendToSpecificClient(st_sender, "CMD_PRIVATECHAT|" + privateSender + "|" + messageContent);
                            sendToSpecificClient(st_receiver1, "CMD_PRIVATECHATT|" + privateSenderOff + "|" + messageContentOff);
                            
                            System.out.println("[ServerThread] message = "+messageContentOff);
                            break;
                            
                        case "CMD_ROOM":
                            clientRoom = tokenizer.nextToken();
                            changeUserRoom();
                            notifyToAllUsers("CMD_ONLINE_USERS|"+getAllUsers());
                            notifyToAllUsers("CMD_OFFLINE_USERS|"+getAllUsers1());
                            notifyToUsersInRoom("CMD_ONLINE_THIS_ROOM"+getUsersThisRoom());
                            notifyToUsersInRoom(clientName+" has just entered!");
                            break;
                            
                        case "CMD_LEAVE_ROOM":
                            String room = clientRoom;
                            clientRoom = "";    
                            removeUserRoom();
                            notifyToUsersInRoom(room, "CMD_ONLINE_THIS_ROOM"+getUsersAtRoom(room));
                            notifyToUsersInRoom(room, clientName+" has just leaved this room!");     
                            
                            break;
                            
                        case "CMD_CHECK_NAME":
                            clientName = tokenizer.nextToken();
                            clientPass = tokenizer.nextToken();
                            isUserExist = listUser.containsKey(clientName);
                            
                            if(isUserExist) {  
                                sendToClient(NICKNAME_EXIST);
                            }
                            else { 
                                int kq = userDB.checkUser(clientName, clientPass);
                                if(kq == 1) {
                                    sendToClient(NICKNAME_VALID);
                                   
                                    this.appendMessage("\n["+sdf.format(new Date())+"] Client \""+clientName+"\" is connecting to server");
                                    listUser.put(clientName, this);
                                    as=userDB.offlineUser();
                                    for(String name : as){
                                        listUseroff.put(name,this);
                                    }
                                    
                                    
                                           
                                    try{
                                        userDB.UpdateState(clientName);//methode d'updater
                                         
               
                                    } catch (Exception e) {
                                            System.out.println("ServerT:400 hihihihih");
                                      } 
                                    
                                     
                                   
                                } else sendToClient(NICKNAME_INVALID);
                            }
                            break;
                            
                        case "CMD_SIGN_UP":
                              String name = tokenizer.nextToken();
                            String pass = tokenizer.nextToken();
                            System.out.println("name, pass = "+name+" - "+pass);
                            vec=userDB.User();
                            
                            isUserExist = vec.contains(name);
                            
                            if(isUserExist) {
                                sendToClient(NICKNAME_EXIST);
                            } else {
                                int kq = userDB.insertUser(new utilisateur(name, pass));
                                if(kq > 0) {
                                    sendToClient(SIGNUP_SUCCESS);
                                    
                                } else sendToClient(ACCOUNT_EXIST);
                            }
                            break;
                            
                        case "CMD_ONLINE_USERS":
                            sendToClient("CMD_ONLINE_USERS|"+getAllUsers());
                           
                           
                            break;
                            
                             case "CMD_OFFLINE_USERS":
                                sendToClient("CMD_OFFLINE_USERS|"+getAllUsers1());
                                break;
                                ///////////////////////////////////////////////////////////////
                                ///////////////////////////////////////////////////////////////////////
                                ///////////////////////////////////////////////////////////////
                                ////////////////////////
                        case "CMD_EDITER":
                            clientName = tokenizer.nextToken();
                            String name1 = tokenizer.nextToken();
                            String pass2 = tokenizer.nextToken();
                            //String hc=tokenizer.nextToken();
                            //clientName;
                            System.out.print(clientName);
                           userDB.UPDATEUser(new utilisateur(name1, pass2),clientName);
                          sendToClient(EDITER_SUCCESS);
  
               
                
                            
                            break;
                        
                        case "CMD_SENDFILETOSERVER":    //the sender sends a file to server:
                            sender = tokenizer.nextToken();
                            receiver = tokenizer.nextToken();
                            fileName = tokenizer.nextToken();
                            int len = Integer.valueOf(tokenizer.nextToken());
                             System.out.println( fileName );
                            String path = System.getProperty("user.dir") + "\\sendfile\\" +fileName;
                            try {
                                userDB.StockerMessage(sender, receiver,"",fileName);
                               
                            } catch (Exception e) {
                                System.out.println("ServerT:400 hihihihih");
                            }

                            BufferedInputStream bis = new BufferedInputStream(socketOfServer.getInputStream());  
                            FileOutputStream fos = new FileOutputStream(path);  
                            
                            byte[] buffer = new byte[BUFFER_SIZE];
                            int count = -1;
                            while((count = bis.read(buffer)) > 0) {  
                                fos.write(buffer, 0, count);        
                            }

                            Thread.sleep(300);
                            bis.close();
                            fos.close();
                            socketOfServer.close();
                            
                        
                            SocketServeur stSender = listUser.get(sender);        
      
                            SocketServeur stReceiver = listUser.get(receiver);
                            sendToSpecificClient(stSender, "CMD_FILEAVAILABLE|"+fileName+"|"+receiver+"|"+sender);
                            sendToSpecificClient(stReceiver, "CMD_FILEAVAILABLE|"+fileName+"|"+sender+"|"+sender);

                            isBusy = false;
                            break;
                            
                        case "CMD_DOWNLOADFILE":    //server sends file to someone who just pressed download file
                            fileName = tokenizer.nextToken();
                            path = System.getProperty("user.dir") + "\\sendfile\\" + fileName;
                            FileInputStream fis = new FileInputStream(path);
                            BufferedOutputStream bos = new BufferedOutputStream(socketOfServer.getOutputStream());
                            
                            byte []buffer2 = new byte[BUFFER_SIZE];
                            int count2=0;
                            
                            while((count2 = fis.read(buffer2)) > 0) {
                                bos.write(buffer2, 0, count2);
                            }

                            bos.close();
                            fis.close();
                            socketOfServer.close();
                            
                            break;
                            
                        case "CMD_ICON":
                            icon = tokenizer.nextToken();
                            notifyToUsersInRoom("CMD_ICON|"+icon+"|"+this.clientName);
                            break;
                            
                        default:
                            notifyToAllUsers(message);
                            break;
                    } 
                    
                } catch (Exception e) {
                    clientQuit();
                    break;
                }
            }
        } catch (IOException ex) {
            clientQuit();
            Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        //this.closeServerThread();
        //this.closeServerThread();
        //this.closeServerThread();
        //this.closeServerThread();
    }

    
}