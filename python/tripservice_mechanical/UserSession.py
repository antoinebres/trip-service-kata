from DependendClassCallDuringUnitTestException import DependendClassCallDuringUnitTestException


class UserSession(object):
    __instance = None

    def __new__(cls):
        if UserSession.__instance is None:
            UserSession.__instance = object.__new__(cls)
        return UserSession.__instance

    @staticmethod
    def getInstance():
        return UserSession()

    def isUserLoggedIn(self, user):
        raise DependendClassCallDuringUnitTestException(
            "UserSession.isUserLoggedIn() should not be called in an unit test"
        )

    def getLoggedUser(self):
        raise DependendClassCallDuringUnitTestException(
            "UserSession.getLoggedUser() should not be called in an unit test"
        )
