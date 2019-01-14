package main.dao;

import java.util.ArrayList;
import java.util.List;

import main.entity.Equip;
import main.gotham.R;

public class EquipDao implements EquipImpl {

    private List<Equip> equips=new ArrayList<Equip>();

    public EquipDao(){createEquip();}

    public void createEquip(){
        int[] pictures={R.drawable.desk,R.drawable.desk,R.drawable.desk,R.drawable.desk,R.drawable.desk,R.drawable.desk,R.drawable.desk};
        String[] names={"桌子1","桌子2","桌子3","桌子4","桌子5","桌子6","桌子7"};
        String[] describes={"增加四个座位","增加四个座位","增加四个座位","增加四个座位","增加四个座位","增加四个座位","增加四个座位"};
        int[] moneys={2000,2000,2000,3000,4000,5000,3000};
        int[] levels={2,3,4,5,6,7,8};

        for(int i=0;i<7;i++){
            Equip e=new Equip();
            e.setId(i+1);
            e.setName(names[i]);
            e.setPicture(pictures[i]);
            e.setDescription(describes[i]);
            e.setLevel(levels[i]);
            e.setMoney(moneys[i]);
            equips.add(e);
        }
    }

    @Override
    public List<Equip> getAll() {
        return equips;
    }

    @Override
    public Equip getByID(int id) {
        return equips.get(id-1);
    }
}
