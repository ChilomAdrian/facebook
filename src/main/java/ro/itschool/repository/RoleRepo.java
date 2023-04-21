package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
