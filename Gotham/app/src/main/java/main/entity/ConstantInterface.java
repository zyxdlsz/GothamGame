package main.entity;

/**
 * Created by liszdeng on 11/7/18.
 */

public interface ConstantInterface {

    /*
     * this is final static for walk animation
     */
    /**向下移动动画**/
    int ANIM_DOWN = 0;
    /**向左移动动画**/
    int ANIM_LEFT = 1;
    /**向右移动动画**/
    int ANIM_RIGHT = 2;
    /**向上移动动画**/
    int ANIM_UP = 3;
    /**向下停止动画**/
    int ANIM_STOP_D = 4;
    /**向上停止动画**/
    int ANIM_STOP_U = 5;
    /**动画的总数量**/
    int ANIM_COUNT = 6;

    /*
     * this is final static for route pattern
     */
    //从门到位置1
    int ROUTE_PATTERN1_A=0;
    //从位置1到门
    int ROUTE_PATTERN1_B=1;
    //从门到位置2
    int ROUTE_PATTERN2_A=2;
    //从位置2到门
    int ROUTE_PATTERN2_B=3;
    //路径总数
    int ROUTE_COUNT=4;

    /*
     * this is final static for entrance
     */
    int DOOR1_X=6;
    int DOOR1_Y=0;
    int DOOR2_X=10;
    int DOOR2_Y=4;

    /*
     * this is final static for char position
     */
    //位置1
    int P1_X=1;
    int P1_Y=7;
    //位置2
    int P2_X=3;
    int P2_Y=9;

}
