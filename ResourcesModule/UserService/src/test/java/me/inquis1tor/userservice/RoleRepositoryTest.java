package me.inquis1tor.userservice;

import lombok.AllArgsConstructor;
import me.inquis1tor.userservice.entities.Role;
import me.inquis1tor.userservice.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.default_schema=test",
        "spring.liquibase.default-schema=test"
})
class RoleRepositoryTest {

    private RoleRepository roleRepository;

    @Test
    void save() {
        Role user=new Role();
        user.setTitle("User");

        user=roleRepository.save(user);

        Assertions.assertNotNull(user);
    }

}
