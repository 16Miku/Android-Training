package com.example.smartrefreshlayout_demo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;


/**
 * 详细文字讲解说明：
 *
 * static { ... } 块： 这是 Java 中的静态代码块，在类加载时执行，因此是进行全局配置的理想位置。
 * SmartRefreshLayout.setDefaultRefreshHeaderCreator(...)： 设置全局默认的刷新头部创建器。当你在 XML 中不指定 srlHeader 属性时，或者在代码中不调用 setRefreshHeader() 时，SmartRefreshLayout 就会使用这个默认创建器来创建头部。
 * SmartRefreshLayout.setDefaultRefreshFooterCreator(...)： 类似地，设置全局默认的加载底部创建器。
 * layout.setPrimaryColorsId(...)： 在创建器中，你可以设置刷新布局的主题色和强调色，这些颜色会应用到 Header/Footer 的背景和文字/图标上。
 * android:name=".MyApplication"： 在 AndroidManifest.xml 中声明你的自定义 Application 类，确保其 static 块和 onCreate() 方法在应用启动时被执行。
 *
 *
 * 问题根源：配置优先级
 * SmartRefreshLayout 的配置遵循一定的优先级规则：
 * XML 中直接声明的 Header/Footer 子视图： 如果你在 SmartRefreshLayout 标签内部显式地添加了 <com.scwang.smart.refresh.header.ClassicsHeader /> 或 <com.scwang.smart.refresh.footer.ClassicsFooter /> 这样的子视图，那么 SmartRefreshLayout 会优先使用这些在 XML 中声明的 Header/Footer。它将不会去调用 MyApplication 中设置的 DefaultRefreshHeaderCreator 和 DefaultRefreshFooterCreator 来创建 Header/Footer。
 * XML 中 `SmartRefreshLayout` 标签上的属性： app:srlPrimaryColor 和 app:srlAccentColor 这些属性是直接作用在 SmartRefreshLayout 实例上的。它们会覆盖任何通过代码（包括在 MyApplication 的 static 块中通过 layout.setPrimaryColorsId() 设置的）对 SmartRefreshLayout 实例设置的默认主题色。
 *
 * 结论：
 * 您的全局配置没有生效，是因为：
 * 您在 activity_main.xml 中显式声明了 `ClassicsHeader` 和 `ClassicsFooter`，导致 SmartRefreshLayout 根本就没有使用 MyApplication 中定义的默认创建器。
 * 即使 MyApplication 中的创建器被调用了，activity_main.xml 中 app:srlPrimaryColor="@color/black" 的设置也会覆盖 MyApplication 中 layout.setPrimaryColorsId(R.color.pink) 的设置，因为它在局部 XML 中被指定了。
 *
 * 2. 解决方案
 * 要让您在 MyApplication 中设置的全局配置生效，您需要：
 * (1)从 `activity_main.xml` 中移除显式声明的 `Header` 和 `Footer` 子视图。 这将使 SmartRefreshLayout 回退到使用全局默认创建器。
 * (2)从 `activity_main.xml` 中移除 `SmartRefreshLayout` 标签上的 `app:srlPrimaryColor` 和 `app:srlAccentColor` 属性。 这将允许 MyApplication 中设置的主题色生效。
 */
public class MyApplication extends Application {
    // SmartRefreshLayout 允许你进行全局配置，例如设置默认的 Header/Footer 样式，或者统一修改某些行为。
    // android:name=".MyApplication"： 在 AndroidManifest.xml 中声明你的自定义 Application 类，确保其 static 块和 onCreate() 方法在应用启动时被执行。

    // static 代码块在类加载时执行，用于设置全局默认 Header/Footer
    static {



        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(

                new DefaultRefreshHeaderCreator() {
                    @NonNull
                    @Override
                    public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {

                        // 全局设置这个 RefreshLayout 的主题颜色
                        // 同时设置 PrimaryColor (背景) 和 AccentColor (文本/图标)
                        // Primary color pink, Accent color white
                        // layout.setPrimaryColorsId( R.color.blue,R.color.green );

                        // 返回经典 Header
                        return new ClassicsHeader(context)
                                .setPrimaryColorId(R.color.blue)
                                .setAccentColorId(R.color.black);
                    }
                }
        );


        // 设置全局的 Footer  构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(

                new DefaultRefreshFooterCreator() {
                    @NonNull
                    @Override
                    public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {


                        // 全局设置这个 RefreshLayout 的主题颜色
                        // 同时设置 PrimaryColor (背景) 和 AccentColor (文本/图标)
                        // Primary color , Accent color
                        // layout.setPrimaryColorsId( R.color.pink,R.color.black );

                        // 指定为经典 Footer，默认是 BallPulseFooter
                        return new ClassicsFooter(context).
                                setPrimaryColorId(R.color.pink)
                                .setAccentColorId(R.color.green);

                    }
                }


        );





    }

}
