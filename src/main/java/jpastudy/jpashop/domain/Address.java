package jpastudy.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //여기와 멤버에서 둘 중에 하나만 선언해도 되지만 보통 2군데 다 작상
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 규약, 함부로 new 사용해서 기본생성자 못쓰도록 protected 로 선언
    protected Address() {
    }

    // 주소 변경시에서는 객체 새로 생성해서 대체하기
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
