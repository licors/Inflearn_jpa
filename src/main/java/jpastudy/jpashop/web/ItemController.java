package jpastudy.jpashop.web;

import jpastudy.jpashop.domain.item.Album;
import jpastudy.jpashop.domain.item.Book;
import jpastudy.jpashop.domain.item.Item;
import jpastudy.jpashop.domain.item.Movie;
import jpastudy.jpashop.form.AlbumForm;
import jpastudy.jpashop.form.BookForm;
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

    @GetMapping(value = "/items/new/book")
    public String createBookForm(Model model) {

        model.addAttribute("form", new BookForm());
        return "items/createBookForm";
    }

    @PostMapping(value = "/items/new/book")
    public String createBook(BookForm bookForm) {

        Book book = new Book();
        book.setAllData(
                bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getStockQuantity(),
                bookForm.getAuthor(),
                bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping(value = "/items/new/album")
    public String createAlbumForm(Model model) {

        model.addAttribute("form", new AlbumForm());
        return "items/createAlbumForm";
    }

    @PostMapping(value = "/items/new/album")
    public String createAlbum(AlbumForm albumForm) {

        Album album = new Album();
        album.setAllData(
                albumForm.getName(),
                albumForm.getPrice(),
                albumForm.getStockQuantity(),
                albumForm.getArtist(),
                albumForm.getEtc());

        itemService.saveItem(album);
        return "redirect:/items";
    }

    @GetMapping(value = "/items/new/movie")
    public String createMovieForm(Model model) {

        model.addAttribute("form", new MovieForm());
        return "items/createMovieForm";
    }

    @PostMapping(value = "/items/new/movie")
    public String createMovie(MovieForm movieForm) {

        Movie movie = new Movie();
        movie.setAllData(
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

        // id 등을 넘겨받는 경우 해당 유저가 권한이 있는지 확인하는 것이 필요할 수도 있다.
        Item findItem = itemService.findOne(itemId);
        if (findItem.getDType().equals("B")) {
            Book findBook = (Book) itemService.findOne(itemId);
            BookForm bookForm = new BookForm();
            bookForm.createBookForm(
                    findBook.getId(),
                    findBook.getName(),
                    findBook.getPrice(),
                    findBook.getStockQuantity(),
                    findBook.getAuthor(),
                    findBook.getIsbn());

            model.addAttribute("form", bookForm);
            return "items/updateBookForm";

        } else if (findItem.getDType().equals("A")) {
            Album findAlbum = (Album) itemService.findOne(itemId);
            AlbumForm albumForm = new AlbumForm();
            albumForm.createAlbumForm(
                    findAlbum.getId(),
                    findAlbum.getName(),
                    findAlbum.getPrice(),
                    findAlbum.getStockQuantity(),
                    findAlbum.getArtist(),
                    findAlbum.getEtc()
            );
            model.addAttribute("form", albumForm);
            return "items/updateAlbumForm";

        } else if (findItem.getDType().equals("M")) {
            Movie findMovie = (Movie) itemService.findOne(itemId);
            MovieForm movieForm = new MovieForm();
            movieForm.createMovieForm(
                    findMovie.getId(),
                    findMovie.getName(),
                    findMovie.getPrice(),
                    findMovie.getStockQuantity(),
                    findMovie.getDirector(),
                    findMovie.getActor()
            );
            model.addAttribute("form", movieForm);
            return "items/updateMovieForm";

        }
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit/book")
    public String updateBook(@ModelAttribute("form") BookForm form, @PathVariable("itemId") Long itemId) {

        // 컨트롤러 안에서 마음대로 엔티티 생성하지 마라
        // 여기서 new 로 생성한 item 은 준영속성 엔티티로 jpa 가 관리하지 않는다.
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

//        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        itemService.updateBook(itemId, new Book().setAllData(
                form.getName(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getAuthor(),
                form.getIsbn()));

        return "redirect:/items";
    }

    @PostMapping(value = "/items/{itemId}/edit/album")
    public String updateAlbum(@ModelAttribute("form") AlbumForm form, @PathVariable("itemId") Long itemId) {

        itemService.updateAlbum(itemId, new Album().setAllData(
                form.getName(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getArtist(),
                form.getEtc()));

        return "redirect:/items";
    }

    @PostMapping(value = "/items/{itemId}/edit/movie")
    public String updateMovie(@ModelAttribute("form") MovieForm form, @PathVariable("itemId") Long itemId) {

        itemService.updateMovie(itemId, new Movie().setAllData(
                form.getName(),
                form.getPrice(),
                form.getStockQuantity(),
                form.getDirector(),
                form.getActor()));

        return "redirect:/items";
    }
}
