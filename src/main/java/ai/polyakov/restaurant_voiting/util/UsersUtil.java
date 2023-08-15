package ai.polyakov.restaurant_voiting.util;

import ai.polyakov.restaurant_voiting.model.Role;
import ai.polyakov.restaurant_voiting.model.User;
import lombok.experimental.UtilityClass;
import ai.polyakov.restaurant_voiting.to.UserTo;

@UtilityClass
public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}