package cn.forward.overscroll;

import android.animation.Animator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

/**
 * @author ziwei huang
 */
public class SimpleOverScrollCallback implements IOverScrollCallback {

    private static final int MAX_BOUNCE_BACK_DURATION_MS = 300;
    private static final int MIN_BOUNCE_BACK_DURATION_MS = 150;

    private int mMinFlingVelocity;
    private Interpolator mInterpolator;

    @Override
    public boolean canScroll(View child, int offset, @ScrollDirection int scrollDirection) {
        return true;
    }


    @Override
    public int getMaxFlingOffset(View child, int offset, @ScrollDirection int scrollDirection) {
        if (scrollDirection == DIRECTION_DOWN || scrollDirection == DIRECTION_RIGHT) {
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
            mMinFlingVelocity = ViewConfiguration.get(child.getContext()).getScaledMinimumFlingVelocity() * 15;
        }
        return mMinFlingVelocity;
    }

    @Override
    public void onOffsetChanged(View child, int offset) {

    }

    @Override
    public void onSpringBack(View child, int offset, Animator animator) {

    }
}
