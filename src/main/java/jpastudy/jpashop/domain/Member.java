package jpastudy.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter // 가능하면 필요한 것만 setter 열어놓고, 나머지는 값을 세팅하는 별도의 메소드 넣는 것이 유지보수에 효율적
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // order 테이블에 있는 member 와 맵핑한다는 의미, 현재 이 클래스는 order 클래스에서 맵핑된 거울이란 의미
    // => 여기서 값을 변경해도 db 변경 안됨
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
