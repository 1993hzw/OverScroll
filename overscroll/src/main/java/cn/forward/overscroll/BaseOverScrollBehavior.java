package cn.forward.overscroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/**
 * 垂直方向上的弹性滑动和惯性滑动效果
 *
 * @author ziwei huang
 */
public abstract class BaseOverScrollBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int MAX_BOUNCE_BACK_DURATION_MS = 300;
    private static final int MIN_BOUNCE_BACK_DURATION_MS = 150;

    private final Interpolator mBounceBackInterpolator = new DecelerateInterpolator(0.8f);

    private ValueAnimator mSpringBackAnimator;
    private OverScroller mOverScroller;

    private @IOverScrollListener.ScrollDirection
    int mDirectionToEnd, mDirectionToStart;

    public BaseOverScrollBehavior() {
    }

    public BaseOverScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public abstract boolean onStartNestedScroll(CoordinatorLayout parent, View child,
                                                View directTargetChild, View target, int nestedScrollAxes, int type);

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (type == ViewCompat.TYPE_TOUCH) {
            stopSpringBack();
        }

        if (type == ViewCompat.TYPE_TOUCH) {
            if (mOverScroller != null) {
                mOverScroller.forceFinished(true);
            }
        }

        if ((axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0) {
            mDirectionToEnd = IOverScrollListener.DIRECTION_DOWN;
            mDirectionToStart = IOverScrollListener.DIRECTION_UP;
        } else {
            mDirectionToEnd = IOverScrollListener.DIRECTION_RIGHT;
            mDirectionToStart = IOverScrollListener.DIRECTION_LEFT;
        }

        coordinatorLayout.requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public abstract void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                           View target, int dx, int dy, int[] consumed, int type);

    protected void onNestedPreScrollInner(CoordinatorLayout coordinatorLayout, View child,
                                          View target, int distance, int[] consumed, int type) {
        IOverScrollListener overscrollListener = (IOverScrollListener) child;

        if (distance != 0) {
            int min, max;
            if (distance < 0) { // We're scrolling to end
                if (!overscrollListener.canScroll(child, getOffset(child), mDirectionToEnd)) {
                    return;
                }

                min = getOffset(child);
                max = 0;
            } else {  // We're scrolling to start
                if (!overscrollListener.canScroll(child, getOffset(child), mDirectionToStart)) {
                    return;
                }

                min = 0;
                max = getOffset(child);
            }
            if (min != max) {
                consumed[1] = scrollWithoutDampingFactor(child, distance, min, max);
            }
        }
    }

    @Override
    public abstract void onNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                        View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
                                        int type);

    protected void onNestedScrollInner(CoordinatorLayout coordinatorLayout, View child,
                                       View target, int distanceConsumed, int distanceUnconsumed,
                                       int type) {
        IOverScrollListener overscrollListener = (IOverScrollListener) child;

        if (distanceUnconsumed < 0) {
            // If the scrolling view is scrolling to end but not consuming, it's probably be at
            // the top of it's content

            if (!overscrollListener.canScroll(child, getOffset(child), mDirectionToEnd)) {
                return;
            }

            if (type == ViewCompat.TYPE_TOUCH) {
                scroll(child, distanceUnconsumed, 0, getMaxOffset(child));
            } else { // fling
                if ((mOverScroller.computeScrollOffset()
                        && Math.abs(mOverScroller.getCurrVelocity()) < Math.abs(overscrollListener.getMinFlingVelocity(child, getOffset(child), mDirectionToEnd)))  // too slow
                        || getOffset(child) >= overscrollListener.getMaxFlingOffset(child, getOffset(child), mDirectionToEnd)) { // reach edge
                    ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH);
                } else {
                    scroll(child, distanceUnconsumed, // slow down
                            getOffset(child), overscrollListener.getMaxFlingOffset(child, getOffset(child), mDirectionToEnd));
                }
            }

        } else if (distanceUnconsumed > 0) {
            if (!overscrollListener.canScroll(child, getOffset(child), mDirectionToStart)) {
                return;
            }

            if (type == ViewCompat.TYPE_TOUCH) {
                scroll(child, distanceUnconsumed, getMinOffset(child), 0);
            } else { // fling
                if ((mOverScroller.computeScrollOffset()
                        && Math.abs(mOverScroller.getCurrVelocity()) < overscrollListener.getMinFlingVelocity(child, getOffset(child), mDirectionToStart)) // too slow
                        || getOffset(child) <= overscrollListener.getMaxFlingOffset(child, getOffset(child), mDirectionToStart)) { // reach edge
                    ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH);
                } else {
                    scroll(child, distanceUnconsumed,  // slow down
                            overscrollListener.getMaxFlingOffset(child, getOffset(child), mDirectionToStart), getOffset(child));
                }
            }
        }
    }

    @Override
    public abstract boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY);

    protected boolean onNestedPreFlingInner(CoordinatorLayout coordinatorLayout, View child, View target, float velocity) {
        if (child == target) {
            if (mOverScroller == null) {
                mOverScroller = new OverScroller(coordinatorLayout.getContext());
            }
            /* velocityX = 0, velocityY = velocity
                        or
               velocityX = velocity, velocityY = 0
             */
            mOverScroller.fling(0, 0, 0, (int) velocity, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        return false;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                   View target, int type) {
        if (type == ViewCompat.TYPE_TOUCH) { // touching
            if (getOffset(child) != 0) { // and out of bound
                ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH);
                springBack(child);
            }
        } else {
            springBack(child);
        }
    }

    /**
     *
     */
    private final int computerWithDampingFactor(View child, int distance) {
        IOverScrollListener overscroll = (IOverScrollListener) child;
        int direction = distance > 0 ? mDirectionToStart : mDirectionToEnd;
        float factor = overscroll.getDampingFactor(child, getOffset(child), direction);
        if (factor == 0) {
            factor = 1;
        }
        int newDistance = (int) (distance / factor + 0.5f);
        return newDistance;
    }

    private final int scrollWithoutDampingFactor(View child, int distance, int minOffset, int maxOffset) {
        return computerOffset(child, getOffset(child) - distance, minOffset, maxOffset);
    }

    private final int scroll(View child, int distance, int minOffset, int maxOffset) {
        return computerOffset(child, getOffset(child) - computerWithDampingFactor(child, distance), minOffset, maxOffset);
    }

    /**
     * @return 消耗掉距离
     */
    private int computerOffset(View child, int newOffset, int minOffset, int maxOffset) {
        final int curOffset = getOffset(child);
        int consumed = 0;

        if (curOffset >= minOffset && curOffset <= maxOffset) {
            // If we have some scrolling range, and we're currently within the min and max
            // offsets, calculate a new offset
            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);

            if (curOffset != newOffset) {
                setOffset(child, newOffset);
                // Update how much dy we have consumed
                consumed = curOffset - newOffset;
            }
        }

        return consumed;
    }

    public void stopSpringBack() {
        if (mSpringBackAnimator == null) {
            return;
        }
        if (mSpringBackAnimator.isRunning()) {
            mSpringBackAnimator.cancel();
        }
    }

    public void springBack(final View child) {

        int startOffset = getOffset(child);
        if (startOffset == 0) {
            return;
        }

        if (mSpringBackAnimator == null) {
            mSpringBackAnimator = ValueAnimator.ofInt();
            mSpringBackAnimator.setInterpolator(mBounceBackInterpolator);
        }

        if (mSpringBackAnimator.isStarted()) {
            return;
        }

        // Duration is proportional to the view's size.
        float bounceBackDuration = (Math.abs(startOffset) * 1f / getMaxOffset(child)) * MAX_BOUNCE_BACK_DURATION_MS;
        mSpringBackAnimator.setIntValues(startOffset, 0);
        mSpringBackAnimator.setDuration(Math.max((int) bounceBackDuration, MIN_BOUNCE_BACK_DURATION_MS));
        mSpringBackAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setOffset(child, value);
            }
        });
        mSpringBackAnimator.start();
    }

    public void setOffset(View child, int offset) {
        IOverScrollListener overscrollListener = (IOverScrollListener) child;
        updateOffset(child, offset);
        overscrollListener.onOffsetChanged(child, getOffset(child));
    }

    protected abstract void updateOffset(View child, int offset);

    public abstract int getOffset(View child);

    public abstract int getMaxOffset(View child);

    public abstract int getMinOffset(View child);

}