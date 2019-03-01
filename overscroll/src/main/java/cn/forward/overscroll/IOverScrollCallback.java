package cn.forward.overscroll;

import android.animation.Animator;
import android.support.annotation.IntDef;
import android.view.View;

/**
 * listen the over scroll
 *
 * @author ziwei huang
 */
public interface IOverScrollCallback {

    @IntDef({DIRECTION_UP, DIRECTION_DOWN})
    public @interface ScrollDirection {
    }

    public static final int DIRECTION_UP = 1 << 0;
    public static final int DIRECTION_DOWN = 1 << 1;
    public static final int DIRECTION_LEFT = 1 << 2;
    public static final int DIRECTION_RIGHT = 1 << 3;

    /**
     * @param child           the child view of the CoordinatorLayout this Behavior is associated with. 跟当前behavior绑定的CoordinatorLayout的子view
     * @param offset          offset the vertical offset for the child view, in px. 子View在垂直位置上的偏移值
     * @param scrollDirection {@link #DIRECTION_UP} or {@link #DIRECTION_DOWN}. 过度滑动的方向
     * @return true if the child view can scroll in the scroll direction. 返回true表示子view可以在相应的方向上过度滑动
     */
    boolean canScroll(View child, int offset, @ScrollDirection int scrollDirection);

    /**
     * 最大的惯性滑动的偏移值
     *
     * @param child
     * @param offset
     * @param scrollDirection
     * @return max offsets when fling, in px
     */
    int getMaxFlingOffset(View child, int offset, @ScrollDirection int scrollDirection);

    /**
     * Damping factor, the larger the value, the harder it is to scroll
     * 阻尼因子,值越大则摩擦越大越难滑动
     *
     * @param child
     * @param offset
     * @param scrollDirection
     * @return Damping factor when scrolling, should be positive. Only take effect when you offset the child view away.
     */
    float getDampingFactor(View child, int offset, @ScrollDirection int scrollDirection);

    /**
     * 产生惯性滑动的最小速度(取绝对值)，小于该速度时会停止惯性滑动.
     *
     * @param child
     * @param offset
     * @param scrollDirection
     * @return Minimum velocity (the absolute value) to occur a fling,  in pixels per second. If the velocity is less than the min, the child view will stop the fling
     */
    int getMinFlingVelocity(View child, int offset, @ScrollDirection int scrollDirection);

    /**
     * callback when the child view's offset changed．
     * 子view发生偏移时回调
     * @param child
     * @param offset
     */
    void onOffsetChanged(View child, int offset);

    /**
     * callback before springing back
     * @param child
     * @param offset
     * @param animator the spring-back animation. You can change the animator before starting.
     */
    void onSpringBack(View child, int offset, Animator animator);
}
