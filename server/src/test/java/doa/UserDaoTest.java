package doa;

import org.junit.Test;

import java.util.List;

import dao.IUserDao;
import dao.User;
import dao.tsv.TSVDaoFactory;

public class UserDaoTest {

    @Test
    public void TSVTest() {
        System.out.println("Start");

        IUserDao dao = new TSVDaoFactory().getUserDao();

        User user = new User("bob", "password", "authtoken");
        dao.addUser(user.getId(), user.getPassword(), user.getAuthtoken());
        List<User> users = dao.getUsers();
        assert(users.size() == 1);
        System.out.println(user);
        System.out.println(users.get(0));
        assert(users.get(0).equals(user));

        System.out.println("No problems");
    }

}
