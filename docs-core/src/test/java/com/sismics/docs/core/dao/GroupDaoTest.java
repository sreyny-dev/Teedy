package com.sismics.docs.core.dao;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.dao.GroupDao;
import com.sismics.docs.core.model.jpa.Group;
import org.junit.Assert;
import org.junit.Test;

import com.sismics.docs.core.dao.GroupDao;
import com.sismics.docs.core.model.jpa.Group;
import org.junit.Assert;
import org.junit.Test;

public class GroupDaoTest extends BaseTransactionalTest {

    @Test
    public void testCreateGroup() {
        // Create a new group
        GroupDao groupDao = new GroupDao();
        Group group = new Group();
        group.setName("Test Group");
//        group.setDescription("This is a test group");

        try {
            String createdGroupId = groupDao.create(group, "admin");
            Assert.assertNotNull(createdGroupId);

            // Now fetch the group to verify if it was created
            Group retrievedGroup = groupDao.getActiveById(createdGroupId);
            Assert.assertNotNull(retrievedGroup);
            Assert.assertEquals("Test Group", retrievedGroup.getName());
//            Assert.assertEquals("This is a test group", retrievedGroup.getDescription());

            // Additional assertions as needed

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred while creating group: " + e.getMessage());
        }
    }
}
