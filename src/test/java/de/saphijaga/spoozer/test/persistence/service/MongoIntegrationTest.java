package de.saphijaga.spoozer.test.persistence.service;

import de.saphijaga.spoozer.persistence.domain.*;
import de.saphijaga.spoozer.persistence.handler.UserPersistenceHandler;
import de.saphijaga.spoozer.test.cucumber.application.config.TestMongoConfig;
import de.saphijaga.spoozer.test.data.TestUserFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by samuel on 17.05.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestMongoConfig.class, UserPersistenceHandler.class})
public class MongoIntegrationTest {
    @Autowired
    private MongoOperations mongo;

    @Before
    public void setUp() throws Exception {
        dropAll();
        testSetup();
    }

    @After
    public void tearDown() throws Exception {
        dropAll();
    }

    private void testSetup() {
        mongo.insert(TestUserFactory.testUser());
        // TODO add all test domain objects
    }

    protected void dropAll() {
        mongo.dropCollection(Account.class);
        mongo.dropCollection(History.class);
        mongo.dropCollection(HTrack.class);
        mongo.dropCollection(Playlist.class);
        mongo.dropCollection(Properties.class);
        mongo.dropCollection(SoundcloudAccount.class);
        mongo.dropCollection(SpotifyAccount.class);
        mongo.dropCollection(Track.class);
        mongo.dropCollection(User.class);
    }

    protected User findUser(String id) {
        return find(id, User.class);
    }

    // TODO add methods to find domain objects in database

    private <T> T find(String id, Class<T> domainClass) {
        return mongo.findOne(new Query(where("_id").is(id)), domainClass);
    }
}