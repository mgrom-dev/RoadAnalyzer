package ru.mgrom.roadanalyzer.service;

import org.springframework.stereotype.Service;

import ru.mgrom.roadanalyzer.model.Info;
import ru.mgrom.roadanalyzer.repository.InfoRepository;

@Service
public class InfoService extends BaseService<Info, InfoRepository> implements ServiceInterface<Info> {

}
