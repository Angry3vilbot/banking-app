public class UserSession {
    private static UserSession instance;
    private User user;

    // Private constructor (Singleton)
    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Clear session data, used after a user logs out
    public void clearSession() {
        user = null;
    }
}
