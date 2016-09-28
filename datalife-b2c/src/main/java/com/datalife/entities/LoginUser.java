package com.datalife.entities;

/**
 * Created by barath on 1/18/2016.
 */

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by barath on 1/12/2016.
 */
@Entity
@Table(name = "loginuser", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -5429197634303397754L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private Long userId;
    private String loginToken;
    private String userName;
    private String role;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

