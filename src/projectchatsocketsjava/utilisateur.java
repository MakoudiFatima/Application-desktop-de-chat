
package projectchatsocketsjava;


public class utilisateur {
    String name;
    String pass;
    private String room;

    public utilisateur(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public utilisateur(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    
    public void setRoom(String room) {
        this.room = room;
    }
    
    
}
