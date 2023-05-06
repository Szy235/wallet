package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Currency;
import pl.wsb.programowaniejava.wallet.entities.ExpenseSubcategory;
import pl.wsb.programowaniejava.wallet.entities.ExpenseTransaction;
import pl.wsb.programowaniejava.wallet.entities.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ExpenseTxnManager {

    private final SessionFactory sessionFactory;

    public ExpenseTxnManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ExpenseTransaction> getExpenseTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<ExpenseTransaction> query = session.createQuery("FROM ExpenseTransaction", ExpenseTransaction.class);
            List<ExpenseTransaction> expenseTransactions = query.list();
            session.getTransaction().commit();
            return expenseTransactions;
        }
    }

    public Optional<ExpenseTransaction> getExpenseTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(ExpenseTransaction.class, id));
        }
    }

    public Optional<Integer> addExpenseTransaction(String name, LocalDate date, BigDecimal amount, Currency currency, ExpenseSubcategory expense_subcategory, Person person) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new ExpenseTransaction(name, date, amount, currency, expense_subcategory, person));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public void updateExpenseTransaction(ExpenseTransaction expenseTransaction, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ExpenseTransaction existingExpenseTransaction = session.load(ExpenseTransaction.class, id);
            existingExpenseTransaction.setName(expenseTransaction.getName());
            existingExpenseTransaction.setDate(expenseTransaction.getDate());
            existingExpenseTransaction.setAmount(expenseTransaction.getAmount());
            session.update(existingExpenseTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteExpenseTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ExpenseTransaction existingExpenseTransaction = session.load(ExpenseTransaction.class, id);
            session.delete(existingExpenseTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteExpenseTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM ExpenseTransaction").executeUpdate();
            session.getTransaction().commit();
        }
    }
}