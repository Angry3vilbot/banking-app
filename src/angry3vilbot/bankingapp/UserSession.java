package angry3vilbot.bankingapp;

/**
 * UserSession class to manage user session data.
 * This class is implemented as a Singleton to ensure that
 * only one instance of the session exists throughout the application.
 * It stores the user information and provides methods to
 * get and set the user.
 * It also provides a method to clear the session data
 * when the user logs out.
 */
public class UserSession {
    /**
     * The singleton instance of UserSession.
     */
    private static UserSession instance;
    /**
     * The {@link User} object that contains user information.
     */
    private User user;

    /**
     * Private constructor to prevent instantiation
     */
    // Private constructor (Singleton)
    private UserSession() {}

    /**
     * Get the singleton instance of UserSession
     * This method is synchronized to ensure thread safety
     * when accessing the singleton instance.
     * @return the singleton instance of UserSession
     */
    public static synchronized UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets the {@link User} object that represents the current user
     * @return the {@link User} object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the new {@link User} object
     * @param user the {@link User} object to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Clears the session data
     * This method is used after a user logs out
     * to clear the user information
     * from the session.
     */
    // Clear session data, used after a user logs out
    public void clearSession() {
        user = null;
    }
}
