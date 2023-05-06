package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.ExpenseSubcategory;
import java.util.List;
import java.util.Optional;

public class ExpenseSubcatManager {

    private final SessionFactory sessionFactory;

    public ExpenseSubcatManager(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public List<ExpenseSubcategory> getExpenseSubcategories() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<ExpenseSubcategory> query = session.createQuery("FROM ExpenseSubcategory", ExpenseSubcategory.class);
            List<ExpenseSubcategory> expenseSubcategories = query.list();
            session.getTransaction().commit();
            return expenseSubcategories;
        }
    }

    public Optional<ExpenseSubcategory> getExpenseSubcategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(ExpenseSubcategory.class, code));
        }
    }

    public void addExpenseSubcategory(ExpenseSubcategory expenseSubcategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(expenseSubcategory);
            session.getTransaction().commit();
        }
    }

    public void updateExpenseSubcategory(ExpenseSubcategory expenseSubcategory) {
        try (Session session = sessionFactory.openSession()) {
            getExpenseSubcategory(expenseSubcategory.getCode()).ifPresent(existingExpenseSubcategory -> {
                session.getTransaction().begin();
                existingExpenseSubcategory.setName(expenseSubcategory.getName());
                session.update(existingExpenseSubcategory);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteExpenseSubcategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ExpenseSubcategory existingExpenseSubcategory = session.load(ExpenseSubcategory.class, code);
            session.delete(existingExpenseSubcategory);
            session.getTransaction().commit();
        }
    }

    public void deleteExpenseSubcategories() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM ExpenseSubcategory").executeUpdate();
            session.getTransaction().commit();
        }
    }
}