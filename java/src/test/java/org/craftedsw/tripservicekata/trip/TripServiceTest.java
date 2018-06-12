package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.IUserSessionProxy;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    @Mock
    private ITripDAOProxy tripDAOProxy;

    @Mock
    private IUserSessionProxy userSessionProxy;

    @InjectMocks
    private TripService tripService;

    private User currentUser;
    private User givenUser = new User();
    private final List<Trip> trips = asList(new Trip());

    @Before
    public void setUp() {
        currentUser = new User();
        given(tripDAOProxy.findTripsByUser(any(User.class))).willReturn(trips);
        given(userSessionProxy.getLoggedUser()).willReturn(currentUser);
    }

    @Test
    public void getTripsByUser_return_no_trips_when_logged_user_has_no_friends() {
        // when
        List<Trip> tripsByUser = tripService.getTripsByUser(givenUser);

        // then
        assertThat(tripsByUser).isEmpty();
    }

    @Test
    public void getTripsByUser_return_a_trip_when_logged_user_is_friend_with_given_user() {
        // given
        User user = new User();
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
        given(userSessionProxy.getLoggedUser()).willReturn(null);

        // when
        tripService.getTripsByUser(new User());
    }

    @Test(expected = NullPointerException.class)
    public void getTripsByUser_throws_a_null_pointer_exception_when_given_user_os_null() {
        tripService.getTripsByUser(null);
    }
}
