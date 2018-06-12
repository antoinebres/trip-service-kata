package org.craftedsw.tripservicekata.trip;

import com.sun.istack.internal.NotNull;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSessionProxy;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSessionProxy;

import java.util.ArrayList;
import java.util.List;

public class TripService {

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
		List<Trip> trips = new ArrayList<Trip>();
		User currentUser = getLoggedUser();
		boolean isFriend = false;
		if (currentUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(currentUser)) {
					isFriend = true;
					break;
				}
			}
			if (isFriend) {
                trips = tripDAOProxy.findTripsByUser(user);
            }
			return trips;
		} else {
			throw new UserNotLoggedInException();
		}
	}

    protected User getLoggedUser() {
        return userSessionProxy.getLoggedUser();
	}

}
