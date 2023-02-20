package jpastudy.jpashop;

import jpastudy.jpashop.domain.*;
import jpastudy.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct // 빈들을 다 띄운 후 아래 코드 동작
    public void init() {
        initService.dbInit1(); // 여기에 init 코드 바로 넣으면 트랜잭션이 잘 동작 안됨.
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "강북구", "1111");
            em.persist(member);

            Book book1 = createBook("SPRING1", 30000, 1000);
            em.persist(book1);

            Book book2 = createBook("SPRING2", 20000, 500);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 40);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 50);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "인천", "계양구", "2222");
            em.persist(member);

            Book book1 = createBook("JPA1", 15000, 400);
            em.persist(book1);

            Book book2 = createBook("JPA2", 20000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 15000, 20);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 30);
            Order order = Order.createOrder(member, createDelivery(member), orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setAllData(name, price, stockQuantity, "AAA", "248bun");
            return book;
        }

        private Member createMember(String user, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(user);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

    }
}
