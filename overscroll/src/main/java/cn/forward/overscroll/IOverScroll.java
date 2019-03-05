package cn.forward.overscroll;

import android.view.View;

/**
 * @author ziwei huang
 */
public interface IOverScroll {

    public void setOffset(View child, int offset);

    public int getOffset(View child);

    public int getMaxOffset(View child);

    public int getMinOffset(View child);

    public void stopSpringBack(View child);

    public void springBack(View child);
}
