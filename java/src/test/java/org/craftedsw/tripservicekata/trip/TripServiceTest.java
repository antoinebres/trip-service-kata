package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.Assertions;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TripServiceTest {
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_LONDON = new Trip();
    private static final User ANOTHER_USER = new User();
    private User loggedUser;
    private User GUEST = null;
    private User REGISTERED_USER = new User();
    private TripService tripService;

    @Before
    public void initialise() {
        tripService = new TestableTripService();
        loggedUser = REGISTERED_USER;
    }

    @Test(expected = UserNotLoggedInException.class)
    public void shouldTrowAnExceptionWhenUserNotLoggedIn() throws Exception {
        // Given
        loggedUser = GUEST;
        User user = new User();
        TripService tripService = new TestableTripService();

        // When
        tripService.getTripsByUser(user);

        // Then

    }

    @Test
    public void shouldReturnNoTripsWhenUserAreNotFriends() throws Exception {
        // Given
        User notAFriend = new User();
        notAFriend.addTrip(TO_BRAZIL);
        TripService tripService = new TestableTripService();

        // When
        List<Trip> friendTrips = tripService.getTripsByUser(notAFriend);

        // Then
        Assertions.assertThat(friendTrips.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnTripsWhenUserAreFriends() throws Exception {
        // Given
        User aFriend = new User();
        aFriend.addFriend(ANOTHER_USER);
        aFriend.addFriend(loggedUser);
        aFriend.addTrip(TO_BRAZIL);
        aFriend.addTrip(TO_LONDON);
        TripService tripService = new TestableTripService();


        // When
        List<Trip> friendTrips = tripService.getTripsByUser(aFriend);

        // Then
        Assertions.assertThat(friendTrips.size()).isEqualTo(2);
    }


    public class TestableTripService extends TripService{
        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }
        @Override
        protected List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }

}
