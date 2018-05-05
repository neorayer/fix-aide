package net.fixfixt.fixaide.repository;

import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.model.DataDict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class DataDictRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DataDictRepository repository;

    @Test
    public void save() {
        // model
        DataDict dataDict = new DataDict();
        dataDict.setUuid("1223");
        dataDict.setName("name111");

        // save
        repository.save(dataDict);

        // find
        Iterable<DataDict> all = repository.findAll();
        DataDict found = all.iterator().next();

        // assert
        assertEquals(dataDict, found);
    }

}
