package main.entity;

import main.gotham.R;


/**
 * Created by liszdeng on 10/13/18.
 */

public class Walker implements Cloneable{


    int xxAnim [][] = new int[ConstantInterface.ANIM_COUNT][];

    //英雄在地图中的坐标以英雄脚底中心为原点
    int mHeroPosX = 100;
    int mHeroPosY= 100;

    /**主角行走步长**/
    public int HeroStep = 3;

    public Walker(){
        initAnim();
    }

    public void initAnim(){
        xxAnim[ConstantInterface.ANIM_DOWN] = new int []{R.drawable.xx_down_a,R.drawable.xx_down_b,R.drawable.xx_down_c,R.drawable.xx_down_d};
        xxAnim[ConstantInterface.ANIM_LEFT] = new int []{R.drawable.xx_left_a,R.drawable.xx_left_b,R.drawable.xx_left_c,R.drawable.xx_left_d};
        xxAnim[ConstantInterface.ANIM_RIGHT]= new int []{R.drawable.xx_right_a,R.drawable.xx_right_b,R.drawable.xx_right_c,R.drawable.xx_right_d};
        xxAnim[ConstantInterface.ANIM_UP]   = new int []{R.drawable.xx_up_a,R.drawable.xx_up_b,R.drawable.xx_up_c,R.drawable.xx_up_d};
        xxAnim[ConstantInterface.ANIM_STOP_D] = new int []{R.drawable.xx_down_a};
        xxAnim[ConstantInterface.ANIM_STOP_U] = new int []{R.drawable.xx_up_a};
    }


    @Override
    public Object clone() {
        Walker wal = null;
        try{
            wal = (Walker)super.clone();   //浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<ConstantInterface.ANIM_COUNT;i++) {
            wal.xxAnim[i] = (int []) xxAnim[i].clone();   //深度复制
        }
        return wal;
    }

    public int[][] getXxAnim() {
        return xxAnim;
    }

    public void setXxAnim(int[][] xxAnim) {
        this.xxAnim = xxAnim;
    }

    public int getmHeroPosX() {
        return mHeroPosX;
    }

    public void setmHeroPosX(int mHeroPosX) {
        this.mHeroPosX = mHeroPosX;
    }

    public int getmHeroPosY() {
        return mHeroPosY;
    }

    public void setmHeroPosY(int mHeroPosY) {
        this.mHeroPosY = mHeroPosY;
    }

    public int getHeroStep() {
        return HeroStep;
    }

}