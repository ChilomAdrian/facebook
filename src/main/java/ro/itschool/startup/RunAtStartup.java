package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Post;
import ro.itschool.entity.Role;
import ro.itschool.entity.SpringUser;
import ro.itschool.repository.PostRepo;
import ro.itschool.repository.RoleRepo;
import ro.itschool.service.SpringUserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RunAtStartup {
    private final RoleRepo roleRepo;

    private final SpringUserService springUserService;

    private final PostRepo postRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void insertRestaurantsIntoDB() {


        Role role = new Role("ROLE_USER");
        Role savedRole = roleRepo.save(role);

        role = new Role("ROLE_ADMIN");
        roleRepo.save(role);

        SpringUser springUser = new SpringUser();
        springUser.setPassword("password123");
        springUser.setEmail("elenamaria@email.com");
        springUser.setUsername("elenamaria");
        springUser.setFirstName("Elena");
        springUser.setLastName("Maria");
        springUser.setRoles(Collections.singleton(savedRole));
        springUser.setEnabled(true);
        springUser.setAccountNonExpired(true);
        springUser.setAccountNonLocked(true);
        springUser.setCredentialsNonExpired(true);
        Set<Post> posts = Set.of(new Post(" user message post 1", LocalDateTime.now(), springUser),
                new Post("elenamaria message post 2", LocalDateTime.now(), springUser),
                new Post("elenamaria message post 3", LocalDateTime.now(), springUser));
        springUser.setPosts(posts);
        postRepo.saveAll(posts);
        springUserService.registerUser(springUser);

        SpringUser springUser2 = new SpringUser();
        springUser2.setPassword("parolazxc");
        springUser2.setEmail("marian12@email.com");
        springUser2.setUsername("marian12");
        springUser2.setFirstName("Marian");
        springUser2.setLastName("Andrei");
        springUser2.setRoles(Collections.singleton(savedRole));
        springUser2.setEnabled(true);
        springUser2.setAccountNonExpired(true);
        springUser2.setAccountNonLocked(true);
        springUser2.setCredentialsNonExpired(true);
        Set<Post> posts2 = Set.of(new Post(" lticoiu message post 1 user", LocalDateTime.now(), springUser2),
                new Post("marian12 message post 2", LocalDateTime.now(), springUser2),
                new Post("marian12 message post 3", LocalDateTime.now(), springUser2));
        springUser2.setPosts(posts2);
        postRepo.saveAll(posts2);
        springUserService.registerUser(springUser2);

        SpringUser springUser3 = new SpringUser();
        springUser3.setPassword("passw66");
        springUser3.setEmail("daniel@email.com");
        springUser3.setUsername("daniel");
        springUser3.setFirstName("Daniel");
        springUser3.setLastName("Nastase");
        springUser3.setRoles(Collections.singleton(savedRole));
        springUser3.setEnabled(true);
        springUser3.setAccountNonExpired(true);
        springUser3.setAccountNonLocked(true);
        springUser3.setCredentialsNonExpired(true);
        Set<Post> posts3 = Set.of(new Post(" pgirdea message post 1", LocalDateTime.now(), springUser3),
                new Post("daniel message post 2", LocalDateTime.now(), springUser3),
                new Post("daniel message post 3", LocalDateTime.now(), springUser3));
        springUser3.setPosts(posts3);
        postRepo.saveAll(posts3);
        springUserService.registerUser(springUser3);

    }
}
