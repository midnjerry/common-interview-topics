package net.crusader.games.interviewreview.repository;

import net.crusader.games.interviewreview.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByActive(boolean active);

    default List<Person> findActiveRecords(){
        return findAllByActive(true);
    }
}
