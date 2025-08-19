import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;

public class StatsClientTest {

    @Test
    void testSaveHit() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        StatsClient client = new StatsClient(restTemplate);

        // Создаем LocalDateTime вместо строки
        LocalDateTime timestamp = LocalDateTime.parse("2025-08-11 12:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        EndpointHitDto dto = new EndpointHitDto("ewm", "/events", "192.168.0.1", timestamp);

        client.saveHit(dto);

        verify(restTemplate, times(1)).postForEntity(anyString(), eq(dto), eq(Void.class));
    }
}
