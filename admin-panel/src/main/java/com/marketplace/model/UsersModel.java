package com.marketplace.model;

import com.marketplace.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UsersModel {

    private Boolean checked;
    private User user;

    public UsersModel(User user) {
        this.user = user;
        this.checked = false;
    }

    public static List<UsersModel> of(List<User> user) {
        return user.stream().map(UsersModel::new).collect(Collectors.toList());
    }
}
