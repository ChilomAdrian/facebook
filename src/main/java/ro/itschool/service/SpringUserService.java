package ro.itschool.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.itschool.entity.SpringUser;

import java.util.List;

@Service
public interface SpringUserService {

    SpringUser registerUser(SpringUser springUser);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<SpringUser> getAllUsers();
    List<SpringUser> searchUser(String keyword);
    void followUser(Integer id);
    List<SpringUser> getFollowedUsers();
    void deleteById(Integer id);
    void unfollowUser(Integer id);
}
