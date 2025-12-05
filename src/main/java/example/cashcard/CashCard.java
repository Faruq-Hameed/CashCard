package example.cashcard;

import org.springframework.data.annotation.Id;

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

record CashCard(@Id Long id, Double amount) {
}