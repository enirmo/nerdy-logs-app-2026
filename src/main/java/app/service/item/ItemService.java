package app.service.item;

import app.model.dto.item.ItemRequest;
import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import app.repository.item.ItemRepository;
import app.service.adminlog.AdminLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static app.messages.ErrorMessages.ITEM_ALREADY_ADDED;
import static app.messages.ErrorMessages.ITEM_NOT_FOUND;

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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException(ITEM_NOT_FOUND));

        return item;
    }


    // Check if the item already exists and add it if not
    public void createItem(ItemRequest itemRequest) {
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

        adminLogService.logAction("Added item \"" + item.getName() + "\" to library");
    }



    public Item getItem(UUID itemId) {
        Item item = getItemOrThrow(itemId);

        return item;
    }


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

        adminLogService.logAction("Deleted item \"" + item.getName() + "\" from library.");
    }

    public List<Item> searchByMedium(Medium medium, String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return getItemsByMediumType(medium);
        }

        return itemRepository.findByMediumTypeAndNameContainingIgnoreCase(
                medium,
                searchTerm
        );
    }
}
