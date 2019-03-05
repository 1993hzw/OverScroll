package cn.forward.overscroll.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import cn.forward.overscroll.OverScrollHorizontalBehavior;

/**
 * over scrolling horizontally for RecyclerView
 *
 * @author ziwei huang
 */
@CoordinatorLayout.DefaultBehavior(OverScrollHorizontalBehavior.class)
public class OverScrollHorizontalRecyclerView extends OverScrollVerticalRecyclerView {

    public OverScrollHorizontalRecyclerView(Context context) {
        super(context);
    }

    public OverScrollHorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverScrollHorizontalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
