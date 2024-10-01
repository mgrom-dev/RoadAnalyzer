package ru.mgrom.roadanalyzer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.dto.SessionResponse;
import ru.mgrom.roadanalyzer.model.Spending;

public interface SpendingRepository extends JpaRepository<Spending, Long>, CustomSpendingRepository {
    // select * from Spending where created_at > $1 and created_at < $2
    List<Spending> findByDateBetween(LocalDate min, LocalDate max);
}

interface CustomSpendingRepository {
    List<Spending> findSpendingsByDateRange(LocalDate min, LocalDate max);
}

@Repository
class CustomSpendingRepositoryImpl implements CustomSpendingRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request; // Автоматически инжектируется Spring

    @Override
    public List<Spending> findSpendingsByDateRange(LocalDate min, LocalDate max) {
        String sessionId = request.getSession().getId();

        // Получаем заголовки текущего запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", request.getHeader("Cookie")); // Передаем куки сессии

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем запрос к API /session-info с заголовками
        ResponseEntity<SessionResponse> response = restTemplate.exchange("http://localhost:8080/session-info",
                HttpMethod.GET, entity,
                SessionResponse.class);

        SessionResponse sessionInfo = response.getBody();

        if (sessionInfo == null || sessionInfo.getDatabaseId() == null) {
            throw new RuntimeException("Не удалось получить информацию о сессии.");
        }

        // Получаем уникальный идентификатор базы данных пользователя
        String databaseIdentifier = sessionInfo.getDatabaseId();

        // Теперь выполняем запрос к таблице Spending в нужной базе данных
        String sql = "SELECT * FROM " + databaseIdentifier + ".spending WHERE created_at BETWEEN :min AND :max";
        TypedQuery<Spending> query = (TypedQuery<Spending>) entityManager.createNativeQuery(sql, Spending.class);
        query.setParameter("min", min);
        query.setParameter("max", max);

        return query.getResultList();
    }
}