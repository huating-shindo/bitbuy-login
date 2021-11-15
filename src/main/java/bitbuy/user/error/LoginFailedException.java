package bitbuy.user.error;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String fail) {
        super(fail);
    }
}
