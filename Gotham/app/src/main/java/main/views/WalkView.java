package main.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import main.engine.Animation;

import main.entity.ConstantInterface;
import main.entity.Walker;

/**
 * Created by liszdeng on 10/13/18.
 */

public class WalkView extends View {

    Animation walkerAnim [] = new Animation[ConstantInterface.ANIM_COUNT];

    Paint mPaint = null;

    //当前绘制动画状态ID,向右
    int mAnimationState;

    //屏幕宽高才尺寸
    int mScreenWidth = 0;
    int mScreenHeight = 0;

    //人物
    Walker walker=null;

    /**人物图片资源与实际英雄脚底板坐标的偏移**/
    public final static int OFF_HERO_X = 16;
    public final static int OFF_HERO_Y = 35;

    //初始化人物的标签
    private boolean flag=true;
    private int pattern;
    private int[][] positions=new int[2][2];
    private int[] door1=new int[2];
    private int[] door2=new int[2];
    private int[] dest=new int[2];
    /**
     * 构造方法
     *
     * @param context
     */
    public WalkView(Context context, Walker walker,int pat) {
        super(context);
        init(context,walker,pat);
    }

    public WalkView(Context context, AttributeSet attrs, Walker walker,int pat) {
        super(context, attrs);
        init(context,walker,pat);
    }

    public WalkView(Context context, AttributeSet attrs, int defStyleAttr, Walker walker,int pat) {
        super(context, attrs, defStyleAttr);
        init(context,walker,pat);
    }

    public void init(Context context,Walker wal,int pat){
        mPaint = new Paint();
        walker=wal;
        pattern=pat;
        Log.v("mylog","pattern is "+pat);
        initAnimation(context);
    }

    private void initAnimation(Context context) {
        //这里可以用循环来处理总之我们需要把动画的ID传进去
        walkerAnim[ConstantInterface.ANIM_DOWN] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_DOWN], true);
        walkerAnim[ConstantInterface.ANIM_LEFT] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_LEFT], true);
        walkerAnim[ConstantInterface.ANIM_RIGHT] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_RIGHT], true);
        walkerAnim[ConstantInterface.ANIM_UP] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_UP], true);
        walkerAnim[ConstantInterface.ANIM_STOP_D] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_STOP_D], true);
        walkerAnim[ConstantInterface.ANIM_STOP_U] = new Animation(context, walker.getXxAnim()[ConstantInterface.ANIM_STOP_U], true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mScreenWidth = getWidth()-250;
        mScreenHeight = getHeight()-250;
        if(flag){
            //初始化 门，人的位置，终点位置，行走方向
            positions[0][0]=mScreenWidth*ConstantInterface.P1_X/10;
            positions[0][1]=mScreenWidth*ConstantInterface.P1_Y/10;
            positions[1][0]=mScreenWidth*ConstantInterface.P2_X/10;
            positions[1][1]=mScreenWidth*ConstantInterface.P2_Y/10;
            if(pattern%2==0){
                if(pattern<2) {//上一半的座位数
                    door1[0]=mScreenWidth*ConstantInterface.DOOR1_X/10;
                    door1[1]=mScreenHeight*ConstantInterface.DOOR1_Y/10;
                    walker.setmHeroPosX(door1[0]);
                    walker.setmHeroPosY(door1[1]);
                    //mAnimationState = ConstantInterface.ANIM_LEFT;
                }
                else{
                    door2[0]=mScreenWidth*ConstantInterface.DOOR2_X/10;
                    door2[1]=mScreenHeight*ConstantInterface.DOOR2_Y/10;
                    walker.setmHeroPosX(door2[0]);
                    walker.setmHeroPosY(door2[1]);
                    //mAnimationState = ConstantInterface.ANIM_RIGHT;
                }
                mAnimationState = ConstantInterface.ANIM_DOWN;
                //mAnimationState = ConstantInterface.ANIM_STOP_D;
            }else{
                walker.setmHeroPosX(positions[pattern/2-1][0]);
                walker.setmHeroPosY(positions[pattern/2-1][1]);
            }
            switch(pattern){
                case ConstantInterface.ROUTE_PATTERN1_A:
                    dest[0]=positions[0][0];
                    dest[1]=positions[0][1];
                    break;
                case ConstantInterface.ROUTE_PATTERN1_B:
                    dest[0]=door1[0];
                    dest[1]=door1[1];
                    break;
                case ConstantInterface.ROUTE_PATTERN2_A:
                    dest[0]=positions[1][0];
                    dest[1]=positions[1][1];
                    break;
                case ConstantInterface.ROUTE_PATTERN2_B:
                    dest[0]=door2[0];
                    dest[1]=door2[1];
                    break;
            }

            flag=false;
        }
        /**绘制动画**/
        RenderAnimation(canvas);
        /**动画更新**/
        UpdateAnimation();
        super.onDraw(canvas);
        invalidate();
    }

    private void UpdateAnimation(){
            /*
             *  一开始向右走，然后碰到右边，向下走，碰到下边，向左走，碰到左边，向上走
             *  移动
             */
        switch(mAnimationState){
            case ConstantInterface.ANIM_RIGHT://向右走
                walker.setmHeroPosX(walker.getmHeroPosX()+walker.getHeroStep());

                walker.setmHeroPosY(walker.getmHeroPosY()+walker.getHeroStep());
                break;
            case ConstantInterface.ANIM_LEFT://向左走
                walker.setmHeroPosX(walker.getmHeroPosX()-walker.getHeroStep());

                walker.setmHeroPosY(walker.getmHeroPosY()-walker.getHeroStep());
                break;
            case ConstantInterface.ANIM_UP://向上走
                walker.setmHeroPosY(walker.getmHeroPosY()-walker.getHeroStep());

                walker.setmHeroPosX(walker.getmHeroPosX()+walker.getHeroStep());
                break;
            case ConstantInterface.ANIM_DOWN://向下走
                walker.setmHeroPosY(walker.getmHeroPosY()+walker.getHeroStep());

                walker.setmHeroPosX(walker.getmHeroPosX()-walker.getHeroStep());
                break;
            case ConstantInterface.ANIM_STOP_D:
            case ConstantInterface.ANIM_STOP_U:
                //Log.v("mylog","then return");
                return;
        }

        //碰撞检测+终点检测

        switch(pattern){
            case ConstantInterface.ROUTE_PATTERN1_A:
                if(walker.getmHeroPosX()>dest[0]&&walker.getmHeroPosY()>dest[1]){
                    walker.setmHeroPosX(dest[0]);
                    walker.setmHeroPosY(dest[1]);
                    mAnimationState=ConstantInterface.ANIM_STOP_D;
                }
                else {
                    routeA(OFF_HERO_X, mScreenHeight - OFF_HERO_X);
                }
                break;
            case ConstantInterface.ROUTE_PATTERN2_A:
                if(walker.getmHeroPosX()<dest[0]&&walker.getmHeroPosY()<dest[1]){
                    Log.v("mylog","stop");
                    //walker.setmHeroPosX(dest[0]);
                    //walker.setmHeroPosY(dest[1]);
                    mAnimationState=ConstantInterface.ANIM_STOP_U;
                }
                else {
                    routeA(OFF_HERO_X, mScreenHeight - OFF_HERO_X);
                }
                break;
            case ConstantInterface.ROUTE_PATTERN1_B:
                if(walker.getmHeroPosX()>dest[0]&&walker.getmHeroPosY()>dest[1]){
                    walker.setmHeroPosX(dest[0]);
                    walker.setmHeroPosY(dest[1]);
                    mAnimationState=ConstantInterface.ANIM_STOP_D;
                }
                else {
                    routeB(OFF_HERO_X, mScreenHeight - OFF_HERO_X);
                }
                break;
            case ConstantInterface.ROUTE_PATTERN2_B:
                if(walker.getmHeroPosX()<dest[0]&&walker.getmHeroPosY()<dest[1]){
                    walker.setmHeroPosX(dest[0]);
                    walker.setmHeroPosY(dest[1]);
                    mAnimationState=ConstantInterface.ANIM_STOP_U;
                }
                else {
                    routeB(OFF_HERO_X, mScreenHeight - OFF_HERO_X);
                }
                break;
        }

    }

    /*
     * 边缘检测
     */
    private void routeA(int leftmax,int bottom){

        if (walker.getmHeroPosX() < leftmax) {//碰到左边
            walker.setmHeroPosX(leftmax);
            mAnimationState=ConstantInterface.ANIM_RIGHT;
        }else if (walker.getmHeroPosX() > mScreenWidth-OFF_HERO_X) {//碰到右边
            walker.setmHeroPosX(mScreenWidth-OFF_HERO_X);
            mAnimationState=ConstantInterface.ANIM_DOWN;
        }

        if (walker.getmHeroPosY() < OFF_HERO_Y) {//碰到上边
            walker.setmHeroPosY(OFF_HERO_Y);
            mAnimationState=ConstantInterface.ANIM_DOWN;
        }if (walker.getmHeroPosY() > bottom) {//碰到下边
            walker.setmHeroPosY(bottom);
            mAnimationState=ConstantInterface.ANIM_LEFT;
            //mAnimationState=ConstantInterface.ANIM_STOP_D;

        }
    }
    private void routeB(int leftmax,int bottom){

        if (walker.getmHeroPosX() < leftmax) {//碰到左边
            walker.setmHeroPosX(leftmax);
            mAnimationState=ConstantInterface.ANIM_UP;
        } else if (walker.getmHeroPosX() > mScreenWidth-OFF_HERO_X) {//碰到右边
            walker.setmHeroPosX(mScreenWidth-OFF_HERO_X);
            mAnimationState=ConstantInterface.ANIM_LEFT;
            //mAnimationState=ANIM_STOP;
        }
        if (walker.getmHeroPosY() < OFF_HERO_Y) {//碰到上边
            walker.setmHeroPosY(OFF_HERO_Y);
            mAnimationState=ConstantInterface.ANIM_RIGHT;
        } else if (walker.getmHeroPosY() > bottom) {//碰到下边
            walker.setmHeroPosY(bottom);
            mAnimationState=ConstantInterface.ANIM_UP;
        }
    }

    private void RenderAnimation(Canvas canvas) {
        //Log.v("mylog","direction is "+mAnimationState);
        walkerAnim[mAnimationState].DrawAnimation(canvas, mPaint, walker.getmHeroPosX(), walker.getmHeroPosY());
    }
}
