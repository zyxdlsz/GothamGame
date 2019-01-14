package main.dao;

import java.util.List;

import main.entity.People;

/**
 * Created by liszdeng on 9/23/18.
 */

public interface PeopleImpl {

    public List<People> getAll();
    public List<People> getPair(int i);
    public People getByName(String name);
    public People getByID(int id);

    //getCount, pair可以自己算，然后通过getByID获取
}
