package app.item;

import app.exceptions.ItemNotFoundException;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.repository.item.ItemRepository;
import app.service.adminlog.AdminLogService;
import app.service.item.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import app.model.entity.item.Genre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static app.messages.ErrorMessages.ITEM_ALREADY_ADDED;
import static app.messages.ErrorMessages.ITEM_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private AdminLogService adminLogService;

    @InjectMocks
    private ItemService itemService;


    // Create item tests
    @Test
    void createItemCreatesItemWhenItemDoesNotExist() {

        ItemRequest request = ItemRequest.builder()
                .itemName("Storm of the Century")
                .medium(Medium.BOOK)
                .genre(Genre.HORROR)
                .description("Description")
                .pictureCover("cover.jpg")
                .releaseYear(1999)
                .build();

        when(itemRepository.findByName("Storm of the Century"))
                .thenReturn(Optional.empty());

        itemService.createItem(request);

        ArgumentCaptor<Item> itemCaptor =
                ArgumentCaptor.forClass(Item.class);

        verify(itemRepository).save(itemCaptor.capture());

        Item savedItem = itemCaptor.getValue();

        assertEquals("Storm of the Century", savedItem.getName());
        assertEquals(Medium.BOOK, savedItem.getMediumType());
        assertEquals(Genre.HORROR, savedItem.getGenre());
        assertEquals("Description", savedItem.getDescription());
        assertEquals("cover.jpg", savedItem.getPictureCover());
        assertEquals(1999, savedItem.getReleaseYear());
    }

    @Test
    void createItemThrowsExceptionWhenItemAlreadyExists() {

        ItemRequest request = ItemRequest.builder()
                .itemName("Storm of the Century")
                .medium(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        Item existingItem = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        when(itemRepository.findByName("Storm of the Century"))
                .thenReturn(Optional.of(existingItem));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> itemService.createItem(request)
        );

        assertEquals(
                String.format(ITEM_ALREADY_ADDED, "Storm of the Century"),
                exception.getMessage()
        );

        verify(itemRepository, never()).save(any(Item.class));
    }


    // Get item tests
    @Test
    void getItemReturnsItemWhenItemExists() {

        UUID itemId = UUID.randomUUID();

        Item item = Item.builder()
                .id(itemId)
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(item));

        Item result = itemService.getItem(itemId);

        assertEquals(item, result);
    }

    @Test
    void getItemThrowsExceptionWhenItemDoesNotExist() {

        UUID itemId = UUID.randomUUID();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.getItem(itemId)
        );

        assertEquals(ITEM_NOT_FOUND, exception.getMessage());
    }


    // Get all items test
    @Test
    void getAllItemsReturnsAllItems() {

        Item book = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        Item game = Item.builder()
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        Item movie = Item.builder()
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .build();

        Item series = Item.builder()
                .name("The Rookie")
                .mediumType(Medium.SERIES)
                .genre(Genre.CRIME)
                .build();

        Item anime = Item.builder()
                .name("Frieren")
                .mediumType(Medium.ANIME)
                .genre(Genre.FANTASY)
                .build();

        List<Item> items = List.of(
                book,
                game,
                movie,
                series,
                anime
        );

        when(itemRepository.findAll())
                .thenReturn(items);

        List<Item> result = itemService.getAllItems();

        assertEquals(items, result);
    }


    // Get items by medium test
    @Test
    void getItemsByMediumTypeReturnsOnlyMatchingItems() {

        Item book = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        Item game = Item.builder()
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        Item movie = Item.builder()
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .build();

        when(itemRepository.findAll())
                .thenReturn(List.of(book, game, movie));

        List<Item> result =
                itemService.getItemsByMediumType(Medium.BOOK);

        assertEquals(List.of(book), result);
    }


    // Update item tests
    @Test
    void updateItemChangesDetailsAndSavesItem() {

        UUID itemId = UUID.randomUUID();

        Item item = Item.builder()
                .id(itemId)
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .description("Old description")
                .pictureCover("old-cover.jpg")
                .releaseYear(2001)
                .build();

        ItemRequest request = ItemRequest.builder()
                .itemName("The Rookie")
                .medium(Medium.SERIES)
                .genre(Genre.CRIME)
                .description("New description")
                .pictureCover("rookie-cover.jpg")
                .releaseYear(2018)
                .build();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(item));

        itemService.updateItem(itemId, request);

        assertEquals("The Rookie", item.getName());
        assertEquals(Medium.SERIES, item.getMediumType());
        assertEquals(Genre.CRIME, item.getGenre());
        assertEquals("New description", item.getDescription());
        assertEquals("rookie-cover.jpg", item.getPictureCover());
        assertEquals(2018, item.getReleaseYear());

        verify(itemRepository).save(item);
    }

    @Test
    void updateItemThrowsExceptionWhenItemDoesNotExist() {

        UUID itemId = UUID.randomUUID();

        ItemRequest request = ItemRequest.builder()
                .itemName("Frieren")
                .medium(Medium.ANIME)
                .genre(Genre.FANTASY)
                .build();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.updateItem(itemId, request)
        );

        assertEquals(ITEM_NOT_FOUND, exception.getMessage());

        verify(itemRepository, never()).save(any(Item.class));
    }


    // Delete item tests
    @Test
    void deleteItemDeletesItemAndLogsAction() {

        UUID itemId = UUID.randomUUID();

        Item item = Item.builder()
                .id(itemId)
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(item));

        itemService.deleteItem(itemId);

        verify(itemRepository).delete(item);

        verify(adminLogService).logAction(
                "Deleted item \"Hollow Knight\" from library."
        );
    }

    @Test
    void deleteItemThrowsExceptionWhenItemDoesNotExist() {

        UUID itemId = UUID.randomUUID();

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(
                ItemNotFoundException.class,
                () -> itemService.deleteItem(itemId)
        );

        assertEquals(ITEM_NOT_FOUND, exception.getMessage());

        verify(itemRepository, never()).delete(any(Item.class));
        verify(adminLogService, never()).logAction(anyString());
    }


    // Search by medium tests
    @Test
    void searchByMediumReturnsMatchingMediumWhenSearchIsBlank() {

        Item book = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        Item game = Item.builder()
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        Item movie = Item.builder()
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .build();

        when(itemRepository.findAll())
                .thenReturn(List.of(book, game, movie));

        List<Item> result =
                itemService.searchByMedium(Medium.BOOK, "");

        assertEquals(List.of(book), result);
    }

    @Test
    void searchByMediumReturnsSearchResultsWhenSearchIsProvided() {

        Item anime = Item.builder()
                .name("Frieren")
                .mediumType(Medium.ANIME)
                .genre(Genre.FANTASY)
                .build();

        List<Item> items = List.of(anime);

        when(itemRepository.findByMediumTypeAndNameContainingIgnoreCase(
                Medium.ANIME,
                "fri"
        )).thenReturn(items);

        List<Item> result =
                itemService.searchByMedium(Medium.ANIME, "fri");

        assertEquals(items, result);
    }


    // Search all items tests
    @Test
    void searchItemsReturnsLatestThreeWhenKeywordIsBlank() {

        Item book = Item.builder()
                .name("Storm of the Century")
                .mediumType(Medium.BOOK)
                .genre(Genre.HORROR)
                .build();

        Item game = Item.builder()
                .name("Hollow Knight")
                .mediumType(Medium.GAME)
                .genre(Genre.ACTION)
                .build();

        Item movie = Item.builder()
                .name("Harry Potter")
                .mediumType(Medium.MOVIE)
                .genre(Genre.FANTASY)
                .build();

        List<Item> items = List.of(
                book,
                game,
                movie
        );

        when(itemRepository.findTop3ByOrderByIdDesc())
                .thenReturn(items);

        List<Item> result = itemService.searchItems("");

        assertEquals(items, result);
    }

    @Test
    void searchItemsReturnsMatchingItemsWhenKeywordIsProvided() {

        Item series = Item.builder()
                .name("The Rookie")
                .mediumType(Medium.SERIES)
                .genre(Genre.CRIME)
                .build();

        List<Item> items = List.of(series);

        when(itemRepository.findByNameContainingIgnoreCase("rookie"))
                .thenReturn(items);

        List<Item> result =
                itemService.searchItems("rookie");

        assertEquals(items, result);
    }


}