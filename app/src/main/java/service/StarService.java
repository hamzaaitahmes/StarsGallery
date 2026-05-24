package service;

import beans.Star;
import dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    public static StarService getInstance() {
        if (instance == null) {
            instance = new StarService();
        }
        return instance;
    }

    private void seed() {
        stars.add(new Star("Kate Bosworth",
                "https://randomuser.me/api/portraits/women/1.jpg", 4.5f));
        stars.add(new Star("George Clooney",
                "https://randomuser.me/api/portraits/men/2.jpg", 4.8f));
        stars.add(new Star("Michelle Rodriguez",
                "https://randomuser.me/api/portraits/women/3.jpg", 4.2f));
        stars.add(new Star("Leonardo DiCaprio",
                "https://randomuser.me/api/portraits/men/4.jpg", 4.9f));
        stars.add(new Star("Scarlett Johansson",
                "https://randomuser.me/api/portraits/women/5.jpg", 4.7f));
        stars.add(new Star("Tom Cruise",
                "https://randomuser.me/api/portraits/men/6.jpg", 4.3f));
    }

    @Override
    public boolean create(Star o) {
        return stars.add(o);
    }

    @Override
    public boolean update(Star o) {
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getId() == o.getId()) {
                stars.set(i, o);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Star o) {
        return stars.remove(o);
    }

    @Override
    public Star findById(int id) {
        for (Star s : stars) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    @Override
    public List<Star> findAll() {
        return stars;
    }
}