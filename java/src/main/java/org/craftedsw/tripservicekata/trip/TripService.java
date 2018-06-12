package org.craftedsw.tripservicekata.trip;

import com.sun.istack.internal.NotNull;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSessionProxy;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSessionProxy;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private static final ArrayList<Trip> NO_TRIPS = new ArrayList<Trip>();
    private ITripDAOProxy tripDAOProxy;
    private IUserSessionProxy userSessionProxy;

    public TripService() {
        this.tripDAOProxy = new TripDAOProxy();
        this.userSessionProxy = new UserSessionProxy();
    }

    public TripService(ITripDAOProxy tripDAOProxy, IUserSessionProxy userSessionProxy) {
        this.tripDAOProxy = tripDAOProxy;
        this.userSessionProxy = userSessionProxy;
    }

    public List<Trip> getTripsByUser(@NotNull User user) throws UserNotLoggedInException {
		User currentUser = userSessionProxy.getLoggedUser();
        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendWith(currentUser) ? tripDAOProxy.findTripsByUser(user) : NO_TRIPS;
    }
}
