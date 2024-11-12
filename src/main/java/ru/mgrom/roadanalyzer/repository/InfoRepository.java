package ru.mgrom.roadanalyzer.repository;

import org.springframework.stereotype.Repository;

import ru.mgrom.roadanalyzer.model.Info;

@Repository
public class InfoRepository extends BaseRepository<Info> implements GenericRepository<Info> {

    public InfoRepository() {
        super(Info.class, "info");
    }

}