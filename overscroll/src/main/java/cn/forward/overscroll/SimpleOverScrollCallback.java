package cn.forward.overscroll;

import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author ziwei huang
 */
public class SimpleOverScrollCallback implements IOverScrollCallback {

    private int mMinFlingVelocity;

    @Override
    public boolean canScroll(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection) {
        return true;
    }


    @Override
    public int getMaxFlingOffset(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection) {
        if (scrollDirection == DIRECTION_DOWN || scrollDirection == DIRECTION_RIGHT) {
            return child.getHeight() / 3;
        } else {
            return -child.getHeight() / 3;
        }
    }

    @Override
    public float getDampingFactor(IOverScroll overScroll, View child, @ScrollDirection int scrollDirection) {
        int absOffset = Math.abs(overScroll.getOffset(child));
        float progress = absOffset * 1f / child.getHeight();
        return 1 + 4 * progress; // factor = {1, 5}
    }

    @Override
    public int getMinFlingVelocity(IOverScroll overScroll, View child, int scrollDirection) {
        if (mMinFlingVelocity <= 0) {
            mMinFlingVelocity = ViewConfiguration.get(child.getContext()).getScaledMinimumFlingVelocity() * 15;
        }
        return mMinFlingVelocity;
    }

    @Override
    public void onOffsetChanged(IOverScroll overScroll, View child, int offset) {

    }

    @Override
    public boolean onSpringBack(IOverScroll overScroll, View child) {
        return false;
    }

    @Override
    public void onStopSpringingBack(IOverScroll overScroll, View child) {
    }
}
