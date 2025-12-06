package example.cashcard;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController() // make th class a RestController Component capable of handling HTTP requests
@RequestMapping("/cashcards") // a companion to @RestController that indicates which address requests must
                              // have to access this Controller.
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{cashCardId}") // @GetMapping marks a method as a handler method. GET requests
    private ResponseEntity<CashCard> findById(@PathVariable Long cashCardId) {
        Optional<CashCard> cashCardOptional = this.cashCardRepository.findById(cashCardId);
        if (cashCardOptional.isPresent()) { // know what to do if present
            return ResponseEntity.ok(cashCardOptional.get()); // get the item and return it
        } else {
            return ResponseEntity.notFound().build(); // throw error
        }
        // return this.cashCardRepository.findById(cashCardId)
        // .map(ResponseEntity::ok) //if found, return ok response with the item
        // .orElseGet(() -> ResponseEntity.notFound().build()); //if not found, return
        // not found response
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard() {
        return null;
    }
}
