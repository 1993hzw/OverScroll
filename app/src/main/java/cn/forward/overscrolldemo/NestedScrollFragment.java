package cn.forward.overscrolldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollView;

/**
 * @author ziwei huang
 */
public class NestedScrollFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_scrollview, container, false);

        final View iconHeaderView = view.findViewById(R.id.icon_header);
        final View iconFooterView = view.findViewById(R.id.icon_footer);

        IOverScrollView overScrollView = view.findViewById(R.id.overscroll_view);
        overScrollView.addOffsetChangeListener(new IOffsetChangeListener() {
            @Override
            public void onOffsetChanged(View child, int offset) {
                if (child.getHeight() == 0) {
                    return;
                }

                int absOffset = Math.abs(offset);
                float scale = 3 * absOffset * 1f / child.getHeight();
                if (offset >= 0) {
                    iconHeaderView.setPivotX(iconHeaderView.getWidth() / 2);
                    iconHeaderView.setPivotY(0);
                    iconHeaderView.setScaleX(scale);
                    iconHeaderView.setScaleY(scale);

                    iconFooterView.setScaleX(0);
                    iconFooterView.setScaleY(0);
                } else {
                    iconFooterView.setPivotX(iconFooterView.getWidth() / 2);
                    iconFooterView.setPivotY(iconFooterView.getHeight());
                    iconFooterView.setScaleX(scale);
                    iconFooterView.setScaleY(scale);

                    iconHeaderView.setScaleX(0);
                    iconHeaderView.setScaleY(0);
                }
            }
        });

        HorizontalRecyclerViewFragment.initHorizontal(view.findViewById(R.id.horizontal_view));
//        VerticalRecyclerViewFragment.initVerticalOverScroll(view.findViewById(R.id.vertical_view));
        return view;
    }

}
