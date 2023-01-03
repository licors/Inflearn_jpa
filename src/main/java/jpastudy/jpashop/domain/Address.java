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

    // 기본생성자 못쓰도록 protected 로 선언
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
