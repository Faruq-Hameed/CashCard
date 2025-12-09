package example.cashcard;

// import org.springframework.data.annotation.Id;/
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

// public class CashCard {
//     private static long counter = 0; // shared across all objects
//     private Long id;
//     private double amount;

//     public CashCard(Long id, double amount) {
//         this.id = id;
//         this.amount = amount;
//     }

//     public double getAmount() {
//         return this.amount;
//     }

//     public void addMoney(double amount) {
//         this.amount += amount;
//     }

//     public void removeMoney(double amount) {
//         double finalAmount = this.amount - amount;
//         if (finalAmount < 0) {
//             return;
//         }
//         this.amount -= amount;
//     }

//     public Long getId() {
//         return id;
//     }
// }

// @Table("cash_card")
// public class CashCard {
//     @Id
//     private Long id;

//     @Column("amount")
//     private Double amount;
// }

@Entity
public record CashCard(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        Double amount) {
        }


// @Table("cash_cards")
// public class CashCard {
//     @Id
//     private Long id;

//     @Column("balance")
//     private Double amount;

//     // constructor
//     public CashCard(Long id, Double amount) {
//         this.id = id;
//         this.amount = amount;
//     }

//     // getters
//     public Long getId() { return id; }
//     public Double getAmount() { return amount; }

//     // equals, hashCode, toString (boilerplate)
// }

