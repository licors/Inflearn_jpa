package jpastudy.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // foreign key 있는 클래스를 주인으로 삼는 것이 좋다.
    @ManyToOne
    @JoinColumn(name = "member_id")  // foreign key
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 1:1 관계에서는 많이 접근하는 테이블에 FK 를 넣는 경우 효율적
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 자바8 부터 권장, date 는 맵핑을 따로 해야하지만 LocalDateTime 은 하이버네이트에서 자동으로 맵핑됨
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL 숫자형은 중간에 추가하는 경우 꼬이는 경우 발생할 수 있음
    private OrderStatus orderStatus;
}
