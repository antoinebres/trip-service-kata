package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.Assertions;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSession;
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
    private IUserSession fakeUserSession = new FakeUserSession();
    private ITripDAO fakeTripDAO = new FakeTripDAO();

    @Before
    public void initialise() {
        loggedUser = REGISTERED_USER;
    }

    @Test(expected = UserNotLoggedInException.class)
    public void shouldTrowAnExceptionWhenUserNotLoggedIn() throws Exception {
        // Given
        loggedUser = GUEST;
        tripService = new TripService(fakeUserSession, fakeTripDAO);
        // When
        tripService.getTripsByUser(UNUSED_USER);

        // Then
        // Exception is raised

    }

    @Test
    public void shouldReturnNoTripsWhenUserAreNotFriends() throws Exception {
        // Given
        tripService = new TripService(fakeUserSession, fakeTripDAO);
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
        tripService = new TripService(fakeUserSession, fakeTripDAO);
        User aFriend = aUser()
                .friendsWith(ANOTHER_USER, loggedUser)
                .withTrips(TO_LONDON, TO_BRAZIL)
                .build();

        // When
        List<Trip> friendTrips = tripService.getTripsByUser(aFriend);

        // Then
        Assertions.assertThat(friendTrips.size()).isEqualTo(2);
    }

    private class FakeUserSession implements IUserSession {
        public User getLoggedInUser(){
            return loggedUser;
        }
    }

    private class FakeTripDAO implements ITripDAO {
        public List<Trip> tripsBy(User user) {
            return user.trips();
        }
    }
}
