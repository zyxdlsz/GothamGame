package main.dao;

import java.util.List;

import main.entity.Equip;

public interface EquipImpl {

    public List<Equip> getAll();
    public Equip getByID(int id);
}
