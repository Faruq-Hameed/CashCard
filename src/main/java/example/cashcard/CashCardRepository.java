package example.cashcard;

import org.springframework.data.repository.CrudRepository;
//automatically generate the CRUD methods that we need to interact with a database.
interface CashCardRepository extends CrudRepository<CashCard, Long> { //CrudRepository is an interface supplied by Spring Data.

    
}
