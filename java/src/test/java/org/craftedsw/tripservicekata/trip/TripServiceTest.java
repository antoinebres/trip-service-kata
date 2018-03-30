package org.craftedsw.tripservicekata.trip;

import org.assertj.core.api.Assertions;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.List;

public class TripServiceTest {
    @Test
    public void shouldDoSomething() throws Exception {
        // Given
        User user = new User();
        TripService tripService = new TripService();

        // When
        List<Trip> tripsOfGivenUser = tripService.getTripsByUser(user);

        // Then
        Assertions.assertThat(tripsOfGivenUser).isEqualTo("Not an exception");
    }
}
