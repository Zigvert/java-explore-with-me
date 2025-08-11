import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;

public class StatsClientTest {

    @Test
    void testSaveHit() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        StatsClient client = new StatsClient(restTemplate);

        EndpointHitDto dto = new EndpointHitDto("ewm", "/events", "192.168.0.1", "2025-08-11T12:00:00");

        client.saveHit(dto);

        verify(restTemplate, times(1)).postForEntity(anyString(), eq(dto), eq(Void.class));
    }
}
