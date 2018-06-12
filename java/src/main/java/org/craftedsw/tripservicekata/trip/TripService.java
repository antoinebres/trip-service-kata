package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

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
                trips = fetchTripsByUser(user);
            }
			return trips;
		} else {
			throw new UserNotLoggedInException();
		}
	}

    protected List<Trip> fetchTripsByUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
