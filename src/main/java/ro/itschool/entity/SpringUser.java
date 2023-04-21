package ro.itschool.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "springUser")
public class SpringUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<GrantedAuthority> authorities = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"))
    private Set<Post> likes = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id"))
    private List<SpringUser> followedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "springUser")
    private Set<Post> posts = new LinkedHashSet<>();

    public SpringUser(SpringUser springUser) {
        this.enabled = springUser.isEnabled();
        this.roles = springUser.getRoles();
        this.username = springUser.getUsername();
        this.lastName = springUser.getLastName();
        this.firstName = springUser.getFirstName();
        this.id = springUser.getId();
        this.accountNonExpired = springUser.isAccountNonExpired();
        this.accountNonLocked = springUser.isAccountNonLocked();
        this.credentialsNonExpired = springUser.isCredentialsNonExpired();
        this.email = springUser.getEmail();
    }

    public SpringUser(String username,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean accountNonLocked,
                      boolean credentialsNonExpired,
                      List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
    }

    public SpringUser(String id, String username, String lastName, String firstName, String email) {
        this.id = Integer.valueOf(id);
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    @Override
    public String toString() {
        return username;
    }

}
