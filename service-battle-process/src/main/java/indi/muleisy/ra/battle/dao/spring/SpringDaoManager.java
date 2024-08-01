package indi.muleisy.ra.battle.dao.spring;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SpringDaoManager {

    public static final SpringDaoManager INSTANCE = new SpringDaoManager();

    private WeaponDao weaponDao;

    private KnifeDao knifeDao;

    private SupportDao supportDao;

    private GoodDao goodDao;


    private SpringDaoManager() {}

    @Autowired
    public void initialize(WeaponDao weaponDao, KnifeDao knifeDao, SupportDao supportDao, GoodDao goodDao) {
        this.weaponDao = weaponDao;
        this.knifeDao = knifeDao;
        this.supportDao = supportDao;
        this.goodDao = goodDao;
    }
}
