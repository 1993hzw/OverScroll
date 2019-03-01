package cn.forward.overscroll.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollListener;
import cn.forward.overscroll.IOverScrollView;
import cn.forward.overscroll.OverScrollHorizontalBehavior;
import cn.forward.overscroll.OverScrollVerticalBehavior;
import cn.forward.overscroll.SimpleOverScrollListener;

/**
 *
 * over scrolling horizontally for RecyclerView
 * @author ziwei huang
 */
@CoordinatorLayout.DefaultBehavior(OverScrollHorizontalBehavior.class)
public class OverScrollHorizontalRecyclerView extends RecyclerView implements IOverScrollListener, IOverScrollView {

    private List<IOffsetChangeListener> mOffsetChangeListeners;
    private IOverScrollListener mDefaultOverScrollListener = new SimpleOverScrollListener();
    private IOverScrollListener mOverScrollListener = mDefaultOverScrollListener;

    private int mOverScrollOffset;

    public OverScrollHorizontalRecyclerView(Context context) {
        super(context);
    }

    public OverScrollHorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollHorizontalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean canScroll(View child, int offset, int scrollDirection) {
        if (mOverScrollListener != null) {
            return mOverScrollListener.canScroll(child, offset, scrollDirection);
        }

        return false;
    }

    @Override
    public int getMaxFlingOffset(View child, int offset, int scrollDirection) {
        if (mOverScrollListener != null) {
            return mOverScrollListener.getMaxFlingOffset(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public float getDampingFactor(View child, int offset, int scrollDirection) {
        if (mOverScrollListener != null) {
            return mOverScrollListener.getDampingFactor(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public int getMinFlingVelocity(View child, int offset, int scrollDirection) {
        if (mOverScrollListener != null) {
            return mOverScrollListener.getMinFlingVelocity(child, offset, scrollDirection);
        }

        return 0;
    }

    @Override
    public void onOffsetChanged(View child, int offset) {
        mOverScrollOffset = offset;

        if (mOverScrollListener != null) {
            mOverScrollListener.onOffsetChanged(child, offset);
        }

        for (int i = 0; i < mOffsetChangeListeners.size(); i++) {
            mOffsetChangeListeners.get(i).onOffsetChanged(child, offset);
        }
    }

    @Override
    public void setOverScrollListener(final IOverScrollListener overScrollListener) {
        mOverScrollListener = overScrollListener;
        if (overScrollListener != null) {
            overScrollListener.onOffsetChanged(this, getOverScrollOffset());
        }
    }

    @Override
    public IOverScrollListener getOverScrollListener() {
        return mOverScrollListener;
    }

    public IOverScrollListener getDefaultOverScrollListener() {
        return mDefaultOverScrollListener;
    }

    @Override
    public void addOffsetChangeListener(final IOffsetChangeListener listener) {
        if (mOffsetChangeListeners == null) {
            mOffsetChangeListeners = new ArrayList<>();
        }

        if (listener != null && !mOffsetChangeListeners.contains(listener)) {
            mOffsetChangeListeners.add(listener);
            listener.onOffsetChanged(this, getOverScrollOffset());
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
