package jpastudy.jpashop.form;

import lombok.Getter;

@Getter
public class ItemForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    public void createItemForm(Long id, String name, int price, int stockQuantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
