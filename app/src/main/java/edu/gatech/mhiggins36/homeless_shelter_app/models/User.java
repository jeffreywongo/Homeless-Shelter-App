package edu.gatech.mhiggins36.homeless_shelter_app.models;

/**
 * Created by mhigg on 2/21/2018.
 */

public class User {

    private String name;
    private String email;
    private String userType;
    private int userId;
    private String jwt;

    /**
     *
     * @param name of user
     * @param email of user
     * @param userType of user
     * @param userId of user
     * @param jwt java web token
     */
    public User(String name, String email, String userType, int userId, String jwt) {
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.userId = userId;
        this.jwt = jwt;
    }

    /**
     *
     * @return return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType the user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return the jwt
     */
    public String getJwt() {
        return jwt;
    }

    /**
     *
     * @param jwt the jwt
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
