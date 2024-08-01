package indi.muleisy.ra.battlesession.dao.spring;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SpringDaoManager {

    public static final SpringDaoManager INSTANCE = new SpringDaoManager();

    private MatchInfoDao matchInfoDao;


    private SpringDaoManager() {}

    @Autowired
    public void initialize(MatchInfoDao matchInfoDao) {
        this.matchInfoDao = matchInfoDao;
    }
}
