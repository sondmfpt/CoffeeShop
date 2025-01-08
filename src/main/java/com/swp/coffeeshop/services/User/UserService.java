package com.swp.coffeeshop.services.User;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.dto.UserNavigationResponse;
import com.swp.coffeeshop.models.GuestUser;
import com.swp.coffeeshop.models.Role;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.repositories.GuestUserRepository;
import com.swp.coffeeshop.repositories.UserRepository;
import com.swp.coffeeshop.services.Role.RoleService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;
    GuestUserRepository guestUserRepository;
    RoleService roleService;


    public UserService(UserRepository userRepository, GuestUserRepository guestUserRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.guestUserRepository = guestUserRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }


    @Override
    public void saveGuestUser(String trackingId) {
        guestUserRepository.save(new GuestUser(trackingId));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Override
    public GuestUser getGuestUserByTrackingId(String trackingId) {
        return guestUserRepository.findAll().stream().filter(g -> g.getTrackingId().equals(trackingId)).findFirst().orElse(null);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findAll().stream().filter(u -> u.getRole().equals(role)).toList();
    }

    @Override
    public Map numberOfUsersByRole() {
        Map<String, Integer> numOfUser = new HashMap<>();
        List<User> allUsers = userRepository.findAll();
        List<Role> roles = roleService.getAllRoles();
        for (Role role : roles) {
            numOfUser.put(role.getRoleName(), (int) allUsers.stream().filter(u -> u.getRole().getRoleName().equals(role.getRoleName())).count());
        }
        return numOfUser;
    }

    @Override
    public UserNavigationResponse getUsersNavigation(UserNavigationRequest userNavigationRequest) {
        String role;
        String searchValue;
        int pageNum = 1;
        int numPerPage = 5;
        UserNavigationResponse response = new UserNavigationResponse();
        if (userNavigationRequest != null) {
            role = userNavigationRequest.getRole();
            searchValue = userNavigationRequest.getSearchValue();
            pageNum = userNavigationRequest.getPageNum();
        } else {
            role = "";
            searchValue = "";
        }
        List<User> userResponse = userRepository.findAll();
        if (role != null && !role.equals("all")) {
            userResponse = userResponse.stream().filter(u -> u.getRole().getDisplayName().toLowerCase().equals(role.toLowerCase())).toList();
        }
        if (searchValue != null && !searchValue.equals("")) {
            userResponse = userResponse.stream().filter(u -> u.getUsername().toLowerCase().contains(searchValue.toLowerCase())).toList();
        }
        response.setTotalUser(userResponse.size());
        response.setTotalPage((userResponse.size() + numPerPage - 1) / numPerPage);
        userResponse = userResponse.stream().skip((pageNum - 1) * numPerPage).limit(numPerPage).toList();

        if (userNavigationRequest.getOrder() != null) {
            Map order = userNavigationRequest.getOrder();
            String type = order.get("type").toString();
            String value = order.get("value").toString();
            if (!value.equals("default")) {
                switch (type) {
                    case "username":
                        userResponse = userResponse.stream().sorted(Comparator.comparing(User::getUsername)).toList();
                        break;
                    case "firstname":
                        userResponse = userResponse.stream().sorted(Comparator.comparing(User::getFirstName)).toList();
                        break;
                    case "lastname":
                        userResponse = userResponse.stream().sorted(Comparator.comparing(User::getLastName)).toList();
                        break;
                    case "date":
                        userResponse = userResponse.stream().sorted(Comparator.comparing(User::getDob)).toList();
                        break;
                    case "role":
                        userResponse = userResponse.stream().sorted((u1, u2) -> u1.getRole().getDisplayName().compareTo(u2.getRole().getDisplayName())).toList();
                        break;
                    case "status":
                        userResponse = userResponse.stream().sorted(Comparator.comparing(User::getActive)).toList();
                        break;
                }
            }
            if (value.equals("desc")) {
                List<User> temp = new ArrayList<>(userResponse);
                Collections.reverse(temp);
                userResponse = temp;
            }
        }
        response.setUsers(userResponse);
        return response;
    }
}
