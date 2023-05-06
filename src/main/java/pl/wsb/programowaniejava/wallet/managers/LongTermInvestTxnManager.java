package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Currency;
import pl.wsb.programowaniejava.wallet.entities.LongTermInvestment;
import pl.wsb.programowaniejava.wallet.entities.LongTermInvestmentTransaction;
import pl.wsb.programowaniejava.wallet.entities.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LongTermInvestTxnManager {

    private final SessionFactory sessionFactory;

    public LongTermInvestTxnManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<LongTermInvestmentTransaction> getLongTermInvestmentTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<LongTermInvestmentTransaction> query = session.createQuery("FROM LongTermInvestmentTransaction", LongTermInvestmentTransaction.class);
            List<LongTermInvestmentTransaction> longTermInvestmentTransactions = query.list();
            session.getTransaction().commit();
            return longTermInvestmentTransactions;
        }
    }

    public Optional<LongTermInvestmentTransaction> getLongTermInvestmentTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(LongTermInvestmentTransaction.class, id));
        }
    }

    public Optional<Integer> addLongTermInvestmentTransaction(String name, LocalDate date, BigDecimal amount, Currency currency, LongTermInvestment long_term_investment, Person person) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Integer id = (Integer) session.save(new LongTermInvestmentTransaction(name, date, amount, currency, long_term_investment, person));
            session.getTransaction().commit();
            return Optional.ofNullable(id);
        }
    }

    public void updateLongTermInvestmentTransaction(LongTermInvestmentTransaction longTermInvestmentTransaction, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            LongTermInvestmentTransaction existingLongTermInvestmentTransaction = session.load(LongTermInvestmentTransaction.class, id);
            existingLongTermInvestmentTransaction.setName(longTermInvestmentTransaction.getName());
            existingLongTermInvestmentTransaction.setDate(longTermInvestmentTransaction.getDate());
            existingLongTermInvestmentTransaction.setAmount(longTermInvestmentTransaction.getAmount());
            session.update(existingLongTermInvestmentTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteLongTermInvestmentTransaction(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            LongTermInvestmentTransaction existingLongTermInvestmentTransaction = session.load(LongTermInvestmentTransaction.class, id);
            session.delete(existingLongTermInvestmentTransaction);
            session.getTransaction().commit();
        }
    }

    public void deleteLongTermInvestmentTransactions() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM LongTermInvestmentTransaction").executeUpdate();
            session.getTransaction().commit();
        }
    }
}