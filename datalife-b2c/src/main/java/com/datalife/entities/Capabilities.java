package com.datalife.entities;

/**
 * Created by Dipak on 3/4/2015.
 */
public class Capabilities {

    private String isGuest;

    private String isMutable;

    private String isAdmin;

    public String getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(String isGuest) {
        this.isGuest = isGuest;
    }

    public String getIsMutable() {
        return isMutable;
    }

    public void setIsMutable(String isMutable) {
        this.isMutable = isMutable;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "Capabilities{" +
                "isGuest='" + isGuest + '\'' +
                ", isMutable='" + isMutable + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }
}
