package app.service.item;

import app.model.dto.item.ItemRequest;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Helper to check if item exists
    private Item getItemOrThrow(UUID itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found."));

        return item;
    }


    // Check if the item already exists and add it if not
    public void createItem(ItemRequest itemRequest) {
        itemRepository.findByName(itemRequest.getItemName())
                .ifPresent(item -> {
                    throw new IllegalArgumentException(itemRequest.getItemName() + " is already added.");
                });

        // Note that description, pictureCover and releaseYear are not mandatory fields, so they can be null
        Item item = Item.builder()
                .name(itemRequest.getItemName())
                .mediumType(itemRequest.getMedium())
                .genre(itemRequest.getGenre())
                .description(itemRequest.getDescription())
                .pictureCover(itemRequest.getPictureCover())
                .releaseYear(itemRequest.getReleaseYear())
                .build();

        itemRepository.save(item);
    }



    public Item getItem(UUID itemId) {
        Item item = getItemOrThrow(itemId);

        return item;
    }


    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();

        return items;
    }


    public List<Item> getItemsByMediaType(Medium mediaType) {
        List<Item> items = getAllItems()
                .stream()
                .filter(item -> item.getMediumType() == mediaType)
                .toList();

        return items;
    }


    public List<Item> searchItems(String keyword) {
        return List.of();
    }


    public void updateItem(UUID itemId, ItemRequest request) {
        Item item = getItemOrThrow(itemId);

        item.setName(request.getItemName());
        item.setMediumType(request.getMedium());
        item.setGenre(request.getGenre());
        item.setDescription(request.getDescription());
        item.setPictureCover(request.getPictureCover());
        item.setReleaseYear(request.getReleaseYear());

        itemRepository.save(item);
    }


    public void deleteItem(UUID itemId) {
        Item item = getItemOrThrow(itemId);

        itemRepository.delete(item);
    }
}
