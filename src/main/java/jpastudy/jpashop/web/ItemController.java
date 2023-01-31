package jpastudy.jpashop.web;

import jpastudy.jpashop.domain.item.Album;
import jpastudy.jpashop.domain.item.Book;
import jpastudy.jpashop.domain.item.Item;
import jpastudy.jpashop.domain.item.Movie;
import jpastudy.jpashop.form.AlbumForm;
import jpastudy.jpashop.form.BookForm;
import jpastudy.jpashop.form.ItemForm;
import jpastudy.jpashop.form.MovieForm;
import jpastudy.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/book/new")
    public String createBookForm(Model model) {

        model.addAttribute("form", new BookForm());
        return "items/createBookForm";
    }

    @PostMapping(value = "/items/book/new")
    public String createBook(BookForm bookForm) {

        Book book = new Book();
        book.create(
                bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getStockQuantity(),
                bookForm.getAuthor(),
                bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping(value = "/items/album/new")
    public String createAlbumForm(Model model) {

        model.addAttribute("form", new AlbumForm());
        return "items/createAlbumForm";
    }

    @PostMapping(value = "/items/album/new")
    public String createAlbum(AlbumForm albumForm) {

        Album album = new Album();
        album.create(
                albumForm.getName(),
                albumForm.getPrice(),
                albumForm.getStockQuantity(),
                albumForm.getArtist(),
                albumForm.getEtc());

        itemService.saveItem(album);
        return "redirect:/items";
    }

    @GetMapping(value = "/items/movie/new")
    public String createMovieForm(Model model) {

        model.addAttribute("form", new MovieForm());
        return "items/createMovieForm";
    }

    @PostMapping(value = "/items/movie/new")
    public String createMovie(MovieForm movieForm) {

        Movie movie = new Movie();
        movie.create(
                movieForm.getName(),
                movieForm.getPrice(),
                movieForm.getStockQuantity(),
                movieForm.getDirector(),
                movieForm.getActor());

        itemService.saveItem(movie);
        return "redirect:/items";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Item item = itemService.findOne(itemId);
        ItemForm itemForm = new ItemForm();
        itemForm.createItemForm(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStockQuantity());

        model.addAttribute("form", itemForm);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form, @PathVariable("itemId") Long itemId) {

        // 컨트롤러 안에서 마음대로 엔티티 생성하지 마라
//        Book item = new Book();
//        // merge 를 사용하므로 전체 데이터를 set 함
//        item.setId(form.getId());
//        item.setName(form.getName());
//        item.setPrice(form.getPrice());
//        item.setStockQuantity(form.getStockQuantity());
//        item.setAuthor(form.getAuthor());
//        item.setIsbn(form.getIsbn());
//
//        itemService.saveItem(item);

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}
