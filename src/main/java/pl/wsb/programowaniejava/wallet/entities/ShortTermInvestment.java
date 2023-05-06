package pl.wsb.programowaniejava.wallet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "short_term_investments")
public class ShortTermInvestment {

    @Id
    @Column(name = "code", length = 5)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    public ShortTermInvestment(String code) {
        this.code = code;
    }
}