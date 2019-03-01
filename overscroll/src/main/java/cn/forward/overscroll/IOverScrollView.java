package cn.forward.overscroll;

/**
 * @author ziwei huang
 */
public interface IOverScrollView {

    public void setOverScrollListener(IOverScrollListener overScrollListener);

    public IOverScrollListener getOverScrollListener();

    public void addOffsetChangeListener(IOffsetChangeListener listener);

    public void removeOffsetChangeListener(IOffsetChangeListener listener);

    public int getOverScrollOffset();
}
