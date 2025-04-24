package Modele;

public class SessionManager {
    private static SessionManager instance;
    private int currentUserId;
    private String userType; // "demandeur" ou "admin"
    private boolean isLoggedIn;

    // Constructeur privé pour Singleton
    private SessionManager() {
        resetSession();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(int userId, String userType) {
        this.currentUserId = userId;
        this.userType = userType;
        this.isLoggedIn = true;
    }

    public void logout() {
        resetSession();
    }

    private void resetSession() {
        this.currentUserId = -1;
        this.userType = null;
        this.isLoggedIn = false;
    }

    // Getters
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public String getUserType() {
        return userType;
    }
}