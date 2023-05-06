package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Currency;
import pl.wsb.programowaniejava.wallet.entities.IncomeCategory;
import pl.wsb.programowaniejava.wallet.entities.IncomeTransaction;
import pl.wsb.programowaniejava.wallet.entities.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class IncomeTxnManager {

    private final SessionFactory sessionFactory;

    public IncomeTxnManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<IncomeTransaction> getIncomeTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<IncomeTransaction> query = session.createQuery("FROM IncomeTransaction", IncomeTransaction.class);
            List<IncomeTransaction> incomeTransactions = query.list();
            session.getTransaction().commit();
            return incomeTransactions;
        }
    }

    public Optional<IncomeTransaction> getIncomeTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(IncomeTransaction.class, id));
        }
    }

    public Optional<Integer> addIncomeTransaction(String name, LocalDate date, BigDecimal amount, Currency currency, IncomeCategory income_category, Person person) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new IncomeTransaction(name, date, amount, currency, income_category, person));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public void updateIncomeTransaction(IncomeTransaction incomeTransaction, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            IncomeTransaction existingIncomeTransaction = session.load(IncomeTransaction.class, id);
            existingIncomeTransaction.setName(incomeTransaction.getName());
            existingIncomeTransaction.setDate(incomeTransaction.getDate());
            existingIncomeTransaction.setAmount(incomeTransaction.getAmount());
            session.update(existingIncomeTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteIncomeTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            IncomeTransaction existingIncomeTransaction = session.load(IncomeTransaction.class, id);
            session.delete(existingIncomeTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteIncomeTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM IncomeTransaction").executeUpdate();
            session.getTransaction().commit();
        }
    }
}