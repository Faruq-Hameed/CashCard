package example.cashcard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController() // make th class a RestController Component capable of handling HTTP requests
@RequestMapping("/cashcards") // a companion to @RestController that indicates which address requests must
                              // have to access this Controller.
public class CashCardController {
    private Map<Long, CashCard> cashCards;

    public CashCardController() {
        this.cashCards = new HashMap<>();
    }

    @GetMapping("/{cashCardId}") // @GetMapping marks a method as a handler method. GET requests
    private ResponseEntity<CashCard> findById(@PathVariable Long cashCardId) {
        CashCard cashCard = new CashCard(99L, 123.45);
        return ResponseEntity.ok(cashCard);
    }
}
