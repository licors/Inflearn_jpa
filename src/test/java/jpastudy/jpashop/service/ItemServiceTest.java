package jpastudy.jpashop.service;

import jpastudy.jpashop.domain.item.Book;
import jpastudy.jpashop.domain.item.Item;
import jpastudy.jpashop.exception.NotEnoughStockException;
import jpastudy.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test(expected = NotEnoughStockException.class)
    public void 아이템_재고_에러() throws Exception {
        //given
        Item item1 = createBook("JPABook", 10000, 20);
//        Item item2 = createBook("JAVABook", 5000, 10);

        //when
        em.flush();
        item1.removeStock(25);

        //then
        fail("need more stock 에러 발생해야 함");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        itemService.saveItem(book);
        return book;
    }
}