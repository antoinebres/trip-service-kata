package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSession;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {
    private IUserSession userSession;
    private ITripDAO tripDAO;
    private List<Trip> NO_TRIPS = new ArrayList<Trip>();

    public TripService() {
        userSession = new UserSession();
        tripDAO = new TripDAO();
    }

    public TripService(IUserSession userSession, ITripDAO tripDAO) {
        this.userSession = userSession;
        this.tripDAO = tripDAO;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (userSession.getLoggedInUser() == null) {
			throw new UserNotLoggedInException();
		}
		return user.isFriendWith(userSession.getLoggedInUser())
                ? tripDAO.tripsBy(user)
    		    : NO_TRIPS;
	}
}
