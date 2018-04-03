from User import User
from TripService import TripService


class TestTripService:

    def test_should_not_raise_an_exception(self):
        # Given
        user = User()
        trip_service = TripService()

        # When
        trips_of_given_user = trip_service.getTripsByUser(user)

        # Then
        assert trips_of_given_user == "Not an exception"
