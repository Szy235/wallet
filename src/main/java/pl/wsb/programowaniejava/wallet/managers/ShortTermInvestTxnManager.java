package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Currency;
import pl.wsb.programowaniejava.wallet.entities.ShortTermInvestment;
import pl.wsb.programowaniejava.wallet.entities.ShortTermInvestmentTransaction;
import pl.wsb.programowaniejava.wallet.entities.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ShortTermInvestTxnManager {

    private final SessionFactory sessionFactory;

    public ShortTermInvestTxnManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ShortTermInvestmentTransaction> getShortTermInvestmentTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<ShortTermInvestmentTransaction> query = session.createQuery("FROM ShortTermInvestmentTransaction", ShortTermInvestmentTransaction.class);
            List<ShortTermInvestmentTransaction> shortTermInvestmentTransactions = query.list();
            session.getTransaction().commit();
            return shortTermInvestmentTransactions;
        }
    }

    public Optional<ShortTermInvestmentTransaction> getShortTermInvestmentTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(ShortTermInvestmentTransaction.class, id));
        }
    }

    public Optional<Integer> addShortTermInvestmentTransaction(String name, LocalDate date, BigDecimal amount, Currency currency, ShortTermInvestment short_term_investment, Person person) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new ShortTermInvestmentTransaction(name, date, amount, currency, short_term_investment, person));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public void updateShortTermInvestmentTransaction(ShortTermInvestmentTransaction shortTermInvestmentTransaction, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ShortTermInvestmentTransaction existingShortTermInvestmentTransaction = session.load(ShortTermInvestmentTransaction.class, id);
            existingShortTermInvestmentTransaction.setName(shortTermInvestmentTransaction.getName());
            existingShortTermInvestmentTransaction.setDate(shortTermInvestmentTransaction.getDate());
            existingShortTermInvestmentTransaction.setAmount(shortTermInvestmentTransaction.getAmount());
            session.update(existingShortTermInvestmentTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteShortTermInvestmentTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            ShortTermInvestmentTransaction existingShortTermInvestmentTransaction = session.load(ShortTermInvestmentTransaction.class, id);
            session.delete(existingShortTermInvestmentTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteShortTermInvestmentTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM ShortTermInvestmentTransaction").executeUpdate();
            session.getTransaction().commit();
        }
    }
}