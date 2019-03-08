package cn.forward.overscroll;

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

    public static final int DIRECTION_UP = 1 << 0; // Direction to start
    public static final int DIRECTION_DOWN = 1 << 1; // Direction to end
    public static final int DIRECTION_LEFT = 1 << 2; // Direction to start
    public static final int DIRECTION_RIGHT = 1 << 3; // Direction to end

    /**
     * @param overScroll
     * @param child           the child view of the CoordinatorLayout this Behavior is associated with. 跟当前behavior绑定的CoordinatorLayout的子view
     * @param scrollDirection {@link #DIRECTION_UP} or {@link #DIRECTION_DOWN}. 过度滑动的方向
     * @return true if the child view can scroll in the scroll direction. 返回true表示子view可以在相应的方向上过度滑动
     */
    boolean canScroll(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection);

    /**
     * 最大的惯性滑动的偏移值
     *
     * @param overScroll
     * @param child
     * @param scrollDirection
     * @return max offsets when fling, in px
     */
    int getMaxFlingOffset(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection);

    /**
     * Damping factor, the larger the value, the harder it is to scroll
     * 阻尼因子,值越大则摩擦越大越难滑动
     *
     * @param overScroll
     * @param child
     * @param scrollDirection
     * @return Damping factor when scrolling, should be positive. Only take effect when you offset the child view away.
     */
    float getDampingFactor(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection);

    /**
     * 产生惯性滑动的最小速度(取绝对值)，小于该速度时会停止惯性滑动.
     *
     * @param overScroll
     * @param child
     * @param scrollDirection
     * @return Minimum velocity (the absolute value) to occur a fling,  in pixels per second. If the velocity is less than the min, the child view will stop the fling
     */
    int getMinFlingVelocity(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection);

    /**
     * callback when the child view's offset changed．
     * 子view发生偏移时回调
     *
     * @param overScroll
     * @param child
     * @param offset
     */
    void onOffsetChanged(IOverScroll overScroll, View child, int offset);

    /**
     * callback before springing back
     *
     * @param overScroll
     * @param child
     * @return true if you have consumed the event, false if you haven't. The default implementation always returns false.
     */
    boolean onSpringBack(IOverScroll overScroll, View child);

    /**
     * callback before stop springing back
     *
     * @param overScroll
     * @param child
     */
    void onStopSpringingBack(IOverScroll overScroll, View child);
}
