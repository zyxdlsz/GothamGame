package main.dao;

import main.gotham.R;

import java.util.ArrayList;
import java.util.List;

import main.entity.People;

/**
 * Created by liszdeng on 9/23/18.
 */

public class PeopleDao implements PeopleImpl {

    private List<People> customs=new ArrayList<People>();

    public PeopleDao(){
        CreateCustom();
    }

    public void CreateCustom(){

        String[] names={"迪克","提姆","卡珊","斯蒂芬妮",
                "凯特", "哈维","戈登局长","芭芭拉",
                "蕾妮", "史黛西", "谜语人","双面人",
                "企鹅人", "夜翼", "红头罩","红罗宾",
                "罗宾", "罗宾","女猎手","阿福",
                "超级小子","超级小子"};
        int[] pictures={R.mipmap.people,R.mipmap.people,R.mipmap.girl,R.mipmap.girl,
                R.mipmap.people,R.mipmap.people,R.mipmap.people,R.mipmap.people,
                R.mipmap.people,R.mipmap.people,R.mipmap.people,R.mipmap.people,
                R.mipmap.people,R.mipmap.people,R.mipmap.people,R.mipmap.people,
                R.mipmap.people,R.mipmap.people,R.mipmap.people,R.mipmap.people,
                R.mipmap.people,R.mipmap.people};
        String[] describes={"人民的好警官","未来的CEO","人民的好警官","人民的好警官",
                "人民的好警官","人民的好警官","人民的好警官","人民的好警官",
                "人民的好警官","人民的好警官","人民的好警官","人民的好警官",
                "人民的好警官","人民的好警官","人民的好警官","人民的好警官",
                "人民的好警官","人民的好警官","人民的好警官","人民的好警官",
                "人民的好警官","人民的好警官"};
        String[][] foods={{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},
                {"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},
                {"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},
                {"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},
                {"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"},
                {"苹果","香蕉","菠萝"},{"苹果","香蕉","菠萝"}};
        int[][] days={{1,3,5},{2,4,6},{0,1,5},{1,3,5},
                {1,3,5},{1,3,5},{1,3,5},{1,3,5},
                {1,3,5},{1,3,5},{1,3,5},{1,3,5},
                {1,3,5},{1,3,5},{1,3,5},{1,3,5},
                {1,3,5},{1,3,5},{1,3,5},{1,3,5},
                {1,3,5},{1,3,5}};

        String[][] conver={{"你好","你好","再见"},{"你好","你好","再见"},{"你好","你好","再见"},{"你好","你好","再见"},
                {"你好","你好","再见"},{"你好","你好","再见"}};
        int[][] speaker={{-1,0,1},{-1,0,1},{-1,0,1},{-1,0,1},{-1,0,1},{-1,0,1}};

        String[][] conver2={{"初次见面","你好","你也好","最近怎么样啊？","也就那样吧，你呢？","不太好呢:(","是吗……","下次有空见个面吧","感觉好久没见到你了","好啊","周末怎么样？","那就周末吧","周末见","周末见"},{"你好2","你好","再见"},{"你好3","你好","再见"},{"你好4","你好","再见"},
                {"你好5","你好","再见"},{"你好6","你好","再见"}};
        int[][] speaker2={{-1,0,1,0,1,0,1,1,1,0,0,1,0,1},{-1,0,1},{-1,0,1},{-1,0,1},{-1,0,1},{-1,0,1}};

        String[][][] convers={conver,conver2,conver,conver,
                conver,conver,conver,conver,
                conver,conver,conver,conver,
                conver,conver,conver,conver,
                conver,conver,conver,conver,
                conver,conver};
        int[][][] speakers={speaker,speaker2,speaker,speaker,
                speaker,speaker,speaker,speaker,
                speaker,speaker,speaker,speaker,
                speaker,speaker,speaker,speaker,
                speaker,speaker,speaker,speaker,
                speaker,speaker};

        for(int i=0;i<22;i++){
            People p=new People();
            p.setId(i+1);
            p.setName(names[i]);
            p.setPicture(pictures[i]);
            p.setDescribe(describes[i]);
            p.setFavorite_food(foods[i]);
            p.setFavorite_days(days[i]);
            p.setConversations(convers[i]);
            p.setSpeakers(speakers[i]);
            if(p.isEmpty())
                return;
            customs.add(p);
        }
    }

    public List<People> getAll(){
        return customs;
    }
    public List<People> getPair(int i){
        List<People> pair=new ArrayList<People>();
        for(People p:customs){
            if(p.getId()==i||p.getId()==i+1){
                pair.add(p);
            }
        }
        return pair;
    }
    public People getByName(String name){
        People e=new People();
        for(People p:customs){
            if(name.equals(p.getName())){
                e.setId(p.getId());
                e.setDescribe(p.getDescribe());
                e.setConversations(p.getConversations());
                e.setSpeakers(p.getSpeakers());
                e.setFavorite_food(p.getFavorite_food());
                e.setSatify(p.getSatify());
                e.setName(p.getName());
                e.setFavorite_days(p.getFavorite_days());
                e.setPicture(p.getPicture());
            }
        }
        return e;
    }
    public People getByID(int id){
        People e=new People();
        for(People p:customs){
            if(p.getId()==id){
                e.setId(p.getId());
                e.setDescribe(p.getDescribe());
                e.setConversations(p.getConversations());
                e.setSpeakers(p.getSpeakers());
                e.setFavorite_food(p.getFavorite_food());
                e.setSatify(p.getSatify());
                e.setName(p.getName());
                e.setFavorite_days(p.getFavorite_days());
                e.setPicture(p.getPicture());
            }
        }
        return e;
    }
}
