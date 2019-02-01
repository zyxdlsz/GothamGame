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
    private boolean initflag=true;//是否要初始化的标志
    private int[] seat=new int[2];
    private int judgedoor;
    private int[] door=new int[2];
    private int[] dest=new int[2];
    private int boundary;
    private boolean inflag=true;//进入还是出去的标志
    private boolean turnflag=true;//是否还没有转过弯
    /**
     * 构造方法
     *
     * @param context
     */
    public WalkView(Context context, Walker walker,int[] chair,int door) {
        super(context);
        init(context,walker,chair,door);
    }

    public WalkView(Context context, AttributeSet attrs, Walker walker,int[] chair,int door) {
        super(context, attrs);
        init(context,walker,chair,door);
    }

    public WalkView(Context context, AttributeSet attrs, int defStyleAttr, Walker walker,int[] chair,int door) {
        super(context, attrs, defStyleAttr);
        init(context,walker,chair,door);
    }

    public void init(Context context,Walker wal,int[] chair,int door){
        mPaint = new Paint();
        walker=wal;
        seat[0]=chair[0];
        seat[1]=chair[1];
        judgedoor=door;
        Log.v("mylog","init fist over");
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
        if(initflag){

            mScreenWidth = getWidth()-250;
            mScreenHeight = getHeight()-250;
            //初始化 门，人的位置，终点位置，行走方向

            if(judgedoor==0){
                door[0]=mScreenWidth*ConstantInterface.DOOR1_X/10;
                door[1]=mScreenHeight*ConstantInterface.DOOR1_Y/10;
            }
            else{
                door[0]=mScreenWidth*ConstantInterface.DOOR2_X/10;
                door[1]=mScreenHeight*ConstantInterface.DOOR2_Y/10;
            }
            walker.setmHeroPosX(door[0]);
            walker.setmHeroPosY(door[1]);
            dest[0]=mScreenWidth*seat[0]/10;
            dest[1]=mScreenWidth*seat[1]/10;
            boundary=mScreenHeight*ConstantInterface.B1_X/10;

            mAnimationState = ConstantInterface.ANIM_LEFT;

            initflag=false;



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
                break;
            case ConstantInterface.ANIM_LEFT://向左走
                //Log.v("mylog","go left");
                walker.setmHeroPosX(walker.getmHeroPosX()-walker.getHeroStep());
                //Log.v("mylog","walkerX: "+walker.getmHeroPosX()+" walkerY: "+walker.getmHeroPosY());
                break;
            case ConstantInterface.ANIM_UP://向上走
                //Log.v("mylog","go up");
                walker.setmHeroPosY(walker.getmHeroPosY()-walker.getHeroStep());
                //Log.v("mylog","walkerX: "+walker.getmHeroPosX()+" walkerY: "+walker.getmHeroPosY());
                break;
            case ConstantInterface.ANIM_DOWN://向下走
                //Log.v("mylog","go down");
                walker.setmHeroPosY(walker.getmHeroPosY()+walker.getHeroStep());
                //Log.v("mylog","walkerX: "+walker.getmHeroPosX()+" walkerY: "+walker.getmHeroPosY());
                break;
            case ConstantInterface.ANIM_STOP_D:
            case ConstantInterface.ANIM_STOP_U:
                Log.v("mylog","stop");
                if(inflag)
                    leave();
                return;
        }

        //在遇到第一张桌子之后转弯，转向能去到dest的方向
        if(turnflag&&inflag&&walker.getmHeroPosX()<boundary+OFF_HERO_X){
            if(dest[1]>walker.getmHeroPosY())
                mAnimationState=ConstantInterface.ANIM_DOWN;
            else
                mAnimationState=ConstantInterface.ANIM_UP;
            turnflag=false;
            return;
        }
        //出门时同理
        if(turnflag&&!inflag&&walker.getmHeroPosX()>boundary+OFF_HERO_X){
            if(dest[1]>walker.getmHeroPosY())
                mAnimationState=ConstantInterface.ANIM_DOWN;
            else
                mAnimationState=ConstantInterface.ANIM_UP;
            turnflag=false;
            return;
        }
        //碰撞检测+终点检测
        switch(mAnimationState){
            case ConstantInterface.ANIM_UP://dest[1]<walker.Y
                if(walker.getmHeroPosY()<dest[1]-OFF_HERO_Y){
                    walker.setmHeroPosY(dest[1]-OFF_HERO_Y);
                    //进门向上有两种可能：到了/需要左转
                    if(inflag){
                        if(walker.getmHeroPosX()==dest[0]) {
                            mAnimationState = ConstantInterface.ANIM_STOP_U;
                        }
                        else
                            mAnimationState=ConstantInterface.ANIM_LEFT;
                    }
                    else{//出门向上有一种可能：需要右转
                        mAnimationState=ConstantInterface.ANIM_RIGHT;
                    }
                }
                break;
            case ConstantInterface.ANIM_DOWN://dest[1]>walker.Y
                if(walker.getmHeroPosY()>dest[1]+OFF_HERO_Y){
                    walker.setmHeroPosY(dest[1]+OFF_HERO_Y);
                    //进门向上有两种可能：到了/需要左转
                    if(inflag){
                        if(walker.getmHeroPosX()==dest[0])
                            mAnimationState=ConstantInterface.ANIM_STOP_D;
                        else
                            mAnimationState=ConstantInterface.ANIM_LEFT;
                    }
                    else{//出门向上有一种可能：需要右转
                        mAnimationState=ConstantInterface.ANIM_RIGHT;
                    }
                }
                break;
            case ConstantInterface.ANIM_LEFT://dest[0]<walker.X
                if(walker.getmHeroPosX()<dest[0]){
                    walker.setmHeroPosX(dest[0]);
                    if(dest[1]>walker.getmHeroPosY()){//特殊情况（到达终点时）会有offset
                        Log.v("mylog","dest[1]-offest: "+(dest[1]-OFF_HERO_Y)+" walkerY: "+walker.getmHeroPosY());
                        if(walker.getmHeroPosY()==dest[1]-OFF_HERO_Y)
                            mAnimationState=ConstantInterface.ANIM_STOP_D;
                        else
                            mAnimationState=ConstantInterface.ANIM_DOWN;
                    }
                    else{//特殊情况（到达终点时）会有offset
                        Log.v("mylog","dest[1]+offest: "+(dest[1]+OFF_HERO_Y)+" walkerY: "+walker.getmHeroPosY());
                        if(walker.getmHeroPosY()==dest[1]+OFF_HERO_Y)
                            mAnimationState=ConstantInterface.ANIM_STOP_U;
                        else
                            mAnimationState=ConstantInterface.ANIM_UP;
                    }
                }
                break;
            case ConstantInterface.ANIM_RIGHT:
                if(walker.getmHeroPosX()>dest[0]){
                    walker.setmHeroPosX(dest[0]);
                    if(dest[1]>walker.getmHeroPosY()){//特殊情况（到达终点时）会有offset
                        Log.v("mylog","dest[1]-offest: "+(dest[1]-OFF_HERO_Y)+" walkerY: "+walker.getmHeroPosY());
                        if(walker.getmHeroPosY()==dest[1]-OFF_HERO_Y)
                            mAnimationState=ConstantInterface.ANIM_STOP_D;
                        else
                            mAnimationState=ConstantInterface.ANIM_DOWN;
                    }
                    else{//特殊情况（到达终点时）会有offset
                        Log.v("mylog","dest[1]+offest: "+(dest[1]+OFF_HERO_Y)+" walkerY: "+walker.getmHeroPosY());
                        if(walker.getmHeroPosY()==dest[1]+OFF_HERO_Y)
                            mAnimationState=ConstantInterface.ANIM_STOP_U;
                        else
                            mAnimationState=ConstantInterface.ANIM_UP;
                    }
                }
                break;
        }
        //Log.v("mylog","by the end walkerX: "+walker.getmHeroPosX()+" walkerY: "+walker.getmHeroPosY());

    }

    public void leave(){
        inflag=false;
        turnflag=true;
        mAnimationState=ConstantInterface.ANIM_RIGHT;
        dest[0]=door[0];
        dest[1]=door[1];

        Log.v("mylog","destX: "+dest[0]+" destY: "+dest[1]);

    }

    private void RenderAnimation(Canvas canvas) {
        walkerAnim[mAnimationState].DrawAnimation(canvas, mPaint, walker.getmHeroPosX(), walker.getmHeroPosY());
    }
}
