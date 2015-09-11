package com.vcokey.xs8reader.reader.graphics;

import android.graphics.Paint;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.text.TextPaint;
import android.util.TypedValue;



import java.util.ArrayList;

/**
 * 测试首行缩进和段落计算是否正确
 *
 * Created by hongxiu on 2015/9/10.
 */
public class ReaderLayoutTest extends AndroidTestCase {

    ReaderLayout layout;
    String sb;


    public void setUp() throws Exception {
        sb = "正则表达式的鼻祖或许可一直追溯到科学家对人类神经系统工作原理的早期研究。\n美国新泽西州的和出生在美国底特律的这两位神经生理方面的科学家，研究出了一种用数学方式来描述神经网络的新方法，他们创造性地将神经系统中的神经元描述成了小而简单的自动控制元，从而作出了一项伟大的工作革新。\n" +
                "在1956年,出生在被马克·吐温称为美国最美丽的城市之一的哈特福德市的一位名叫的数学科学家，他在和早期工作的基础之上，发表了一篇题目是《神经网事件的表示法》的论文，利用称之为正则集合的数学符号来描述此模型，引入了正则表达式的概念。正则表达式被作为用来描述其称之为正则集的代数”的一种表达式，因而采用了正则表达式这个术语。\n" +
                "之后一段时间，人们发现可以将这一工作成果应用于其他方面。就把这一成果应用于计算搜索算法的一些早期研究，是 Unix的主要发明人，也就是大名鼎鼎的Unix之父。Unix之父将此符号系统引入编辑器QED，然后是Unix上的编辑器ed，并最终引入grep。在其著作（中文版译作：精通正则表达式，已出到第三版）中对此作了进一步阐述讲解，如果你希望更多了解正则表达式理论和历史，推荐你看看这本书。";
        TextPaint a =new TextPaint(Paint.ANTI_ALIAS_FLAG);
        a.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getContext().getResources().getDisplayMetrics()));
        layout = new ReaderLayout(sb,720,1280,a);
        layout.setSetting(mContext);
    }

    @UiThreadTest
    public void testMeasureText() throws Exception {
        setUp();
        ArrayList<Layout.TextLine> arrayList = new ArrayList<>();
        layout.measureText(arrayList);
//        for (int i = 1; i < arrayList.size(); i++){
//            System.out.println(sb.substring(arrayList.get(i - 1),arrayList.get(i)));
//        }

        assertEquals(arrayList.get(arrayList.size() - 1).position, sb.length());
    }
}