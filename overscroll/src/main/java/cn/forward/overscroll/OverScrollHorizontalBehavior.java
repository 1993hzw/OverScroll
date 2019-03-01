package cn.forward.overscroll;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * 水平方向上的弹性滑动和惯性滑动效果
 *
 * @author ziwei huang
 */
public class OverScrollHorizontalBehavior extends BaseOverScrollBehavior {

    public OverScrollHorizontalBehavior() {
    }

    public OverScrollHorizontalBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, View child,
                                       View directTargetChild, View target, int nestedScrollAxes, int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_HORIZONTAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                  View target, int dx, int dy, int[] consumed, int type) {
        onNestedPreScrollInner(coordinatorLayout, child, target, dx, consumed, type);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                               int type) {
        onNestedScrollInner(coordinatorLayout, child, target, dxConsumed, dxUnconsumed, type);

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return onNestedPreFlingInner(coordinatorLayout, child, target, velocityX);
    }

    @Override
    public void updateOffset(View child, int offset) {
        child.setTranslationX(offset);
    }

    @Override
    public int getOffset(View child) {
        return (int) child.getTranslationX();
    }

    @Override
    public int getMaxOffset(View child) {
        return child.getWidth();
    }

    public int getMinOffset(View child) {
        return -child.getWidth();
    }

}