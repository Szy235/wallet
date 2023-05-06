package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.ExpenseCategory;
import java.util.List;
import java.util.Optional;

public class ExpenseCatManager {

    private final SessionFactory sessionFactory;

    public ExpenseCatManager(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public List<ExpenseCategory> getExpenseCategories() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<ExpenseCategory> query = session.createQuery("FROM ExpenseCategory", ExpenseCategory.class);
            List<ExpenseCategory> expenseCategories = query.list();
            session.getTransaction().commit();
            return expenseCategories;
        }
    }

    public Optional<ExpenseCategory> getExpenseCategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(ExpenseCategory.class, code));
        }
    }

    public void addExpenseCategory(ExpenseCategory expenseCategory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(expenseCategory);
            session.getTransaction().commit();
        }
    }

    public void updateExpenseCategory(ExpenseCategory expenseCategory) {
        try (Session session = sessionFactory.openSession()) {
            getExpenseCategory(expenseCategory.getCode()).ifPresent(existingExpenseCategory -> {
                session.getTransaction().begin();
                existingExpenseCategory.setName(expenseCategory.getName());
                session.update(existingExpenseCategory);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteExpenseCategory(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ExpenseCategory existingExpenseCategory = session.load(ExpenseCategory.class, code);
            session.delete(existingExpenseCategory);
            session.getTransaction().commit();
        }
    }

    public void deleteExpenseCategories() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM ExpenseCategory").executeUpdate();
            session.getTransaction().commit();
        }
    }
}