package com.example.customflowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color; // 导入颜色
import android.graphics.Paint; // 导入画笔 (如果需要绘制拖放反馈)
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent; // 导入 DragEvent
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";

    private int horizontalSpacing; // 子View之间的水平间距 (像素)
    private int verticalSpacing;   // 行之间的垂直间距 (像素)

    // 用于存储每一行子View的列表，方便在onLayout中使用
    private List<List<View>> allLines = new ArrayList<>();
    // 用于存储每一行的高度
    private List<Integer> lineHeights = new ArrayList<>();

    // 如果需要拖放时的视觉反馈，可以定义画笔
    // private Paint dropTargetPaint;

    // 构造函数1
    public FlowLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    // 构造函数2
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    // 构造函数3
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    // 统一的初始化方法
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
        try {
            horizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, 0);
            verticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, 0);
            Log.d(TAG, "Initialized with horizontalSpacing=" + horizontalSpacing + ", verticalSpacing=" + verticalSpacing);
        } finally {
            a.recycle();
        }

        // 初始化用于拖放反馈的画笔 (如果需要)
        // dropTargetPaint = new Paint();
        // dropTargetPaint.setColor(Color.parseColor("#880000FF")); // 半透明蓝色
        // dropTargetPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 启用/禁用 FlowLayout 作为拖放目标
     * @param enabled true 为启用，false 为禁用
     */
    public void setDragAndDropEnabled(boolean enabled) {
        // 在 MainActivity 中设置 OnDragListener，这里不需要重复设置
        // 但可以作为指示，如果需要内部处理拖放行为，可以设置一个默认的 OnDragListener
    }


    // --- 测量阶段 ---
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure called");

        allLines.clear();
        lineHeights.clear();

        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        // int selfHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        // int selfHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int desiredWidth = 0;
        int desiredHeight = 0;

        int currentLineWidth = 0;
        int currentLineHeight = 0;

        List<View> currentLineViews = new ArrayList<>();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 拖动中的子View可能会被设置为INVISIBLE或GONE，但我们仍然需要测量和布局它
            // 这里我们假设拖动中的View仍然参与布局，只是在ACTION_DROP后才重新排序和可见性处理
            if (child.getVisibility() == GONE) { // 忽略GONE的子View
                continue;
            }

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childMeasuredWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childMeasuredHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            boolean willExceedCurrentLine = (currentLineViews.size() > 0 &&
                    currentLineWidth + horizontalSpacing + childMeasuredWidth > (selfWidthSize - paddingLeft - paddingRight));

            if (willExceedCurrentLine && selfWidthMode != MeasureSpec.UNSPECIFIED) {
                allLines.add(currentLineViews);
                lineHeights.add(currentLineHeight);

                desiredWidth = Math.max(desiredWidth, currentLineWidth);
                desiredHeight += currentLineHeight + verticalSpacing;

                currentLineViews = new ArrayList<>();
                currentLineWidth = 0;
                currentLineHeight = 0;
            }

            currentLineViews.add(child);
            currentLineWidth += childMeasuredWidth;
            if (currentLineViews.size() > 1) {
                currentLineWidth += horizontalSpacing;
            }
            currentLineHeight = Math.max(currentLineHeight, childMeasuredHeight);
        }

        if (currentLineViews.size() > 0) {
            allLines.add(currentLineViews);
            lineHeights.add(currentLineHeight);
            desiredWidth = Math.max(desiredWidth, currentLineWidth);
            desiredHeight += currentLineHeight;
        }

        desiredWidth += paddingLeft + paddingRight;
        desiredHeight += paddingTop + paddingBottom;

        int measuredWidth = resolveSize(desiredWidth, widthMeasureSpec);
        int measuredHeight = resolveSize(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
        Log.d(TAG, "onMeasure finished: " + measuredWidth + "x" + measuredHeight);
    }

    // --- 布局阶段 ---
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout called, changed: " + changed + ", bounds: " + l + "," + t + "," + r + "," + b);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int currentX = paddingLeft;
        int currentY = paddingTop;

        for (int i = 0; i < allLines.size(); i++) {
            List<View> lineViews = allLines.get(i);
            int lineHeight = lineHeights.get(i);

            for (View child : lineViews) {
                if (child.getVisibility() == GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                int childLeft = currentX + lp.leftMargin;
                int childTop = currentY + lp.topMargin;
                int childRight = childLeft + childWidth;
                int childBottom = childTop + childHeight;

                child.layout(childLeft, childTop, childRight, childBottom);
                Log.d(TAG, "Child laid out at: " + childLeft + "," + childTop + "," + childRight + "," + childBottom);

                currentX += childWidth + lp.leftMargin + lp.rightMargin + horizontalSpacing;
            }

            currentY += lineHeight + verticalSpacing;
            currentX = paddingLeft;
        }
    }

    // --- 自定义 LayoutParams ---
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }

    // --- 拖放相关方法 ---

    /**
     * 计算拖放视图在 FlowLayout 子视图列表中的最佳插入位置。
     * 这个方法通过模拟布局过程，根据拖放坐标找到最接近的插入点。
     *
     * @param draggedView 正在被拖动的视图。
     * @param dropX       拖放事件的 X 坐标，相对于 FlowLayout 的左上角。
     * @param dropY       拖放事件的 Y 坐标，相对于 FlowLayout 的左上角。
     * @return 子视图列表中新的插入索引。
     */
    public int findInsertionIndex(View draggedView, float dropX, float dropY) {
        // 调整拖放坐标，使其相对于 FlowLayout 的内容区域（减去 padding）
        float effectiveDropX = dropX - getPaddingLeft();
        float effectiveDropY = dropY - getPaddingTop();

        int insertionIndex = getChildCount(); // 默认插入到所有子视图的末尾

        int currentYOffset = 0; // 当前行顶部相对于 FlowLayout 内容顶部的 Y 偏移
        int childGlobalIndex = 0; // 跟踪子视图在 getChildAt() 中的绝对索引

        // 遍历所有行，查找拖放点所在的行
        for (int lineIdx = 0; lineIdx < allLines.size(); lineIdx++) {
            List<View> lineViews = allLines.get(lineIdx);
            int lineHeight = lineHeights.get(lineIdx);

            // 检查 dropY 是否落在当前行的垂直范围内（包括行间距）
            if (effectiveDropY >= currentYOffset && effectiveDropY < currentYOffset + lineHeight + verticalSpacing) {
                // 拖放点在此行内，现在查找此行内的插入点

                int currentXOffset = 0; // 当前子视图左侧相对于 FlowLayout 内容左侧的 X 偏移

                for (int childInLineIdx = 0; childInLineIdx < lineViews.size(); childInLineIdx++) {
                    View child = lineViews.get(childInLineIdx);
                    // 忽略正在被拖动的视图本身，因为它将从原位置移除
                    if (child == draggedView) {
                        // 仍然计算它的空间，但不将其视为插入目标
                        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                        currentXOffset += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + (childInLineIdx > 0 ? horizontalSpacing : 0);
                        continue;
                    }

                    MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                    int childWidthWithMargins = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

                    // 计算当前子视图的中心 X 坐标（相对于 FlowLayout 内容左侧）
                    float childCenterX = currentXOffset + lp.leftMargin + child.getMeasuredWidth() / 2f;

                    if (effectiveDropX < childCenterX) {
                        // 拖放点在当前子视图中心之前，插入到此子视图之前
                        insertionIndex = childGlobalIndex;
                        return insertionIndex; // 找到最佳插入点，直接返回
                    }
                    currentXOffset += childWidthWithMargins + (childInLineIdx > 0 ? horizontalSpacing : 0);
                    childGlobalIndex++;
                }
                // 如果循环结束，说明拖放点在当前行的所有子视图之后，插入到当前行的末尾
                insertionIndex = childGlobalIndex;
                return insertionIndex; // 找到最佳插入点，直接返回

            }
            // 移动到下一行的 Y 偏移和全局索引
            currentYOffset += lineHeight + verticalSpacing;
            childGlobalIndex += lineViews.size();
        }

        // 如果 effectiveDropY 超出了所有现有行的范围，则插入到所有子视图的末尾
        return getChildCount();
    }
}
