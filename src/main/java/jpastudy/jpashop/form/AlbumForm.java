package jpastudy.jpashop.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlbumForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String artist;
    private String etc;

    public void createAlbumForm(Long id, String name, int price, int stockQuantity, String artist, String etc){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.artist = artist;
        this.etc = etc;
    }
}
