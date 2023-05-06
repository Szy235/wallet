package pl.wsb.programowaniejava.wallet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "expense_transactions")
public class ExpenseTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "amount", nullable = false, precision = 11, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private ExpenseSubcategory expense_subcategory;

    @ManyToOne
    private Person person;

    public ExpenseTransaction(String name, LocalDate date, BigDecimal amount, Currency currency, ExpenseSubcategory expense_subcategory, Person person) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.expense_subcategory = expense_subcategory;
        this.person = person;
    }
}