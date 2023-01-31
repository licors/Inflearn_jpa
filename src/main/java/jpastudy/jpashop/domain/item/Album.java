package jpastudy.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item{

    private String artist;
    private String etc;

    public void create(String name, int price, int stockQuantity, String artist, String etc) {
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }
}
