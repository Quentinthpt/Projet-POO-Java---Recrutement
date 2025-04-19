package Modele;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private int id_user;
    private String message;
    private boolean lu;
    private Timestamp date;


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getId_user() {return id_user;}
    public void setId_user(int id_user) {this.id_user = id_user;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public boolean isLu() {return lu;}
    public void setLu(boolean lu) {this.lu = lu;}

    public Timestamp getDate() {return date;}
    public void setDate(Timestamp date) {this.date = date;}

}
