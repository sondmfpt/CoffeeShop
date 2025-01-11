package com.swp.coffeeshop.services.User;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.dto.UserNavigationResponse;
import com.swp.coffeeshop.dto.UserUpdateRequest;
import com.swp.coffeeshop.models.GuestUser;
import com.swp.coffeeshop.models.User;

import java.util.List;
import java.util.Map;

public interface IUserService {
    List<User> findAll();

    User findByUsername(String username);

    User findById(Integer id);

    void saveGuestUser(String trackingId);

    void saveUser(User user);

    String updateUser(UserUpdateRequest user);

    GuestUser getGuestUserByTrackingId(String trackingId);

    List<User> getUsersByRole(String role);

    Map numberOfUsersByRole();

    UserNavigationResponse getUsersNavigation(UserNavigationRequest userNavigationRequest);
}
