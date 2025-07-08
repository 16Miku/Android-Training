package com.example.customviews;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private UnderlinedTextView myUnderlinedTextView;
    private Button btnChangeText;
    private Button btnChangeColorSize;

    private boolean isTextOriginal = true;
    private boolean isStyleOriginal = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myUnderlinedTextView = findViewById(R.id.my_underlined_text_view);
        btnChangeText = findViewById(R.id.btn_change_text);
        btnChangeColorSize = findViewById(R.id.btn_change_color_size);

        // 设置初始文本
        myUnderlinedTextView.setText("小米的新价值观，以“真诚热爱”为核心，强调用户至上的理念。其中，“和用户交朋友”的理念表明小米将更加真诚地对待用户，赢得用户的信任，并将用户放在核心地位，不仅仅关注他们的需求，还将他们视为最重要的合作伙伴。\n" +
                "\n" +
                "另外，“工程师思维”标志着小米将继续坚持技术创新，敢于探索，不断推出创新的产品。这一理念还强调了技术和质量对小米的重要性，相信技术创新和卓越质量将助力小米不断壮大。\n" +
                "\n" +
                "“主人翁精神”的价值观意味着每个小米员工都将积极参与公司的使命和愿景，肩负起荣誉和责任，坚决不为短期目标而牺牲公司的长期价值。这将有助于保持公司的可持续增长。\n" +
                "\n" +
                "“信任第一”强调了诚信的重要性，无论是对待用户、同事、股东还是合作伙伴，小米都将始终秉承诚信原则，进行坦诚沟通。\n" +
                "\n" +
                "“共创共识”将鼓励团队内的合作和民主决策，确保每个员工都有权参与重要决策的制定，并将决策结果充分沟通，保持团队的一致性。\n" +
                "\n" +
                "“结果导向”的理念则明确强调了数据和成果的重要性，小米将注重最终结果，同时也重视里程碑的达");


        btnChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextOriginal) {
                    myUnderlinedTextView.setText("这是新的短文本内容，用于演示文本变化和下划线自适应。自定义 View 的能力非常强大！\n\n您可以通过属性动画来进一步美化这些效果。");
                } else {
                    myUnderlinedTextView.setText("小米的新价值观，以“真诚热爱”为核心，强调用户至上的理念。其中，“和用户交朋友”的理念表明小米将更加真诚地对待用户，赢得用户的信任，并将用户放在核心地位，不仅仅关注他们的需求，还将他们视为最重要的合作伙伴。\n" +
                            "\n" +
                            "另外，“工程师思维”标志着小米将继续坚持技术创新，敢于探索，不断推出创新的产品。这一理念还强调了技术和质量对小米的重要性，相信技术创新和卓越质量将助力小米不断壮大。\n" +
                            "\n" +
                            "“主人翁精神”的价值观意味着每个小米员工都将积极参与公司的使命和愿景，肩负起荣誉和责任，坚决不为短期目标而牺牲公司的长期价值。这将有助于保持公司的可持续增长。\n" +
                            "\n" +
                            "“信任第一”强调了诚信的重要性，无论是对待用户、同事、股东还是合作伙伴，小米都将始终秉承诚信原则，进行坦诚沟通。\n" +
                            "\n" +
                            "“共创共识”将鼓励团队内的合作和民主决策，确保每个员工都有权参与重要决策的制定，并将决策结果充分沟通，保持团队的一致性。\n" +
                            "\n" +
                            "“结果导向”的理念则明确强调了数据和成果的重要性，小米将注重最终结果，同时也重视里程碑的达");
                }
                isTextOriginal = !isTextOriginal;
                Toast.makeText(MainActivity.this, "文本内容已切换", Toast.LENGTH_SHORT).show();
            }
        });

        btnChangeColorSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStyleOriginal) {
                    myUnderlinedTextView.setTextColor(Color.BLUE);
                    myUnderlinedTextView.setTextSize(20f);
                    myUnderlinedTextView.setUnderlineColor(Color.RED);
                    myUnderlinedTextView.setUnderlineThickness(2.5f);
                    myUnderlinedTextView.setUnderlineOffset(5f);
                } else {
                    myUnderlinedTextView.setTextColor(Color.BLACK);
                    myUnderlinedTextView.setTextSize(16f);
                    myUnderlinedTextView.setUnderlineColor(Color.GRAY);
                    myUnderlinedTextView.setUnderlineThickness(1.5f);
                    myUnderlinedTextView.setUnderlineOffset(3f);
                }
                isStyleOriginal = !isStyleOriginal;
                Toast.makeText(MainActivity.this, "样式已切换", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
