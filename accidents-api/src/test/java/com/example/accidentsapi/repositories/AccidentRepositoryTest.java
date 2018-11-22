package com.example.accidentsapi.repositories;


import com.example.accidentsapi.models.Accident;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccidentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccidentRepository accidentRepository;

    @Before
    public void setUp() {
        Accident firstAccident = new Accident(
                "date1",
                "time1",
                "borough1",
                "zip1",
                12345.0,
                -12345.0,
                "test1",
                0,
                0
        );

        Accident secondAccident = new Accident(
                "date2",
                "time2",
                "borough2",
                "zip2",
                123.9,
                -123.9,
                "test2",
                0,
                0
        );

        entityManager.persist(firstAccident);
        entityManager.persist(secondAccident);
        entityManager.flush();
    }

    @Test
    public void findAll_returnsAllAccidents() {
        Iterable<Accident> accidentsFromDb = accidentRepository.findAll();
        assertThat(Iterables.size(accidentsFromDb), is(2));
    }

    @Test
    public void findAll_returnsUserName() {
        Iterable<Accident> accidentsFromDb = accidentRepository.findAll();

        String secondAccidentsDate = Iterables.get(accidentsFromDb, 1).getDate();

        assertThat(secondAccidentsDate, is("date2"));
    }

    @Test
    public void findAll_returnsFirstName() {
        Iterable<Accident> accidentsFromDb = accidentRepository.findAll();

        String secondAccidentsTime = Iterables.get(accidentsFromDb, 1).getTime();

        assertThat(secondAccidentsTime, is("time2"));
    }

    @Test
    public void findAll_returnsLatitude() {
        Iterable<Accident> accidentsFromDb = accidentRepository.findAll();

        Double secondAccidentsLatitude = Iterables.get(accidentsFromDb, 2).getLatitude();
        assertThat(secondAccidentsLatitude, is(123.9));
        System.out.println(accidentsFromDb);
    }
}
