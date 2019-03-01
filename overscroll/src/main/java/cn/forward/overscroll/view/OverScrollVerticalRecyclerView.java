package cn.forward.overscroll.view;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollCallback;
import cn.forward.overscroll.IOverScrollView;
import cn.forward.overscroll.OverScrollVerticalBehavior;
import cn.forward.overscroll.SimpleOverScrollCallback;

/**
 * over scrolling horizontally for RecyclerView
 *
 * @author ziwei huang
 */
@CoordinatorLayout.DefaultBehavior(OverScrollVerticalBehavior.class)
public class OverScrollVerticalRecyclerView extends RecyclerView implements IOverScrollCallback, IOverScrollView {

    private List<IOffsetChangeListener> mOffsetChangeListeners;
    private IOverScrollCallback mDefaultOverCallback = new SimpleOverScrollCallback();
    private IOverScrollCallback mOverScrollCallback = mDefaultOverCallback;

    private Integer mOverScrollOffset;

    public OverScrollVerticalRecyclerView(Context context) {
        super(context);
    }

    public OverScrollVerticalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollVerticalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean canScroll(View child, int offset, int scrollDirection) {
        if (mOverScrollCallback != null) {
            return mOverScrollCallback.canScroll(child, offset, scrollDirection);
        }

        return false;
    }

    @Override
    public int getMaxFlingOffset(View child, int offset, int scrollDirection) {
        if (mOverScrollCallback != null) {
            return mOverScrollCallback.getMaxFlingOffset(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public float getDampingFactor(View child, int offset, int scrollDirection) {
        if (mOverScrollCallback != null) {
            return mOverScrollCallback.getDampingFactor(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public int getMinFlingVelocity(View child, int offset, int scrollDirection) {
        if (mOverScrollCallback != null) {
            return mOverScrollCallback.getMinFlingVelocity(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public void onOffsetChanged(View child, int offset) {
        mOverScrollOffset = offset;

        if (mOverScrollCallback != null) {
            mOverScrollCallback.onOffsetChanged(child, offset);
        }

        for (int i = 0; i < mOffsetChangeListeners.size(); i++) {
            mOffsetChangeListeners.get(i).onOffsetChanged(child, offset);
        }
    }

    @Override
    public void onSpringBack(View child, int offset, Animator animator) {
        if (mOverScrollCallback != null) {
            mOverScrollCallback.onSpringBack(child, offset, animator);
        }
    }

    @Override
    public void setOverScrollCallback(final IOverScrollCallback overScrollCallback) {
        mOverScrollCallback = overScrollCallback;
    }

    @Override
    public IOverScrollCallback getOverScrollCallback() {
        return mOverScrollCallback;
    }

    public IOverScrollCallback getDefaultOverCallback() {
        return mDefaultOverCallback;
    }

    @Override
    public void addOffsetChangeListener(final IOffsetChangeListener listener) {
        if (mOffsetChangeListeners == null) {
            mOffsetChangeListeners = new ArrayList<>();
        }

        if (listener != null && !mOffsetChangeListeners.contains(listener)) {
            mOffsetChangeListeners.add(listener);
        }
    }

    @Override
    public void removeOffsetChangeListener(IOffsetChangeListener listener) {
        mOffsetChangeListeners.remove(listener);
    }

    @Override
    public int getOverScrollOffset() {
        return mOverScrollOffset;
    }
}
