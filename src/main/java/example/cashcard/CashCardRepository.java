package example.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;//automatically generate the CRUD methods that we need to interact with a database.
interface CashCardRepository extends CrudRepository<CashCard, Long>  { //CrudRepository is an interface supplied by Spring Data.
// interface CashCardRepository extends PagingAndSortingRepository<CashCard, Long>  { //PagingAndSortingRepository extends CrudRepository to provide additional methods to retrieve entities using the pagination and sorting abstraction.
}