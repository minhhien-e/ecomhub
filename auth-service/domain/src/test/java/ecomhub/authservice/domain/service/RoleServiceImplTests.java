package ecomhub.authservice.domain.service;

import ecomhub.authservice.domain.entity.Account;
import ecomhub.authservice.domain.entity.Permission;
import ecomhub.authservice.domain.entity.Role;
import ecomhub.authservice.domain.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceImplTests {

    private RoleServiceImpl roleService;
    private Role targetRole;
    private Account requester;
    private Role requesterRole;
    private Permission editPermission;
    private Permission deletePermission;

    @BeforeEach
    void init() {
        roleService = new RoleServiceImpl();
        
        // Create target role with level 5
        targetRole = new Role("TargetRole", "Target role description", 5);
        
        // Create requester role with level 8 (higher than target)
        requesterRole = new Role("RequesterRole", "Requester role description", 8);
        
        // Create permissions
        editPermission = new Permission("Edit Role", "role.edit", "Permission to edit roles");
        deletePermission = new Permission("Delete Role", "role.delete", "Permission to delete roles");
        
        // Grant permissions to requester role
        requesterRole.grantPermission(editPermission);
        requesterRole.grantPermission(deletePermission);
        
        // Create requester account with the role
        Set<Role> requesterRoles = Set.of(requesterRole);
        requester = new Account(UUID.randomUUID(), "requester@example.com", "requesteruser", 
                              "0939346634", "hashedPassword123", "LOCAL", true, requesterRoles);
    }

    //region Can Be Modified Tests
    @Test
    void canBeModifiedBy_WhenRequesterHasHigherLevelAndPermission_ShouldReturnTrue() {
        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, requester);

        // Assert
        assertTrue(canModify);
    }

    @Test
    void canBeModifiedBy_WhenRequesterHasSameLevel_ShouldReturnFalse() {
        // Arrange
        Role sameLevelRole = new Role("SameLevelRole", "Same level role description", 5);
        sameLevelRole.grantPermission(editPermission);
        Set<Role> sameLevelRoles = Set.of(sameLevelRole);
        Account sameLevelRequester = new Account(UUID.randomUUID(), "samelevel@example.com", "sameleveluser", 
                                               "0939346634", "hashedPassword123", "LOCAL", true, sameLevelRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, sameLevelRequester);

        // Assert
        assertFalse(canModify);
    }

    @Test
    void canBeModifiedBy_WhenRequesterHasLowerLevel_ShouldReturnFalse() {
        // Arrange
        Role lowerLevelRole = new Role("LowerLevelRole", "Lower level role description", 3);
        lowerLevelRole.grantPermission(editPermission);
        Set<Role> lowerLevelRoles = Set.of(lowerLevelRole);
        Account lowerLevelRequester = new Account(UUID.randomUUID(), "lowerlevel@example.com", "lowerleveluser", 
                                                "0939346634", "hashedPassword123", "LOCAL", true, lowerLevelRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, lowerLevelRequester);

        // Assert
        assertFalse(canModify);
    }

    @Test
    void canBeModifiedBy_WhenRequesterHasHigherLevelButNoPermission_ShouldReturnFalse() {
        // Arrange
        Role noPermissionRole = new Role("NoPermissionRole", "No permission role description", 8);
        // Don't grant any permissions
        Set<Role> noPermissionRoles = Set.of(noPermissionRole);
        Account noPermissionRequester = new Account(UUID.randomUUID(), "nopermission@example.com", "nopermissionuser", 
                                                  "0939346634", "hashedPassword123", "LOCAL", true, noPermissionRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, noPermissionRequester);

        // Assert
        assertFalse(canModify);
    }

    @Test
    void canBeModifiedBy_WhenRequesterHasMultipleRoles_ShouldReturnTrue() {
        // Arrange
        Role additionalRole = new Role("AdditionalRole", "Additional role description", 10);
        additionalRole.grantPermission(editPermission);
        
        Set<Role> multipleRoles = new HashSet<>();
        multipleRoles.add(requesterRole);
        multipleRoles.add(additionalRole);
        
        Account multipleRolesRequester = new Account(UUID.randomUUID(), "multipleroles@example.com", "multiplerolesuser", 
                                                   "0939346634", "hashedPassword123", "LOCAL", true, multipleRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, multipleRolesRequester);

        // Assert
        assertTrue(canModify);
    }

    @Test
    void canBeModifiedBy_WhenRequesterHasMultipleRolesButNoneQualify_ShouldReturnFalse() {
        // Arrange
        Role lowLevelRole = new Role("LowLevelRole", "Low level role description", 3);
        lowLevelRole.grantPermission(editPermission);
        
        Role noPermissionRole = new Role("NoPermissionRole", "No permission role description", 8);
        // Don't grant any permissions
        
        Set<Role> multipleRoles = new HashSet<>();
        multipleRoles.add(lowLevelRole);
        multipleRoles.add(noPermissionRole);
        
        Account multipleRolesRequester = new Account(UUID.randomUUID(), "multipleroles@example.com", "multiplerolesuser", 
                                                   "0939346634", "hashedPassword123", "LOCAL", true, multipleRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, multipleRolesRequester);

        // Assert
        assertFalse(canModify);
    }
    //endregion

    //region Can Be Deleted Tests
    @Test
    void canBeDeletedBy_WhenRequesterHasHigherLevelAndPermission_ShouldReturnTrue() {
        // Act
        boolean canDelete = roleService.canBeDeletedBy(targetRole, requester);

        // Assert
        assertTrue(canDelete);
    }

    @Test
    void canBeDeletedBy_WhenRequesterHasSameLevel_ShouldReturnFalse() {
        // Arrange
        Role sameLevelRole = new Role("SameLevelRole", "Same level role description", 5);
        sameLevelRole.grantPermission(deletePermission);
        Set<Role> sameLevelRoles = Set.of(sameLevelRole);
        Account sameLevelRequester = new Account(UUID.randomUUID(), "samelevel@example.com", "sameleveluser", 
                                               "0939346634", "hashedPassword123", "LOCAL", true, sameLevelRoles);

        // Act
        boolean canDelete = roleService.canBeDeletedBy(targetRole, sameLevelRequester);

        // Assert
        assertFalse(canDelete);
    }

    @Test
    void canBeDeletedBy_WhenRequesterHasLowerLevel_ShouldReturnFalse() {
        // Arrange
        Role lowerLevelRole = new Role("LowerLevelRole", "Lower level role description", 3);
        lowerLevelRole.grantPermission(deletePermission);
        Set<Role> lowerLevelRoles = Set.of(lowerLevelRole);
        Account lowerLevelRequester = new Account(UUID.randomUUID(), "lowerlevel@example.com", "lowerleveluser", 
                                                "0939346634", "hashedPassword123", "LOCAL", true, lowerLevelRoles);

        // Act
        boolean canDelete = roleService.canBeDeletedBy(targetRole, lowerLevelRequester);

        // Assert
        assertFalse(canDelete);
    }

    @Test
    void canBeDeletedBy_WhenRequesterHasHigherLevelButNoPermission_ShouldReturnFalse() {
        // Arrange
        Role noPermissionRole = new Role("NoPermissionRole", "No permission role description", 8);
        // Don't grant any permissions
        Set<Role> noPermissionRoles = Set.of(noPermissionRole);
        Account noPermissionRequester = new Account(UUID.randomUUID(), "nopermission@example.com", "nopermissionuser", 
                                                  "0939346634", "hashedPassword123", "LOCAL", true, noPermissionRoles);

        // Act
        boolean canDelete = roleService.canBeDeletedBy(targetRole, noPermissionRequester);

        // Assert
        assertFalse(canDelete);
    }
    //endregion

    //region Edge Cases Tests
    @Test
    void canBeModifiedBy_WhenTargetRoleHasZeroLevel_ShouldWorkCorrectly() {
        // Arrange
        Role zeroLevelTargetRole = new Role("ZeroLevelRole", "Zero level role description", 0);
        Role highLevelRole = new Role("HighLevelRole", "High level role description", 1);
        highLevelRole.grantPermission(editPermission);
        Set<Role> highLevelRoles = Set.of(highLevelRole);
        Account highLevelRequester = new Account(UUID.randomUUID(), "highlevel@example.com", "highleveluser", 
                                               "0939346634", "hashedPassword123", "LOCAL", true, highLevelRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(zeroLevelTargetRole, highLevelRequester);

        // Assert
        assertTrue(canModify);
    }

    @Test
    void canBeModifiedBy_WhenTargetRoleHasVeryHighLevel_ShouldWorkCorrectly() {
        // Arrange
        Role veryHighLevelTargetRole = new Role("VeryHighLevelRole", "Very high level role description", 100);
        Role superHighLevelRole = new Role("SuperHighLevelRole", "Super high level role description", 101);
        superHighLevelRole.grantPermission(editPermission);
        Set<Role> superHighLevelRoles = Set.of(superHighLevelRole);
        Account superHighLevelRequester = new Account(UUID.randomUUID(), "superhighlevel@example.com", "superhighleveluser", 
                                                    "0939346634", "hashedPassword123", "LOCAL", true, superHighLevelRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(veryHighLevelTargetRole, superHighLevelRequester);

        // Assert
        assertTrue(canModify);
    }

    //region Permission Key Tests
    @Test
    void canBeModifiedBy_WhenPermissionKeyIsDifferent_ShouldReturnFalse() {
        // Arrange
        Role differentPermissionRole = new Role("DifferentPermissionRole", "Different permission role description", 8);
        Permission differentPermission = new Permission("Different Permission", "different.permission", "Different permission description");
        differentPermissionRole.grantPermission(differentPermission);
        Set<Role> differentPermissionRoles = Set.of(differentPermissionRole);
        Account differentPermissionRequester = new Account(UUID.randomUUID(), "differentpermission@example.com", "differentpermissionuser", 
                                                        "0939346634", "hashedPassword123", "LOCAL", true, differentPermissionRoles);

        // Act
        boolean canModify = roleService.canBeModifiedBy(targetRole, differentPermissionRequester);

        // Assert
        assertFalse(canModify);
    }

    @Test
    void canBeDeletedBy_WhenPermissionKeyIsDifferent_ShouldReturnFalse() {
        // Arrange
        Role differentPermissionRole = new Role("DifferentPermissionRole", "Different permission role description", 8);
        Permission differentPermission = new Permission("Different Permission", "different.permission", "Different permission description");
        differentPermissionRole.grantPermission(differentPermission);
        Set<Role> differentPermissionRoles = Set.of(differentPermissionRole);
        Account differentPermissionRequester = new Account(UUID.randomUUID(), "differentpermission@example.com", "differentpermissionuser", 
                                                        "0939346634", "hashedPassword123", "LOCAL", true, differentPermissionRoles);

        // Act
        boolean canDelete = roleService.canBeDeletedBy(targetRole, differentPermissionRequester);

        // Assert
        assertFalse(canDelete);
    }
    //endregion
} 