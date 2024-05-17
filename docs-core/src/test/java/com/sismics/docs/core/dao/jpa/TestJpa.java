package com.sismics.docs.core.dao.jpa;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.util.TransactionUtil;
import com.sismics.docs.core.util.authentication.InternalAuthenticationHandler;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
//public class TestJpa extends BaseTransactionalTest {
//    @Test
//    public void testJpa() throws Exception {
//        // Create a user
//        UserDao userDao = new UserDao();
//        User user = new User();
//        user.setUsername("username");
//        user.setPassword("12345678");
//        user.setEmail("toto@docs.com");
//        user.setRoleId("admin");
//        user.setStorageQuota(10L);
//        String id = userDao.create(user, "me");
//
//        TransactionUtil.commit();
//
//        // Search a user by his ID
//        user = userDao.getById(id);
//        Assert.assertNotNull(user);
//        Assert.assertEquals("toto@docs.com", user.getEmail());
//
//        // Authenticate using the database
//        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("username", "12345678"));
//    }
//}
//New Verion

//public class TestJpa extends BaseTransactionalTest {
//    @Test
//    public void testJpa() throws Exception {
//        // Create a user
//        UserDao userDao = new UserDao();
//        User user1 = new User();
//        user1.setUsername("user1");
//        user1.setPassword("12345678");
//        user1.setEmail("user1@docs.com");
//        user1.setRoleId("admin");
//        user1.setStorageQuota(10L);
//        String id1 = userDao.create(user1, "me");
//
//        TransactionUtil.commit();
//
//        Assert.assertNotNull(new InternalAuthenticationHandler().authenticate("username", "12345678"));
//
//        // Delete the user
//        userDao.delete("username", "me");
//
//        TransactionUtil.commit();
//
//        // Verify user is deleted
//        user1 = userDao.getById(id1);
//        Assert.assertNull("User should be deleted", user1);
//
//        // Verify associated data is deleted
//        // Add assertions here based on your data model
//        // For example, check that related AuthenticationTokens, Documents, Files, etc. are deleted
//        // You can do this by querying the database or using the userDao methods
//
//        // Verify the user can no longer be authenticated
//        Assert.assertNull("User should not be authenticated after deletion", new InternalAuthenticationHandler().authenticate("username", "12345678"));
//
//
//        // Test updating a user
//        User updatedUser = userDao.getById(id1);
//        updatedUser.setEmail("updateduser1@docs.com");
//        updatedUser.setStorageQuota(7L);
//        userDao.update(updatedUser, "me");
//        TransactionUtil.commit();
//
//        User retrievedUpdatedUser = userDao.getById(id1);
//        Assert.assertEquals("updateduser1@docs.com", retrievedUpdatedUser.getEmail());
//        Assert.assertEquals((Object) 7L, (Object) retrievedUpdatedUser.getStorageQuota());
//
//        // Test updating user password
//        User passwordUpdateUser = userDao.getById(id1);
//        passwordUpdateUser.setPassword("newpassword123");
//        userDao.updatePassword(passwordUpdateUser, "me");
//        TransactionUtil.commit();
//
//        User retrievedPasswordUpdateUser = userDao.getById(id1);
//        Assert.assertNotNull(retrievedPasswordUpdateUser);
//        Assert.assertEquals("newpassword123", retrievedPasswordUpdateUser.getPassword());
//    }
//}

/**
 * Tests the persistence layer.
 *
 * @author jtremeaux
 */


/**
 * Tests the persistence layer.
 *
 * @author jtremeaux
 */
public class TestJpa extends BaseTransactionalTest {
    @Test
    public void testCreateUser() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("newpassword");
        user.setEmail("newuser@example.com");
        user.setRoleId("user");
        user.setStorageQuota(50L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        User createdUser = userDao.getById(id);
        Assert.assertNotNull(createdUser);
        Assert.assertEquals("newuser@example.com", createdUser.getEmail());
        Assert.assertEquals("user", createdUser.getRoleId());

        // Clean up
        userDao.delete("newuser", "admin");
        TransactionUtil.commit();
    }


    @Test
    public void testDeleteUser() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("tobedeleted");
        user.setPassword("password");
        user.setEmail("tobedeleted@example.com");
        user.setRoleId("user");
        user.setStorageQuota(100L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        userDao.delete("tobedeleted", "admin");

        TransactionUtil.commit();

        User deletedUser = userDao.getById(id);
        Assert.assertNull("User should be deleted", deletedUser);
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("tobeupdated");
        user.setPassword("password");
        user.setEmail("tobeupdated@example.com");
        user.setRoleId("user");
        user.setStorageQuota(100L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        User updatedUser = userDao.getById(id);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setStorageQuota(200L);

        userDao.update(updatedUser, "admin");

        TransactionUtil.commit();

        User retrievedUpdatedUser = userDao.getById(id);
        Assert.assertNotNull(retrievedUpdatedUser);
        Assert.assertEquals("updated@example.com", retrievedUpdatedUser.getEmail());
        Assert.assertEquals((Object) 200L, (Object) retrievedUpdatedUser.getStorageQuota());

        // Clean up
        userDao.delete("tobeupdated", "admin");
        TransactionUtil.commit();
    }

    @Test
    public void testUpdatePassword() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("updatepassword");
        user.setPassword("oldpassword");
        user.setEmail("updatepassword@example.com");
        user.setRoleId("user");
        user.setStorageQuota(100L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        User passwordUpdateUser = userDao.getById(id);
        passwordUpdateUser.setPassword("newpassword");
        userDao.updatePassword(passwordUpdateUser, "admin");

        TransactionUtil.commit();

        User retrievedPasswordUpdateUser = userDao.getById(id);
        Assert.assertNotNull(retrievedPasswordUpdateUser);
        Assert.assertEquals("newpassword", retrievedPasswordUpdateUser.getPassword());

        // Clean up
        userDao.delete("updatepassword", "admin");
        TransactionUtil.commit();
    }

    @Test
    public void testGetUserById() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("getbyiduser");
        user.setPassword("password");
        user.setEmail("getbyiduser@example.com");
        user.setRoleId("user");
        user.setStorageQuota(100L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        User retrievedUser = userDao.getById(id);
        Assert.assertNotNull(retrievedUser);
        Assert.assertEquals("getbyiduser@example.com", retrievedUser.getEmail());

        // Clean up
        userDao.delete("getbyiduser", "admin");
        TransactionUtil.commit();
    }

    @Test
    public void testUpdateQuota() throws Exception {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUsername("quotauser");
        user.setPassword("password");
        user.setEmail("quotauser@example.com");
        user.setRoleId("user");
        user.setStorageQuota(100L);

        String id = userDao.create(user, "admin");

        TransactionUtil.commit();

        User quotaUpdateUser = userDao.getById(id);
        quotaUpdateUser.setStorageQuota(150L);
        userDao.updateQuota(quotaUpdateUser);

        TransactionUtil.commit();

        User retrievedQuotaUpdateUser = userDao.getById(id);
        Assert.assertNotNull(retrievedQuotaUpdateUser);
        Assert.assertEquals((Object) 150L, (Object) retrievedQuotaUpdateUser.getStorageQuota());

        // Clean up
        userDao.delete("quotauser", "admin");
        TransactionUtil.commit();
    }
}


