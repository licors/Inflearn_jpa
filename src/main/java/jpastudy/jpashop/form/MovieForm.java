package jpastudy.jpashop.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String director;
    private String actor;

    public void createMovieForm(Long id, String name, int price, int stockQuantity, String director, String actor){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.director = director;
        this.actor = actor;
    }
}
