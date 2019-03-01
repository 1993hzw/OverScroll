package cn.forward.overscrolldemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new VerticalRecyclerViewFragment())
                            .commitAllowingStateLoss();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new ScrollViewFragment())
                            .commitAllowingStateLoss();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new VerticalRecyclerViewFragment())
                .commitAllowingStateLoss();

    }
}
