package app.service.adminlog;

import app.model.entity.adminlog.AdminLog;
import app.repository.adminlog.AdminLogRepository;
import app.service.adminlog.AdminLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminLogServiceTest {

    @Mock
    private AdminLogRepository adminLogRepository;

    @InjectMocks
    private AdminLogService adminLogService;


    // Find all logs tests
    @Test
    void findAllReturnsAllLogs() {

        AdminLog log1 = AdminLog.builder()
                .action("Deleted user user")
                .build();

        AdminLog log2 = AdminLog.builder()
                .action("Deleted item \"Hollow Knight\" from library.")
                .build();

        List<AdminLog> logs = List.of(log1, log2);

        when(adminLogRepository.findAllByOrderByCreatedOnDesc())
                .thenReturn(logs);

        List<AdminLog> result = adminLogService.findAll();

        assertEquals(logs, result);
    }


    // Log action tests
    @Test
    void logActionCreatesAndSavesLog() {

        String action = "Deleted user user";

        adminLogService.logAction(action);

        ArgumentCaptor<AdminLog> logCaptor =
                ArgumentCaptor.forClass(AdminLog.class);

        verify(adminLogRepository).save(logCaptor.capture());

        AdminLog savedLog = logCaptor.getValue();

        assertEquals(action, savedLog.getAction());
        assertNotNull(savedLog.getCreatedOn());
    }


    // Search logs tests
    @Test
    void searchLogsReturnsAllLogsWhenQueryIsBlank() {

        AdminLog log1 = AdminLog.builder()
                .action("Deleted user user")
                .build();

        AdminLog log2 = AdminLog.builder()
                .action("Deleted item \"Hollow Knight\" from library.")
                .build();

        List<AdminLog> logs = List.of(log1, log2);

        when(adminLogRepository.findAllByOrderByCreatedOnDesc())
                .thenReturn(logs);

        List<AdminLog> result =
                adminLogService.searchLogs("");

        assertEquals(logs, result);
    }

    @Test
    void searchLogsReturnsMatchingLogsWhenQueryIsProvided() {

        AdminLog log = AdminLog.builder()
                .action("Deleted item \"Hollow Knight\" from library.")
                .build();

        List<AdminLog> logs = List.of(log);

        when(adminLogRepository
                .findByActionContainingIgnoreCaseOrderByCreatedOnDesc(
                        "Hollow Knight"
                ))
                .thenReturn(logs);

        List<AdminLog> result =
                adminLogService.searchLogs("Hollow Knight");

        assertEquals(logs, result);
    }
}