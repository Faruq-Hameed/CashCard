package example.cashcard;

import java.net.URI;
import java.security.Principal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController() // make th class a RestController Component capable of handling HTTP requests
@RequestMapping("/cashcards") // a companion to @RestController that indicates which address requests must
                              // have to access this Controller.
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    // @GetMapping("/{cashCardId}") // @GetMapping marks a method as a handler
    // method. GET requests
    // private ResponseEntity<CashCard> findById(@PathVariable Long cashCardId) {
    // Optional<CashCard> cashCardOptional =
    // this.cashCardRepository.findById(cashCardId);
    // if (cashCardOptional.isPresent()) { // know what to do if present
    // return ResponseEntity.ok(cashCardOptional.get()); // get the item and return
    // it
    // } else {
    // return ResponseEntity.notFound().build(); // throw error
    // }
    // // return this.cashCardRepository.findById(cashCardId)
    // // .map(ResponseEntity::ok) //if found, return ok response with the item
    // // .orElseGet(() -> ResponseEntity.notFound().build()); //if not found,
    // return
    // // not found response
    // }

    @GetMapping("/{cashCardId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long cashCardId, Principal principal) {
        CashCard cashCard = findCashCard(cashCardId, principal);
        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }

        // Optional<CashCard> cashCardOptional = Optional.ofNullable(cashCardRepository
        // .findByIdAndOwner(cashCardId, principal.getName()));
        // if (cashCardOptional.isPresent()) { // know what to do if present
        // return ResponseEntity.ok(cashCardOptional.get()); // get the item and return
        // it
        // } else {
        // return ResponseEntity.notFound().build(); // throw error
        // }
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb,
            Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        URI locationOfNewCashCard = ucb.path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build(); // return 201 created response with our built URI
                                                                      // as location header
    }

    // @GetMapping
    // private ResponseEntity<Iterable<CashCard>> findAll() {
    // return ResponseEntity.ok(this.cashCardRepository.findAll());
    // }

    // @GetMapping
    // private ResponseEntity<Iterable<CashCard>> findAll(Pageable pageable) {
    // Page<CashCard> page = this.cashCardRepository.findAll(
    // PageRequest.of(
    // pageable.getPageNumber(), // extracts the page query parameter from the
    // request URI.
    // pageable.getPageSize(), // extracts the size query parameter from the request
    // URI.
    // pageable.getSortOr(
    // Sort.by(Sort.Direction.ASC, "amount") // default sorting by amount in
    // ascending order
    // ) // extracts the sort query parameter from the request URI.
    // ));
    // return ResponseEntity.ok(page.getContent());
    // }

    @GetMapping
    private ResponseEntity<Iterable<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = this.cashCardRepository.findByOwner(
                principal.getName(), // The Principal holds our user's authenticated, authorized information
                PageRequest.of(
                        pageable.getPageNumber(), // extracts the page query parameter from the request URI.
                        pageable.getPageSize(), // extracts the size query parameter from the request URI.
                        pageable.getSortOr(
                                Sort.by(Sort.Direction.ASC, "amount") // default sorting by amount in ascending order
                        ) // extracts the sort query parameter from the request URI.
                ));
        return ResponseEntity.ok(page.getContent());
    }

    // @GetMapping
    // private ResponseEntity<Iterable<CashCard>>
    // findAllCashCards(org.springframework.data.domain.Pageable pageable) {
    // Page<CashCard> page = this.cashCardRepository.findAll(
    // PageRequest.of(pageable.getPageNumber(),
    // pageable.getPageSize(),
    // pageable.getSortOr(
    // Sort.by(Sort.Direction.DESC, "amount"))));

    // return ResponseEntity.ok(page.getContent());

    // // return ResponseEntity.ok(this.cashCardRepository.findAll(
    // // PageRequest.of(0,3, Sort.by(new Sort.Order(Sort.Direction.DESC,
    // "amount")));
    // // ));

    // }

    @PutMapping("/{cashCardId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate,
            Principal principal) {
        CashCard cashCard = findCashCard(requestedId, principal);
        if (cashCard == null) {
            return ResponseEntity.notFound().build();
        }
        CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
        cashCardRepository.save(updatedCashCard);
        return ResponseEntity.noContent().build();
    }

    private CashCard findCashCard(Long requestedId, Principal principal) {
        return cashCardRepository.findByIdAndOwner(requestedId, principal.getName());
    }
}
