package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.IncomeCategory;
import java.util.List;
import java.util.Optional;

public class IncomeCatManager {

    private final SessionFactory sessionFactory;

    public IncomeCatManager(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public List<IncomeCategory> getIncomeCategories() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<IncomeCategory> query = session.createQuery("FROM IncomeCategory", IncomeCategory.class);
            List<IncomeCategory> incomeCategories = query.list();
            session.getTransaction().commit();
            return incomeCategories;
        }
    }

    public Optional<IncomeCategory> getIncomeCategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(IncomeCategory.class, code));
        }
    }

    public void addIncomeCategory(IncomeCategory incomeCategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(incomeCategory);
            session.getTransaction().commit();
        }
    }

    public void updateIncomeCategory(IncomeCategory incomeCategory) {
        try (Session session = sessionFactory.openSession()) {
            getIncomeCategory(incomeCategory.getCode()).ifPresent(existingIncomeCategory -> {
                session.getTransaction().begin();
                existingIncomeCategory.setName(incomeCategory.getName());
                session.update(existingIncomeCategory);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteIncomeCategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            IncomeCategory existingIncomeCategory = session.load(IncomeCategory.class, code);
            session.delete(existingIncomeCategory);
            session.getTransaction().commit();
        }
    }

    public void deleteIncomeCategories() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM IncomeCategory").executeUpdate();
            session.getTransaction().commit();
        }
    }
}