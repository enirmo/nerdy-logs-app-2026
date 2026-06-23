package app.repository.item;

import app.model.entity.item.Item;
import app.model.entity.item.Medium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Optional<Item> findByName(String name);

    List<Item> findByMediumTypeAndNameContainingIgnoreCase(Medium mediumType, String name);

    List<Item> findTop3ByOrderByIdDesc();

    List<Item> findByNameContainingIgnoreCase(String keyword);
}
