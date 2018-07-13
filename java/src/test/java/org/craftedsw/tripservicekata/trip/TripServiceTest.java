package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.Assertions;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;

public class TripServiceTest {
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_LONDON = new Trip();
    private static final User ANOTHER_USER = new User();
    private User loggedUser;
    private User GUEST = null;
    private User UNUSED_USER = new User();
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

        // When
        tripService.getTripsByUser(UNUSED_USER);

        // Then

    }

    @Test
    public void shouldReturnNoTripsWhenUserAreNotFriends() throws Exception {
        // Given
        User notAFriend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_BRAZIL)
                .build();

        // When
        List<Trip> friendTrips = tripService.getTripsByUser(notAFriend);

        // Then
        Assertions.assertThat(friendTrips.size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnTripsWhenUserAreFriends() throws Exception {
        // Given
        User aFriend = aUser()
                .friendsWith(ANOTHER_USER, loggedUser)
                .withTrips(TO_LONDON, TO_BRAZIL)
                .build();

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
