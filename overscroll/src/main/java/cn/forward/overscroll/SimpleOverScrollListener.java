package cn.forward.overscroll;

import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author ziwei huang
 */
public class SimpleOverScrollListener implements IOverScrollListener {

    private int mMinFlingVelocity;

    @Override
    public boolean canScroll(View child, int offset, @ScrollDirection int scrollDirection) {
        return true;
    }


    @Override
    public int getMaxFlingOffset(View child, int offset, @ScrollDirection int scrollDirection) {
        if (scrollDirection == DIRECTION_DOWN) {
            return child.getHeight() / 3;
        } else {
            return -child.getHeight() / 3;
        }
    }

    @Override
    public float getDampingFactor(View child, int offset, @ScrollDirection int scrollDirection) {
        int absOffset = Math.abs(offset);
        float progress = absOffset * 1f / child.getHeight();
        return 1 + 4 * progress; // factor = {1, 5}
    }

    @Override
    public int getMinFlingVelocity(View child, int offset, int scrollDirection) {
        if (mMinFlingVelocity <= 0) {
            mMinFlingVelocity = ViewConfiguration.get(child.getContext()).getScaledMinimumFlingVelocity() * 20;
        }
        return mMinFlingVelocity;
    }

    @Override
    public void onOffsetChanged(View child, int offset) {

    }
}
