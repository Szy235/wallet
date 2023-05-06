package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.LongTermInvestment;
import java.util.List;
import java.util.Optional;

public class LongTermInvestManager {

    private final SessionFactory sessionFactory;

    public LongTermInvestManager(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public List<LongTermInvestment> getLongTermInvestments() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<LongTermInvestment> query = session.createQuery("FROM LongTermInvestment", LongTermInvestment.class);
            List<LongTermInvestment> longTermInvestments = query.list();
            session.getTransaction().commit();
            return longTermInvestments;
        }
    }

    public Optional<LongTermInvestment> getLongTermInvestment(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(LongTermInvestment.class, code));
        }
    }

    public void addLongTermInvestment(LongTermInvestment longTermInvestment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(longTermInvestment);
            session.getTransaction().commit();
        }
    }

    public void updateLongTermInvestment(LongTermInvestment longTermInvestment) {
        try (Session session = sessionFactory.openSession()) {
            getLongTermInvestment(longTermInvestment.getCode()).ifPresent(existingLongTermInvestment -> {
                session.getTransaction().begin();
                existingLongTermInvestment.setName(longTermInvestment.getName());
                session.update(existingLongTermInvestment);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteLongTermInvestment(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            LongTermInvestment existingLongTermInvestment = session.load(LongTermInvestment.class, code);
            session.delete(existingLongTermInvestment);
            session.getTransaction().commit();
        }
    }

    public void deleteLongTermInvestments() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM LongTermInvestment").executeUpdate();
            session.getTransaction().commit();
        }
    }
}