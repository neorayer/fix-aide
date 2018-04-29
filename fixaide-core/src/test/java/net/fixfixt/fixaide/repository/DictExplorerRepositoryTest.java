package net.fixfixt.fixaide.repository;

import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.model.DictExplorer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class DictExplorerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DictExplorerRepository repository;

    @Test
    public void save() {
        // model
        DictExplorer dictExplorer = new DictExplorer();
        dictExplorer.setUuid("1223");
        dictExplorer.setName("name111");

        // save
        repository.save(dictExplorer);

        // find
        Iterable<DictExplorer> all = repository.findAll();
        DictExplorer found = all.iterator().next();

        // assert
        assertEquals(dictExplorer, found);
    }

}
