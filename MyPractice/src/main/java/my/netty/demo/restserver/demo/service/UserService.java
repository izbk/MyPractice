package my.netty.demo.restserver.demo.service;

import java.util.ArrayList;
import java.util.List;

import my.netty.demo.restserver.demo.bo.Icon;
import my.netty.demo.restserver.demo.dao.UserDao;
import my.netty.demo.restserver.demo.entity.User;
import my.netty.demo.restserver.demo.entity.UserLite;
import my.netty.demo.restserver.nettyrest.ApiProtocol;

public class UserService extends BaseService {

    public UserService(ApiProtocol apiProtocol) {
        super(apiProtocol);
    }

    /**
     * Version compatibility demo
     *
     * @param uid
     * @return
     */
    public User get(int uid) {

        if (apiProtocol.getBuild() > 105) {
            return getFromDatabase(uid);
        }

        return new User(uid, "xxx", 25);
    }

    public List<User> list() {

        if (apiProtocol.getBuild() > 105) {
            return getListFromDatabase();
        }

        List<User> users = new ArrayList<User>();
        users.add(new User(1, "mengkang.zhou", 25));
        users.add(new User(2, "meng.zhou", 23));

        return users;
    }


    public User getFromDatabase(int uid) {
        UserDao  userDao  = new UserDao();
        UserLite userLite = userDao.get(uid);
        return exchange(userLite);
    }

    public List<User> getListFromDatabase() {
        List<UserLite> userLites = new UserDao().list();
        List<User>     users     = new ArrayList<User>();

        for (UserLite userLite : userLites) {
            users.add(exchange(userLite));
        }

        return users;
    }

    public User exchange(UserLite userLite){
        Icon icon = IconService.get(userLite.getIcon());
        int  age  = (((int) (System.currentTimeMillis() / 1000)) - userLite.getBirthday()) / (24 * 3600 * 365);
        return new User(userLite.getId(), userLite.getName(), icon, age);
    }
}
