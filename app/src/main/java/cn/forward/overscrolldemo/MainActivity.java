package cn.forward.overscrolldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overscroll);

        final View iconHeaderView = findViewById(R.id.icon_header);
        final View iconFooterView = findViewById(R.id.icon_footer);

        IOverScrollView overScrollView = findViewById(R.id.overscroll_view);
        overScrollView.addOffsetChangeListener(new IOffsetChangeListener() {
            @Override
            public void onOffsetChanged(View child, int offset) {
                if (child.getHeight() == 0) {
                    return;
                }

                int absOffset = Math.abs(offset);
                float scale = 3 * absOffset * 1f / child.getHeight();
                if (offset >= 0) {
                    iconHeaderView.setPivotX(child.getWidth() / 2);
                    iconHeaderView.setPivotY(0);
                    iconHeaderView.setScaleX(scale);
                    iconHeaderView.setScaleY(scale);

                    iconFooterView.setScaleX(0);
                    iconFooterView.setScaleY(0);
                } else {
                    iconFooterView.setPivotX(child.getWidth() / 2);
                    iconFooterView.setPivotY(0);
                    iconFooterView.setScaleX(scale);
                    iconFooterView.setScaleY(scale);

                    iconHeaderView.setScaleX(0);
                    iconHeaderView.setScaleY(0);
                }
            }
        });
    }
}
