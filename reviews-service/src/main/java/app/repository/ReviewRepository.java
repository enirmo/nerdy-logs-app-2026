package app.repository;

import app.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByUserIdAndMediaId(UUID userId, UUID mediaId);
    boolean existsByUserIdAndMediaId(UUID userId, UUID mediaId);
}
