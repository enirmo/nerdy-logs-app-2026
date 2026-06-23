package app.repository.adminlog;

import app.model.entity.adminlog.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdminLogRepository extends JpaRepository<AdminLog, UUID> {
    List<AdminLog> findAllByOrderByCreatedOnDesc();

    List<AdminLog> findByActionContainingIgnoreCaseOrderByCreatedOnDesc(String action);
}
