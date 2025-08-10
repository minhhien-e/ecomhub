package ecomhub.authservice.adapter.output.repository;

import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.infrastructure.data.persistence.entity.PermissionEntity;
import ecomhub.authservice.infrastructure.data.persistence.entity.RoleEntity;
import ecomhub.authservice.infrastructure.data.persistence.repository.PermissionJpaRepository;
import ecomhub.authservice.infrastructure.data.persistence.repository.RoleJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(RoleRepository.class)
@Testcontainers
@Transactional
class RoleRepositoryTests {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.3")
            .withDatabaseName("authdb-test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleJpaRepository roleJpaRepository;

    @Autowired
    PermissionJpaRepository permissionJpaRepository;

    UUID roleId;
    UUID permissionId;

    @BeforeEach
    void setup() {
        // Tạo permission trước
        permissionId = UUID.randomUUID();
        PermissionEntity p = PermissionEntity.builder()
                .id(permissionId)
                .key("role.delete")
                .name("Delete Role")
                .build();
        permissionJpaRepository.save(p);

        // Tạo role
        roleId = UUID.randomUUID();
        RoleEntity r = new RoleEntity();
        r.setId(roleId);
        r.setName("Admin");
        r.setDescription("Administrator role");
        r.setActive(true);
        r.setLevel(10);
        roleJpaRepository.save(r);
    }

    @Test
    void findById_WhenRoleHasPermissions_ShouldMapToDomainSuccessfully() {
        Optional<Role> result = roleRepository.findById(roleId);

        assertThat(result).isPresent();
        Role role = result.get();
        assertThat(role.getName().getValue()).isEqualTo("Admin");
    }

    @Test
    void existsByName_WhenRoleExists_ShouldReturnTrue() {
        assertThat(roleRepository.existsByName("Admin")).isTrue();
    }

    @Test
    void updateActive_ShouldChangeStatus() {
        int updated = roleRepository.updateActive(roleId, false);
        assertThat(updated).isEqualTo(1);

        Role updatedRole = roleRepository.findById(roleId).orElseThrow();
        assertThat(updatedRole.isActive()).isFalse();
    }

    @Test
    void grantAndRevokePermissions_ShouldWork() {
        UUID extraPermissionId = permissionJpaRepository.save(
                PermissionEntity.builder()
                        .id(UUID.randomUUID())
                        .key("role.edit")
                        .name("Edit Role")
                        .build()
        ).getId();

        // Grant
        roleRepository.grantPermissions(roleId, Set.of(extraPermissionId));
        Role roleAfterGrant = roleRepository.findById(roleId).orElseThrow();
        assertThat(roleAfterGrant.getPermissions()).hasSize(2);

        // Revoke
        roleRepository.revokePermissions(roleId, Set.of(extraPermissionId));
        Role roleAfterRevoke = roleRepository.findById(roleId).orElseThrow();
        assertThat(roleAfterRevoke.getPermissions()).hasSize(1);
    }
}
