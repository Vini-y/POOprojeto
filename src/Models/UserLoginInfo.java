package Models;

public class UserLoginInfo {
    private int userId;
    private String userType;

    public UserLoginInfo(int userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }
}
