package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Person;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PersonManager {

    private final SessionFactory sessionFactory;

    public PersonManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> getPersons() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Person> query = session.createQuery("FROM Person", Person.class);
            List<Person> persons = query.list();
            session.getTransaction().commit();
            return persons;
        }
    }

    public Optional<Person> getPerson(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Person.class, id));
        }
    }

    public Optional<Integer> addPerson(String firstName, String lastName, LocalDate dateOfBirth, String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new Person(firstName, lastName, dateOfBirth, email));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

   public void updatePerson(Person person, int id) {
        try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                Person existingPerson = session.load(Person.class, id);
                existingPerson.setFirstName(person.getFirstName());
                existingPerson.setLastName(person.getLastName());
                existingPerson.setDateOfBirth(person.getDateOfBirth());
                existingPerson.setEmail(person.getEmail());
                session.update(existingPerson);
                session.getTransaction().commit();
            }
    }

    public void deletePerson(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Person existingPerson = session.load(Person.class, id);
            session.delete(existingPerson);
            session.getTransaction().commit();
        }
    }

    public void deletePersons() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM Person").executeUpdate();
            session.getTransaction().commit();
        }
    }
}