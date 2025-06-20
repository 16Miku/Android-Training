package com.example.day3_ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public  String[] data = {
            "LLM","SpringAI","LangChain","MCP","A2A","Agent","LowCode","RAG",
            "Google","OpenAI","DeepSeek","Gemini2.5Pro","Android","Kotlin","Java","Python",
            "C++","Go","ComfyUI","MidJourney","Dify","Kafka","MySQL","Redis","Linux"
    };

    public  String[] data2 = {
            "Spring AI Alibaba 1.0 GA 正式发布，Java智能体开发进入新时代",
            "MCP 分布式落地实践：0代码实现微服务改造成 MCP Server",
            "WebDancer：从零训练一个 DeepResearch 类智能体",
            "使用 LangChain + Higress + Elasticsearch 构建 RAG 应用",
            "用 n8n + Gemini + SearXNG 简易复刻一个免费的 Deep Research",
            "抖音电商如何用扣子 Coze 打造 AI 客服？",
            "Android基础入门教程 | 菜鸟教程",
            "Manus的技术实现原理浅析与简单复刻",
            "A2A与MCP：AI互联互通协议的全面对比",
            "一分钟打造！能联网+会画图+关联知识库的DeepSeek",
            "LeetCode 热题 100 - 学习计划 - 力扣（LeetCode）全球极客挚爱的技术成长平台",
            "程序员鱼皮 AI 指南 - 编程导航教程 - 做您编程学习路上的导航员",
            "Spark入门教程（非常详细）从零基础入门到精通，看完这一篇就够了-CSDN博客",
            "Android 移动应用开发者工具 – Android 开发者  |  Android Developers",
            "字节跳动 MegaTTS3 开源：0.45B 参数实现高质量中英双语 TTS 与语音克隆 - 知乎",
            "手搓Manus？MCP 原理解析与MCP Client实践",
            "万字长文告诉你如何基于MCP实现AI应用架构新范式转型",
            "从零开始200行python代码实现LLM",
            "DeepChat 0.2.0重磅发布：多窗口AI工作台，让智能对话如虎添翼！",
            "Windows子系统、Copilot皆重磅开源，深夜炸场的微软给我们带来了哪些惊喜？",
            "字节跳动开源了一款 Deep Research 项目"
    };


    List<String> list = new ArrayList<>(  );

    List<String> list2 = new ArrayList<>(  );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        // 1.设置recyclerView的数据


        // 设置问题分类topic的横向滑动列表
        for( String s :  data ) {

            list.add(s);

        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_topic);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // 将topic行设置为横向排列



        recyclerView.setLayoutManager(linearLayoutManager);

        TopicAdapter adapter = new TopicAdapter(list);

        recyclerView.setAdapter(adapter);




        // 设置问题项question的竖向滑动列表
        for( String s :  data2 ) {

            list2.add(s);

        }

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.list_question);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);

        recyclerView2.setLayoutManager(linearLayoutManager2);

        QuestionAdapter adapter2 = new QuestionAdapter(list2);


        recyclerView2.setAdapter(adapter2);





        // 2.用于修改自助服务模块的复用组件中的图片和文字

        // 修改service1
        View selfService1 = findViewById(R.id.self_service_1);

        ImageView image1 = selfService1.findViewById(R.id.image_service);

        TextView text1 = selfService1.findViewById(R.id.text_service);


        image1.setImageResource(R.drawable.pycharm);

        text1.setText("PyCharm");


        // 修改service2
        View selfService2 = findViewById(R.id.self_service_2);

        ImageView image2 = selfService2.findViewById(R.id.image_service);

        TextView text2 = selfService2.findViewById(R.id.text_service);


        image2.setImageResource(R.drawable.studio);

        text2.setText("AndroidStudio");



        // 修改service3
        View selfService3 = findViewById(R.id.self_service_3);

        ImageView image3 = selfService3.findViewById(R.id.image_service);

        TextView text3 = selfService3.findViewById(R.id.text_service);


        image3.setImageResource(R.drawable.vscode);

        text3.setText("VSCode");


        // 修改service4
        View selfService4 = findViewById(R.id.self_service_4);

        ImageView image4 = selfService4.findViewById(R.id.image_service);

        TextView text4 = selfService4.findViewById(R.id.text_service);


        image4.setImageResource(R.drawable.cursor);

        text4.setText("Cursor");


        // 修改service5
        View selfService5 = findViewById(R.id.self_service_5);

        ImageView image5 = selfService5.findViewById(R.id.image_service);

        TextView text5 = selfService5.findViewById(R.id.text_service);


        image5.setImageResource(R.drawable.chatbox);

        text5.setText("ChatBox");



        // 修改service6
        View selfService6 = findViewById(R.id.self_service_6);

        ImageView image6 = selfService6.findViewById(R.id.image_service);

        TextView text6 = selfService6.findViewById(R.id.text_service);


        image6.setImageResource(R.drawable.idea);

        text6.setText("IDEA");








    }



}