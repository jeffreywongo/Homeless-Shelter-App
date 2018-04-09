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
     * @param name
     * @param email
     * @param userType
     * @param userId
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
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getJwt() {
        return jwt;
    }

    /**
     *
     * @param jwt
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
