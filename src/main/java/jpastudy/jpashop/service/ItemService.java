package jpastudy.jpashop.service;

import jpastudy.jpashop.domain.item.Album;
import jpastudy.jpashop.domain.item.Book;
import jpastudy.jpashop.domain.item.Item;
import jpastudy.jpashop.domain.item.Movie;
import jpastudy.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(id);
        // 업데이트 시 영속성인 상태에서 바꾸면 jpa 에서 감지해서 커밋시 반영해준다.
        findItem.change(name, price, stockQuantity);
    }

    @Transactional
    public Book updateBook(Long id, Book param) {
        Book findBook = (Book) itemRepository.findOne(id);
        findBook.setAllData(param.getName(), param.getPrice(), param.getStockQuantity(), param.getAuthor(), param.getIsbn());
        return findBook;
    }

    @Transactional
    public Album updateAlbum(Long id, Album param) {
        Album findAlbum = (Album) itemRepository.findOne(id);
        findAlbum.setAllData(param.getName(), param.getPrice(), param.getStockQuantity(), param.getArtist(), param.getEtc());
        return findAlbum;
    }

    @Transactional
    public Movie updateMovie(Long id, Movie param) {
        Movie findMovie = (Movie) itemRepository.findOne(id);
        findMovie.setAllData(param.getName(), param.getPrice(), param.getStockQuantity(), param.getDirector(), param.getActor());
        return findMovie;
    }
}
