package accountServer;

public class AccountServerImpl implements AccountServer {

    private int usersLimit;
    private int usersCount;

    public AccountServerImpl () {
        this.usersLimit = 10;
        this.usersCount = 0;
    }

    public AccountServerImpl (int usersCount) {
        this.usersLimit = 10;
        this.usersCount = usersCount;
    }

    public AccountServerImpl (int usersLimit, int usersCount) {
        this.usersLimit = usersLimit;
        this.usersCount = usersCount;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    @Override
    public void addNewUser() {
        usersCount += 1;
    }

    @Override
    public void removeUser() {
        usersCount -= 1;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }

    @Override
    public int getUsersCount() {
        return usersCount;
    }
}
