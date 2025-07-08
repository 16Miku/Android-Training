// UnderlinedTextView.java
package com.example.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class UnderlinedTextView extends View {

    // 文本内容
    private String text = "";
    // 文本绘制画笔
    private TextPaint textPaint;
    // 下划线绘制画笔
    private Paint underlinePaint;
    // 用于多行文本布局
    private StaticLayout staticLayout;

    // 文本颜色
    private int textColor = Color.BLACK;
    // 文本大小
    private float textSize = 16f; // sp
    // 下划线颜色
    private int underlineColor = Color.GRAY;
    // 下划线粗细
    private float underlineThickness = 1.5f; // dp
    // 下划线与文本基线的距离
    private float underlineOffset = 3f; // dp

    // 构造函数：在代码中直接创建 View 时调用
    public UnderlinedTextView(Context context) {
        super(context);
        init();
    }

    // 构造函数：在 XML 布局文件中使用 View 时调用
    public UnderlinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO: 在这里可以解析 attrs 获取自定义属性
        init();
    }

    // 构造函数：在 XML 布局文件中使用 View 并指定样式时调用
    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO: 在这里可以解析 attrs 获取自定义属性
        init();
    }

    // 初始化画笔和默认值
    private void init() {
        // 初始化文本画笔
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG); // 开启抗锯齿
        textPaint.setColor(textColor);
        // 将 SP 转换为像素
        textPaint.setTextSize(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, textSize, getResources().getDisplayMetrics()));

        // 初始化下划线画笔
        underlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 开启抗锯齿
        underlinePaint.setColor(underlineColor);
        // 将 DP 转换为像素
        underlinePaint.setStrokeWidth(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, underlineThickness, getResources().getDisplayMetrics()));
        underlinePaint.setStyle(Paint.Style.STROKE); // 绘制线条

        // 将 DP 转换为像素，用于下划线偏移
        underlineOffset = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, underlineOffset, getResources().getDisplayMetrics());
    }

    /**
     * 设置文本内容
     * @param text 要显示的文本
     */
    public void setText(String text) {
        this.text = text;
        requestLayout(); // 文本内容变化可能导致 View 大小变化，需要重新测量布局
        invalidate(); // 文本内容变化，需要重绘
    }

    /**
     * 设置文本颜色
     * @param color 颜色值 (如 Color.RED)
     */
    public void setTextColor(int color) {
        this.textColor = color;
        if (textPaint != null) {
            textPaint.setColor(textColor);
            invalidate(); // 颜色变化只需重绘
        }
    }

    /**
     * 设置文本大小
     * @param sizeSp 文本大小 (sp)
     */
    public void setTextSize(float sizeSp) {
        this.textSize = sizeSp;
        if (textPaint != null) {
            textPaint.setTextSize(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, textSize, getResources().getDisplayMetrics()));
            requestLayout(); // 文本大小变化可能导致 View 大小变化，需要重新测量布局
            invalidate(); // 需要重绘
        }
    }

    /**
     * 设置下划线颜色
     * @param color 颜色值 (如 Color.BLUE)
     */
    public void setUnderlineColor(int color) {
        this.underlineColor = color;
        if (underlinePaint != null) {
            underlinePaint.setColor(underlineColor);
            invalidate(); // 颜色变化只需重绘
        }
    }

    /**
     * 设置下划线粗细
     * @param thicknessDp 粗细 (dp)
     */
    public void setUnderlineThickness(float thicknessDp) {
        this.underlineThickness = thicknessDp;
        if (underlinePaint != null) {
            underlinePaint.setStrokeWidth(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, underlineThickness, getResources().getDisplayMetrics()));
            invalidate(); // 粗细变化只需重绘
        }
    }

    /**
     * 设置下划线与文本基线的距离
     * @param offsetDp 距离 (dp)
     */
    public void setUnderlineOffset(float offsetDp) {
        this.underlineOffset = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, offsetDp, getResources().getDisplayMetrics());
        invalidate(); // 偏移变化只需重绘
    }

    // ... 其他方法和重写




    // UnderlinedTextView.java (续)

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取父容器对宽度的测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 获取父容器对高度的测量模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth;
        int measuredHeight;

        // 计算文本的测量宽度
        // 如果是 EXACTLY 模式，文本的最大宽度就是父容器给定的 widthSize
        // 如果是 AT_MOST 或 UNSPECIFIED，我们可以给一个默认的最大宽度，或者根据文本内容计算
        int contentWidth = widthSize;
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            // 如果父容器不限制宽度，我们给一个较大的默认值，或者根据文本最长行计算
            // 这里我们先假设最长行不会超过一个屏幕宽度，或者给一个默认值
            // 更精确的做法是测量单行文本的最大宽度：textPaint.measureText(text);
            contentWidth = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()); // 示例默认宽度
        }

        // 创建 StaticLayout 来测量多行文本的实际尺寸
        // StaticLayout.Builder 适用于 API 23 及以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            staticLayout = StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, contentWidth)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL) // 文本对齐方式
                    .setLineSpacing(0f, 1f) // 行间距
                    .setIncludePad(false) // 不包含顶部和底部的额外 padding
                    .build();
        } else {
            // 对于 API 23 以下的版本，使用旧的构造函数
            staticLayout = new StaticLayout(text, textPaint, contentWidth,
                    Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        }

        // 根据 StaticLayout 的测量结果确定 View 的测量宽度和高度
        // 测量宽度：取 StaticLayout 的实际宽度和父容器给定宽度的最小值
        measuredWidth = staticLayout.getWidth();
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measuredWidth = Math.min(measuredWidth, widthSize);
        }

        // 测量高度：取 StaticLayout 的实际高度和父容器给定高度的最小值
        measuredHeight = staticLayout.getHeight();
        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measuredHeight = Math.min(measuredHeight, heightSize);
        }

        // 加上 View 的 padding
        measuredWidth += getPaddingLeft() + getPaddingRight();
        measuredHeight += getPaddingTop() + getPaddingBottom();

        // 最终调用 setMeasuredDimension 保存测量结果
        setMeasuredDimension(measuredWidth, measuredHeight);
    }






    // UnderlinedTextView.java (续)

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); // 调用父类方法，处理背景等

        if (staticLayout == null) {
            // 如果文本为空或者在测量阶段未能成功创建 StaticLayout，则不绘制
            return;
        }

        // 将 Canvas 平移到 View 的内容区域（考虑 padding）
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        // 1. 绘制文本
        // StaticLayout 会处理文本的换行和对齐，直接调用 draw 方法即可
        staticLayout.draw(canvas);

        // 2. 绘制每行下划线
        int lineCount = staticLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            // 获取当前行的基线 Y 坐标
            float lineBaselineY = staticLayout.getLineBaseline(i);
            // 获取当前行的左侧 X 坐标（考虑对齐方式）
            float lineLeftX = staticLayout.getLineLeft(i);
            // 获取当前行的宽度
            float lineWidth = staticLayout.getLineWidth(i);

            // 计算下划线的起始和结束点
            // Y 坐标：基线 Y + 下划线偏移量
            float underlineY = lineBaselineY + underlineOffset;
            // X 坐标：从行的左侧开始，到行的右侧结束
            float startX = lineLeftX;
            float endX = lineLeftX + lineWidth;

            // 绘制下划线
            canvas.drawLine(startX, underlineY, endX, underlineY, underlinePaint);
        }

        canvas.restore(); // 恢复 Canvas 状态
    }




}
