package pl.wsb.programowaniejava.wallet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "expense_subcategories")
public class ExpenseSubcategory {

    @Id
    @Column(name = "code", length = 6)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne
    private ExpenseCategory expense_category;

    public ExpenseSubcategory(String code) {
        this.code = code;
    }
}