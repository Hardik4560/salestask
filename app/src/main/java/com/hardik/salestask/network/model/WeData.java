
package com.hardik.salestask.network.model;

import com.hardik.salestask.models.User;

/**
 * Created by Hardik Shah on 15-07-2015.
 */
public class WeData {

    private Long id;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
