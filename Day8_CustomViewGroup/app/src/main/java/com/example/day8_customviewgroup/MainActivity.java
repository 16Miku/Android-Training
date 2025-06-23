package com.example.day8_customviewgroup;



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent; // 导入 DragEvent
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipData; // 导入 ClipData

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FlowLayout flowLayout;
    private Button addTagButton;
    private int tagCount = 0;

    // 模拟一些标签文本
    private String[] tagTexts = {
            "标签1", "标签2", "标签3xxxxxx", "标签4", "标签5我",
            "标签6", "标签7我的江湖", "标签8我们的", "超长标签内容测试",
            "短", "另一个标签", "Android", "自定义View", "ViewGroup练习"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowLayout = findViewById(R.id.flow_layout);
        addTagButton = findViewById(R.id.add_tag_button);

        // 为 FlowLayout 设置 OnDragListener，使其成为拖放目标
        flowLayout.setOnDragListener(new MyDragListener());

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagCount < tagTexts.length) {
                    addTagToFlowLayout(tagTexts[tagCount]);
                    tagCount++;
                } else {
                    Toast.makeText(MainActivity.this, "所有标签已添加", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 初始添加一些标签
        for (int i = 0; i < 3 && i < tagTexts.length; i++) {
            addTagToFlowLayout(tagTexts[i]);
            tagCount++;
        }
    }

    private void addTagToFlowLayout(String text) {
        TextView tagTextView = new TextView(this);
        tagTextView.setText(text);
        tagTextView.setBackgroundResource(R.drawable.tag_background);
        tagTextView.setTextColor(Color.WHITE);
        tagTextView.setPadding(20, 10, 20, 10);
        tagTextView.setGravity(Gravity.CENTER);

        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lp.setMargins(5, 5, 5, 5);

        tagTextView.setLayoutParams(lp);

        // 为标签设置长按监听器，启动拖放操作
        tagTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: Starting drag for " + ((TextView)v).getText());

                // 创建拖放数据 (这里可以为空，因为我们通过 localState 传递 View)
                ClipData data = ClipData.newPlainText("tag_text", ((TextView)v).getText());

                // 创建拖放阴影
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                // 启动拖放操作，将 v (当前标签View) 作为本地状态传递
                // v.startDrag(data, shadowBuilder, v, 0); // 旧版API
                v.startDragAndDrop(data, shadowBuilder, v, 0); // 新版API

                // 拖动开始时，将原始标签设置为不可见，避免视觉重叠
                v.setVisibility(View.INVISIBLE);
                return true; // 消费长按事件
            }
        });

        // 为标签添加点击事件 (不影响长按拖动)
        tagTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了: " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        flowLayout.addView(tagTextView);
    }

    /**
     * FlowLayout 的拖放监听器
     */
    private class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            // v 是接收拖放事件的 View (这里是 flowLayout)
            // event 包含了拖放事件的信息

            final View draggedView = (View) event.getLocalState(); // 获取被拖动的 View

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // 拖放操作开始
                    Log.d(TAG, "ACTION_DRAG_STARTED");
                    // 返回 true 表示 FlowLayout 愿意接收此拖放事件
                    return true;

                case DragEvent.ACTION_DRAG_ENTERED:
                    // 拖动阴影进入 FlowLayout 的边界
                    Log.d(TAG, "ACTION_DRAG_ENTERED");
                    // 可以设置视觉反馈，例如改变 FlowLayout 的背景色
                    // v.setBackgroundColor(Color.parseColor("#4488FF44")); // 半透明绿色
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    // 拖动阴影在 FlowLayout 边界内移动
                    // Log.d(TAG, "ACTION_DRAG_LOCATION: " + event.getX() + "," + event.getY());
                    // 可以在这里根据 event.getX(), event.getY() 实时计算插入位置并提供视觉提示
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    // 拖动阴影离开 FlowLayout 的边界
                    Log.d(TAG, "ACTION_DRAG_EXITED");
                    // 恢复 FlowLayout 的背景色
                    // v.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    return true;

                case DragEvent.ACTION_DROP:
                    // 用户在 FlowLayout 上释放了拖动阴影
                    Log.d(TAG, "ACTION_DROP at " + event.getX() + "," + event.getY());
                    // 恢复 FlowLayout 的背景色
                    // v.setBackgroundColor(Color.parseColor("#F0F0F0"));

                    // 确保被拖动的 View 仍然是 FlowLayout 的子 View (防止从其他地方拖进来)
                    if (draggedView.getParent() != flowLayout) {
                        Log.d(TAG, "Dragged view is not a child of this FlowLayout, adding.");
                        // 如果是从别的地方拖进来的，直接添加
                        // int newIndex = flowLayout.findInsertionIndex(draggedView, event.getX(), event.getY());
                        // flowLayout.addView(draggedView, newIndex);
                    } else {
                        Log.d(TAG, "Dragged view is a child of this FlowLayout, reordering.");
                        // 获取被拖动 View 的旧索引
                        int oldIndex = flowLayout.indexOfChild(draggedView);
                        // 根据拖放坐标计算新的插入索引
                        int newIndex = flowLayout.findInsertionIndex(draggedView, event.getX(), event.getY());

                        Log.d(TAG, "Old index: " + oldIndex + ", New index: " + newIndex);

                        // 先从旧位置移除
                        flowLayout.removeView(draggedView);

                        // 调整新索引，因为移除操作会改变后续元素的索引
                        if (newIndex > oldIndex && newIndex > 0) {
                            newIndex--; // 如果新位置在旧位置之后，移除后新位置会往前移一位
                        }

                        // 将 View 重新添加到新位置
                        flowLayout.addView(draggedView, newIndex);
                    }

                    // 重新测量和布局 FlowLayout
                    flowLayout.requestLayout();
                    draggedView.setVisibility(View.VISIBLE); // 重新显示被拖动的 View
                    Toast.makeText(MainActivity.this, "标签已移动", Toast.LENGTH_SHORT).show();
                    return true; // 返回 true 表示拖放操作已成功处理

                case DragEvent.ACTION_DRAG_ENDED:
                    // 拖放操作结束 (无论是成功拖放还是取消)
                    Log.d(TAG, "ACTION_DRAG_ENDED. Result: " + event.getResult());
                    // 如果拖放没有成功 (例如，没有释放到有效的 Drop Target 上)
                    if (!event.getResult()) {
                        // 恢复被拖动 View 的可见性
                        draggedView.setVisibility(View.VISIBLE);
                        Log.d(TAG, "Drag ended unsuccessfully, restoring visibility.");
                    }
                    return true; // 返回 true 表示已处理结束事件
            }
            return false;
        }
    }
}
