package jpastudy.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
public class Movie extends Item{

    private String director;
    private String actor;

    public Movie setAllData(String name, int price, int stockQuantity, String director, String actor) {
        this.setName(name);
        this.setPrice(price);
        this.setStockQuantity(stockQuantity);
        this.director = director;
        this.actor = actor;
        return this;
    }
}
