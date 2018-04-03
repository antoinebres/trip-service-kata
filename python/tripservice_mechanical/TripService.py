from TripDAO import TripDAO
from UserSession import UserSession
from UserNotLoggedInException import UserNotLoggedInException


class TripService:
    def getTripsByUser(self, user):
        logged_user = UserSession.getInstance().getLoggedUser()
        is_friend = False
        trip_list = []
        if logged_user:
            for friend in user.getFriends():
                if friend is logged_user:
                    is_friend = True
                    break
            if is_friend:
                trip_list = TripDAO.findTripsByUser(user)
            return trip_list
        else:
            raise UserNotLoggedInException()
