package accountServer;

public interface AccountServer {

    int getUsersLimit();

    void addNewUser();

    void removeUser();

    void setUsersLimit(int usersLimit);

    int getUsersCount();
}
