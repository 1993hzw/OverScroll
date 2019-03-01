package cn.forward.overscroll;

/**
 * @author ziwei huang
 */
public interface IOverScrollView {

    public void setOverScrollCallback(IOverScrollCallback overScrollCallback);

    public IOverScrollCallback getOverScrollCallback();

    public void addOffsetChangeListener(IOffsetChangeListener listener);

    public void removeOffsetChangeListener(IOffsetChangeListener listener);

    public int getOverScrollOffset();
}
