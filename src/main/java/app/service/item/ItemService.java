package app.service.item;

import app.exceptions.ItemNotFoundException;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.repository.item.ItemRepository;
import app.service.adminlog.AdminLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static app.messages.ErrorMessages.ITEM_ALREADY_ADDED;
import static app.messages.ErrorMessages.ITEM_NOT_FOUND;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final AdminLogService adminLogService;

    public ItemService(ItemRepository itemRepository, AdminLogService adminLogService) {
        this.itemRepository = itemRepository;
        this.adminLogService = adminLogService;
    }

    // Helper to check if item exists
    private Item getItemOrThrow(UUID itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));

        return item;
    }


    // Check if the item already exists and add it if not
    @CacheEvict(value = "items", allEntries = true)
    public void createItem(ItemRequest itemRequest) {
        log.info("Creating new item: {}", itemRequest.getItemName());

        itemRepository.findByName(itemRequest.getItemName())
                .ifPresent(item -> {
                    throw new IllegalArgumentException(String.format(ITEM_ALREADY_ADDED, itemRequest.getItemName()));
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
        log.info("Item {} created successfully", item.getId());
    }


    // GET items
    public Item getItem(UUID itemId) {
        Item item = getItemOrThrow(itemId);

        return item;
    }

    @Cacheable("items")
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();

        return items;
    }


    public List<Item> getItemsByMediumType(Medium mediaType) {
        List<Item> items = getAllItems()
                .stream()
                .filter(item -> item.getMediumType() == mediaType)
                .toList();

        return items;
    }


    // Update
    @CacheEvict(value = "items", allEntries = true)
    public void updateItem(UUID itemId, ItemRequest request) {
        log.info("Updating item {}", itemId);

        Item item = getItemOrThrow(itemId);

        item.setName(request.getItemName());
        item.setMediumType(request.getMedium());
        item.setGenre(request.getGenre());
        item.setDescription(request.getDescription());
        item.setPictureCover(request.getPictureCover());
        item.setReleaseYear(request.getReleaseYear());

        itemRepository.save(item);
        log.info("Item {} updated successfully", itemId);
    }

    // Delete
    @CacheEvict(value = "items", allEntries = true)
    public void deleteItem(UUID itemId) {
        log.info("Deleting item {}", itemId);

        Item item = getItemOrThrow(itemId);

        itemRepository.delete(item);

        adminLogService.logAction("Deleted item \"" + item.getName() + "\" from library.");
        log.info("Item {} deleted successfully", itemId);
    }


    // Search
    public List<Item> searchByMedium(Medium medium, String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return getItemsByMediumType(medium);
        }

        return itemRepository.findByMediumTypeAndNameContainingIgnoreCase(
                medium,
                searchTerm
        );
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return itemRepository.findTop3ByOrderByIdDesc();
        }

        return itemRepository.findByNameContainingIgnoreCase(keyword);
    }
}
