package pl.wsb.programowaniejava.wallet.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import pl.wsb.programowaniejava.wallet.entities.Currency;
import java.util.List;
import java.util.Optional;

public class CurrencyManager {

    private final SessionFactory sessionFactory;

    public CurrencyManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Currency> getCurrencies() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Currency> query = session.createQuery("FROM Currency", Currency.class);
            List<Currency> currencies = query.list();
            session.getTransaction().commit();
            return currencies;
        }
    }

    public Optional<Currency> getCurrency(String code) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Currency.class, code));
        }
    }

    public void addCurrency(Currency currency) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(currency);
            session.getTransaction().commit();
        }
    }

    public void updateCurrency(Currency currency) {
        try (Session session = sessionFactory.openSession()) {
            getCurrency(currency.getCode()).ifPresent(existingCurrency -> {
                session.getTransaction().begin();
                existingCurrency.setName(currency.getName());
                existingCurrency.setExchangeRate(currency.getExchangeRate());
                session.update(existingCurrency);
                session.getTransaction().commit();
            });
        }
    }

    public void deleteCurrency(String code) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Currency existingCurrency = session.load(Currency.class, code);
            session.delete(existingCurrency);
            session.getTransaction().commit();
        }
    }

    public void deleteCurrencies() {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createQuery("DELETE FROM Currency").executeUpdate();
            session.getTransaction().commit();
        }
    }
}