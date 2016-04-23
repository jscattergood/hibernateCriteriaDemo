package com.example.model;

import com.example.DemoApplication;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="https://github.com/jscattergood">John Scattergood</a> 4/22/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DemoApplication.class})
public class CriteriaTests {
    private static final Logger LOGGER = Logger.getLogger(CriteriaTests.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testCriteria() {
        Session session = sessionFactory.openSession();
        try {
            assertTrue(session.createCriteria(Child.class).list().isEmpty());

            User user1 = new User();
            session.save(user1);

            Father father1 = new Father();
            father1.owner = user1;
            session.save(father1);

            Mother mother1 = new Mother();
            mother1.owner = user1;
            session.save(mother1);

            Child child1 = new Child();
            child1.father = father1;
            child1.mother = mother1;
            session.save(child1);

            Child child2 = new Child();
            child2.father = father1;
            child2.mother = mother1;
            session.save(child2);

            Child child3 = new Child();
            child3.father = father1;
            child3.mother = mother1;
            session.save(child3);
            session.flush();

            List results = session.createCriteria(Child.class)
                    .add(Restrictions.in("id", child1.id, child2.id))
                    .add(Restrictions.eq("isDeleted", false))

                    .createAlias("father", "f")
                    .add(Restrictions.eq("f.isDeleted", false))
                    .setFetchMode("f.owner", FetchMode.SELECT)

                    .createAlias("mother", "m")
                    .add(Restrictions.eq("m.isDeleted", false))
                    .setFetchMode("m.owner", FetchMode.SELECT)
                    .list();
            assertTrue(results.size() == 2);
        }
        finally {
            try {
                sessionFactory.close();
            }
            catch (HibernateException e) {
                LOGGER.error(e);
            }
        }
    }
}
