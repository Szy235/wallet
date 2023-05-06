package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.ShortTermInvestment;
import java.util.List;
import java.util.Optional;

public class ShortTermInvestManager {

    private final SessionFactory sessionFactory;

    public ShortTermInvestManager(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public List<ShortTermInvestment> getShortTermInvestments() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<ShortTermInvestment> query = session.createQuery("FROM ShortTermInvestment", ShortTermInvestment.class);
            List<ShortTermInvestment> shortTermInvestments = query.list();
            session.getTransaction().commit();
            return shortTermInvestments;
        }
    }

    public Optional<ShortTermInvestment> getShortTermInvestment(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(ShortTermInvestment.class, code));
        }
    }

    public void addShortTermInvestment(ShortTermInvestment shortTermInvestment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(shortTermInvestment);
            session.getTransaction().commit();
        }
    }

    public void updateShortTermInvestment(ShortTermInvestment shortTermInvestment) {
        try (Session session = sessionFactory.openSession()) {
            getShortTermInvestment(shortTermInvestment.getCode()).ifPresent(existingShortTermInvestment -> {
                session.getTransaction().begin();
                existingShortTermInvestment.setName(shortTermInvestment.getName());
                session.update(existingShortTermInvestment);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteShortTermInvestment(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ShortTermInvestment existingShortTermInvestment = session.load(ShortTermInvestment.class, code);
            session.delete(existingShortTermInvestment);
            session.getTransaction().commit();
        }
    }

    public void deleteShortTermInvestments() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM ShortTermInvestment").executeUpdate();
            session.getTransaction().commit();
        }
    }
}