package com.swp.coffeeshop.services.User;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.dto.UserNavigationResponse;
import com.swp.coffeeshop.dto.UserUpdateRequest;
import com.swp.coffeeshop.models.GuestUser;
import com.swp.coffeeshop.models.Role;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.repositories.GuestUserRepository;
import com.swp.coffeeshop.repositories.UserRepository;
import com.swp.coffeeshop.services.OtherService.OtherService;
import com.swp.coffeeshop.services.Role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;
    GuestUserRepository guestUserRepository;
    RoleService roleService;
    OtherService otherService;


    public UserService(UserRepository userRepository, GuestUserRepository guestUserRepository, RoleService roleService, OtherService otherService) {
        this.userRepository = userRepository;
        this.guestUserRepository = guestUserRepository;
        this.roleService = roleService;
        this.otherService = otherService;
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
        User user = userRepository.findById(id).get();
        return userRepository.findById(id).get();
    }


    @Override
    public void saveGuestUser(String trackingId) {
        guestUserRepository.save(new GuestUser(trackingId));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public String updateUser(UserUpdateRequest user) {
        try {
            User u = userRepository.findById(user.getId()).get();
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setPhone(user.getPhone());
            u.setEmail(user.getEmail());
            u.setGender(user.getGender());
            u.setDob(user.getDob());
            u.setActive(user.getActive());
            u.setRole(roleService.getRoleById(user.getRoleId()));
            if (user.getPassword() != null) u.setPassword(user.getPassword());
            String relativePath = "src/main/resources/static/img/avatar/avatar-userId" + user.getId() + ".png";
            String absolutePath = Paths.get(relativePath).toAbsolutePath().toString();
            if (!user.getAvatar().isEmpty())
                otherService.saveImage(user.getAvatar(), absolutePath);
            userRepository.save(u);
            return "success";
        } catch (Exception e) {
            return "error";
        }

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
