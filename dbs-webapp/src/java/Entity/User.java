package Entity;

import Utility.CustomDate;

public class User {

    private String username;
    private String password;
    private String accountType;
    private String emailAddress;
    private String creationDate;
    private String token;
    private String tokenExpiration;

    //Constructor
    public User(String username, String password, String accountType, String emailAddress) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.emailAddress = emailAddress;
        this.creationDate = CustomDate.getDateTime();
    }

    public User(String username, String password, String accountType, String emailAddress, String creationDate) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.emailAddress = emailAddress;
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(String tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
}
