package org.craftedsw.tripservicekata.user;

public class UserSessionProxy implements IUserSessionProxy {
    public User getLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }
}
