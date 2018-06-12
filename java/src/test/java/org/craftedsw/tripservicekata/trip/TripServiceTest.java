package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TripServiceTest {

    private User currentUser;

    @Test
    public void getTripsByUser_return_no_trips_when_logged_user_has_no_friends() {
        // given
        TripService tripService = new TestableTripService();
        User user = new User();
        currentUser = new User();

        // when
        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        // then
        assertThat(tripsByUser).isEmpty();
    }

    @Test
    public void getTripsByUser_return_a_trip_when_logged_user_is_friend_with_given_user() {
        // given
        TripService tripService = new TestableTripService();
        User user = new User();
        currentUser = new User();
        User otherFriend = new User();
        user.addFriend(otherFriend);
        user.addFriend(currentUser);

        // when
        List<Trip> tripsByUser = tripService.getTripsByUser(user);

        // then
        assertThat(tripsByUser).isNotEmpty();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void getTripsByUser_throws_an_UserNotLoggedInException_when_user_is_not_logged_in() {
        // given
        TripService tripService = new TestableTripService();
        User user = new User();
        currentUser = null;

        // when
        tripService.getTripsByUser(user);
    }

    @Test(expected = NullPointerException.class)
    public void getTripsByUser_throws_a_null_pointer_exception_when_given_user_os_null() {
        // given
        TripService tripService = new TestableTripService();
        currentUser = new User();

        // when
        tripService.getTripsByUser(null);
    }

    class TestableTripService extends TripService {

        @Override
        protected User getLoggedUser() {
            return currentUser;
        }

        @Override
        protected List<Trip> fetchTripsByUser(User user) {
            List<Trip> trips = new ArrayList<Trip>();
            trips.add(new Trip());
            return trips;
        }
    }
}
