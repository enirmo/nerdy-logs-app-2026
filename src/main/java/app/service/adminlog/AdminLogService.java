package app.service.adminlog;

import app.model.entity.adminlog.AdminLog;
import app.model.entity.user.User;
import app.repository.adminlog.AdminLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminLogService {

    private final AdminLogRepository adminLogRepository;

    public AdminLogService(AdminLogRepository adminLogRepository) {
        this.adminLogRepository = adminLogRepository;
    }

    public void logAction(String action) {
        AdminLog adminLog = AdminLog.builder()
                .action(action)
                .createdOn(LocalDateTime.now())
                .build();

        adminLogRepository.save(adminLog);
    }

    public List<AdminLog> findAll() {
        return adminLogRepository.findAllByOrderByCreatedOnDesc();
    }

    public List<AdminLog> searchLogs(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }

        return adminLogRepository.findByActionContainingIgnoreCaseOrderByCreatedOnDesc(query);
    }
}
