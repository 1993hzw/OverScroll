# OverScroll

[![](https://jitpack.io/v/1993hzw/OverScroll.svg)](https://jitpack.io/#1993hzw/OverScroll)

Use CoordinatorLayout+Behavior to achieve elastic scrolling and inertial scrolling (similar to WeChat homepage). 

利用CoordinatorLayout+Behavior实现弹性滚动和惯性滚动效果(类似微信首页).

# Usage 用法

#### Gradle 

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
 
dependencies {
    implementation 'com.github.1993hzw:OverScroll:1.0'
}
```

In your layout.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    ...

    <cn.forward.overscroll.view.OverScrollVerticalRecyclerView
        android:background="#0ff"
        android:id="@+id/overscroll_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    ...

</android.support.design.widget.CoordinatorLayout>
```

You can use `OverScrollHorizontalRecyclerView`, `OverScrollVerticalRecyclerView` or `OverScrollScrollView` inside CoordinatorLayout.

Now, your layout achieve elastic scrolling and inertial scrolling!

# Extend 拓展

You can set [IOverScrollCallback](https://github.com/1993hzw/OverScroll/blob/master/overscroll/src/main/java/cn/forward/overscroll/IOverScrollCallback.java) or add [IOffsetChangeListener](https://github.com/1993hzw/OverScroll/blob/master/overscroll/src/main/java/cn/forward/overscroll/IOffsetChangeListener.java), if you want to achieve more complex interactions.

```java
IOverScrollView overScrollView = findViewById(R.id.overscroll_view);
overScrollView.setOverScrollCallback(new IOverScrollCallback() {
    @Override
    public boolean canScroll(IOverScroll overScroll, View child, int scrollDirection) {
       ...
    }

    @Override
    public int getMaxFlingOffset(IOverScroll overScroll, View child, int scrollDirection) {
        ...
    }

    @Override
    public float getDampingFactor(IOverScroll overScroll, View child, int scrollDirection) {
        ...
    }

    @Override
    public int getMinFlingVelocity(IOverScroll overScroll, View child, int scrollDirection) {
        ...
    }

    @Override
    public void onOffsetChanged(IOverScroll overScroll, View child, int offset) {
        ...
    }

    @Override
    public boolean onSpringBack(IOverScroll overScroll, View child) {
        ...
    }

    @Override
    public void onStopSpringingBack(IOverScroll overScroll, View child) {
        ...
    }
});

overScrollView.addOffsetChangeListener(new IOffsetChangeListener() {
    @Override
    public void onOffsetChanged(View child, int offset) {
        ...
    }
});
```