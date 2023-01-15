package jpastudy.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // foreign key 있는 클래스를 주인으로 삼는 것이 좋다.
    @ManyToOne(fetch = LAZY) //  xxxToOne : 기본적으로 EAGER 이므로 n+1 문제 해결을 위해 lazy 로 설정
    @JoinColumn(name = "member_id")  // foreign key
    private Member member;

    /*
     컬렉션은 필드에서 초기화하는 것이 안전 (null 문제, 하이버네이트가 엔티티 영속화 할 때 내장 컬렉션으로 변경하는데
     임의의 메서드에서 컬렉션 생성한다면 하이버네이트 내부 동작에 문제가 발생할 수 있음
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 1:1 관계에서는 많이 접근하는 테이블에 FK 를 넣는 경우 효율적
    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 자바8 부터 권장, date 는 맵핑을 따로 해야하지만 LocalDateTime 은 하이버네이트에서 자동으로 맵핑됨
    /*
     테이블 컬럼명 기본 생성 전략대로 변경한다면 order_date
     SpringPhysicalNamingStrategy 오버라이딩 하면 네이밍 전략 수정 가능
     */
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL 숫자형은 중간에 추가하는 경우 꼬이는 경우 발생할 수 있음
    private OrderStatus status;

    /**
     연관관계 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 생성 메서드
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem: orderItems
             ) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * 비지니스 로직
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems
             ) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem: orderItems
        ) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
