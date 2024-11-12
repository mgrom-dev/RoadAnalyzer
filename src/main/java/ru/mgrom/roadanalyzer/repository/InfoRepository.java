package ru.mgrom.roadanalyzer.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Query;
import ru.mgrom.roadanalyzer.model.Info;

@Repository
public class InfoRepository extends BaseRepository<Info> implements GenericRepository<Info> {

    public InfoRepository() {
        super(Info.class, "info");
    }

    @Override
    @Transactional
    public boolean update(Info info, String databaseIdentifier) {
        boolean isUpdated = false;
        try {
            String sql = "UPDATE " + databaseIdentifier + "." + tableName
                    + " SET description = :description, type = :type WHERE id = :id";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("key_info", info.getKeyInfo());
            query.setParameter("value_info", info.getValueInfo());
            query.setParameter("id", info.getId());
            query.executeUpdate();
            isUpdated = true;
        } catch (Exception e) {
            System.out.println("error updating Info: " + e.getMessage());
        }
        return isUpdated;
    }

}