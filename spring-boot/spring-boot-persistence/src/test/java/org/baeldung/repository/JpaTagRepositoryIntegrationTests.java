package org.baeldung.repository;


import org.baeldung.SampleJpaApplication;
import org.baeldung.domain.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleJpaApplication.class)
@Transactional
public class JpaTagRepositoryIntegrationTests {

    @Autowired
    JpaTagRepository repository;

    @Test
    public void findsAllTags() {
        List<Tag> tags = this.repository.findAll();
        assertEquals(tags.size(), (3));
    }

}