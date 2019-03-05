package cn.forward.overscrolldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.forward.overscroll.IOffsetChangeListener;
import cn.forward.overscroll.IOverScrollView;

/**
 * @author ziwei huang
 */
public class VerticalRecyclerViewFragment extends Fragment {

    public static int[] COLORS = new int[]{0xffff0000, 0xff00ff00, 0xff0000ff, 0xff00ffff, 0xffffff00, 0xffff00ff};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview_vertical, container, false);
        initVerticalOverScroll(view);
        return view;
    }

    public static void initVerticalOverScroll(View view) {
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


        RecyclerView recyclerView = view.findViewById(R.id.overscroll_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater factory = LayoutInflater.from(parent.getContext());
                View item = factory.inflate(R.layout.item_vertical, parent, false);
                return new RecyclerView.ViewHolder(item) {
                };
            }

            @Override
            public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
                TextView textView = holder.itemView.findViewById(R.id.text);
                textView.setText("" + (1 + position));

                View container = holder.itemView.findViewById(R.id.container);
                container.setBackgroundColor(COLORS[position % COLORS.length]);

                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(holder.itemView.getContext(), "" + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        });

    }
}
