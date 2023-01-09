
package projectchatsocketsjava;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.util.Vector;

public class baseDonnees {
    private Connection conn;
    public final String DATABASE_NAME = "chat_db";
    public final String USERNAME = "root";
    public final String PASSWORD = "";
    public final String URL_MYSQL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME+"?useSSL=false";
    static Vector<String> ar = new Vector<>();
    public final String USER_TABLE = "user_tb";
    
    private PreparedStatement pst;
    private ResultSet rs;
    private Statement st;
    
    
    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");     
//            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL_MYSQL, USERNAME, PASSWORD);
            System.out.println("Connect successfull");
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
      
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public ResultSet getData() {
        try {
            st = conn.createStatement(); 
            rs = st.executeQuery("SELECT * FROM "+USER_TABLE);
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    private void showData() {
        rs = getData();
        try {
            while(rs.next()) {
                System.out.printf("%-15s %-4s", rs.getString(1), rs.getString(2));
                System.out.println("");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public int DESACTIVERUser(String name )throws SQLException{
        pst = conn.prepareCall("UPDATE user_tb SET `state`='2' WHERE name='"+name+"'");
        pst.executeUpdate();
        
        return 0;
    }
       
    
    public int insertUser(utilisateur u) {
        System.out.println("Before: name = "+u.name+" - pass = "+u.pass);
        try {
            pst = conn.prepareCall("INSERT INTO `user_tb`(`name`, `pass`)  VALUES ('"+u.name+"', '"+u.pass+"')");
            int kq = pst.executeUpdate();
            if(kq > 0) System.out.println("Insert successful!");
            System.out.println("After: name = "+u.name+" - pass = "+u.pass);
            return kq;
//        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int UPDATEUser(utilisateur u,String name) throws SQLException {
        //String Username;
        //String a="1";
        pst = conn.prepareCall("UPDATE `user_tb` SET `state`='0',`pass`='"+u.pass+"'WHERE `name`='" + name +"'");
        pst.executeUpdate();
      
        return 0;}
    public int createUser(utilisateur u) {
        try {
            pst = conn.prepareStatement("INSERT INTO "+USER_TABLE+" VALUE(?,?);");
            pst.setString(1, u.name);
            pst.setString(2, u.pass);
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int checkUser(String name, String pass) {    //return 1 = account is correct
        try {
            pst = conn.prepareStatement("SELECT * FROM "+USER_TABLE+" WHERE name = '" + name + "' AND pass = '" + pass +"'");
            rs = pst.executeQuery();
            
            if(rs.first()) {
                //user and pass is correct:
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return 0;
    }
    
       private void LoadOldPrivateMessages(String sender, String receiver) {
        try {
         
            java.sql.Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_db?useSSL=false", "root", "");
            Statement st1 = conn1.createStatement();
            String fil1 = "SELECT `id_message`, `id_emetteur`, `id_recepteur`, `message_text`, `date` FROM `messages` WHERE `id_emetteur` = '"+sender+"' And  `id_recepteur`= '"+receiver+"'  ORDER BY `date` ASC";
            ResultSet rs1 = st1.executeQuery(fil1);
                while (rs1.next()) {
                String msgtxt = rs1.getString("message_text");
                String senderr = rs1.getString("id_emetteur");
                String reciever = rs1.getString("id_recepteur");
                
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }   
    
    public void closeConnection() {
        try {
            if(rs!=null) rs.close();
            if(pst!=null) pst.close();
            if(st!=null) st.close();
            if(conn!=null) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(baseDonnees.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[UserDatabase.java]  close connection");
        }}
  public void StockerMessage(String emetteur, String recepteur,String message,String Filename) throws ClassNotFoundException, SQLException {
        java.sql.Connection conn =  DriverManager.getConnection(URL_MYSQL,USERNAME,PASSWORD);
        Statement addh =  conn.createStatement();
          long millis=System.currentTimeMillis();  
         java.sql.Date date = new java.sql.Date(millis);  
        String stadd = "INSERT INTO chat_db.messages( `id_emetteur`, `id_recepteur`, `message_text`, `filename`, `date`) " +
                "VALUES ('"+emetteur+"', '"+ recepteur +"', '"+ message+"' ,'"+Filename+"','"+date+"')";
        addh.executeUpdate(stadd);
    }

        /*public void StockerMessage1(String emetteur, String recepteur, String Filename) throws ClassNotFoundException, SQLException {
        java.sql.Connection conn1 =  DriverManager.getConnection(URL_MYSQL,USERNAME,PASSWORD);
        Statement addh1 =  conn1.createStatement();
          long millis=System.currentTimeMillis();  
         java.sql.Date date = new java.sql.Date(millis);  
        String stadd1 = "INSERT INTO chat_db.messages( `id_emetteur`, `id_recepteur`, `filename`, `date`) " +
                "VALUES ('"+emetteur+"', '"+ recepteur +"','"+Filename+"','"+date+"')";
        addh1.executeUpdate(stadd1);
    }*/
        public void UpdateState(String username) throws ClassNotFoundException, SQLException {
        java.sql.Connection conn1 =  DriverManager.getConnection(URL_MYSQL,USERNAME,PASSWORD);
        Statement addh1 =  conn1.createStatement();
          long millis=System.currentTimeMillis();  
         java.sql.Date date = new java.sql.Date(millis);  
        String stadd1 = "UPDATE user_tb SET `state`='1' where `name`='"+ username +"' ";
        addh1.executeUpdate(stadd1);
    }
        
        
        
        
        
             public Vector<java.lang.String> offlineUser() throws ClassNotFoundException, SQLException {
                 try {
        pst = conn.prepareStatement("SELECT `name` FROM user_tb where `state`='0'");
            rs = pst.executeQuery();
       
        
    
       
      //addh1.executeUpdate(stadd1);
                 
                    
        
        while(rs.next()){
  
            ar.add(rs.getString ("name"));
        }
                 } catch (Exception e) {
                     System.out.println("erreur");
                 }
                 
                 
                   
                    
      return ar;
    }   
   //
           
             
             
             
       public void UpdateState1(String username) throws ClassNotFoundException, SQLException {
        java.sql.Connection conn1 =  DriverManager.getConnection(URL_MYSQL,USERNAME,PASSWORD);
        Statement addh1 =  conn1.createStatement();
          long millis=System.currentTimeMillis();  
         java.sql.Date date = new java.sql.Date(millis);  
        String st = " UPDATE `user_tb` SET `state`='0' WHERE `name`='"+ username +"' ";
        addh1.executeUpdate(st);
    }
       
       
       
       
       static Vector<String> users = new Vector<>();
            public Vector<java.lang.String> User() throws ClassNotFoundException, SQLException {
                 try {
        pst = conn.prepareStatement("SELECT `name` FROM user_tb");
            rs = pst.executeQuery();
       
        
    
       
      //addh1.executeUpdate(stadd1);
                 
                    
        
        while(rs.next()){
  
            users.add(rs.getString ("name"));
        }
                 } catch (Exception e) {
                     System.out.println("erreur");
                 }
                 
                 
                   
                    
      return users;
    }
             
             
             
        
        
}

