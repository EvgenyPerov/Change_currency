package ru.skillbox.currency.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Long nominal;

    @Column
    private Double value;

    @Column
    private Long isoNumCode;

    @Column
    private String isoCharCode;

    @Override
    public String toString() {
        return "Currency{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", nominal=" + nominal +
            ", value=" + value +
            ", isoNumCode=" + isoNumCode +
            ", isoCharCode='" + isoCharCode + '\'' +
            '}';
    }
}
