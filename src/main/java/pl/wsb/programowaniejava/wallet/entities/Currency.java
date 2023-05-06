package pl.wsb.programowaniejava.wallet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "currencies")
public class Currency {

    @Id
    @Column(name = "code", length = 3)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "exchange_rate", precision = 5, scale = 4)
    private BigDecimal exchangeRate;

    public Currency(String code) {
        this.code = code;
    }

    public Currency(String name, BigDecimal exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s}", code, name, exchangeRate);
    }
}