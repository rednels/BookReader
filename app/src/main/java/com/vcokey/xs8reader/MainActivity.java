package com.vcokey.xs8reader;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vcokey.xs8reader.reader.widget.LayoutBuilder;
import com.vcokey.xs8reader.reader.widget.Magnifier;
import com.vcokey.xs8reader.reader.widget.TxtPage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mBookPager;

    String sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mBookPager = (ViewPager) findViewById(R.id.reader_book_pager);
        sb = "\u3000\u3000正则表达式的鼻祖或许可一直追溯到科学家对人类神经系统工作原理的早期研究。\n\u3000\u3000美国新泽西州的和出生在美国底特律的这两位神经生理方面的科学家，研究出了一种用数学方式来描述神经网络的新方法，他们创造性地将神经系统中的神经元描述成了小而简单的自动控制元，从而作出了一项伟大的工作革新。\n\u3000\u3000" +
                "在1956 年,出生在被马克·吐温称为美国最美丽的城市之一的哈特福德市的一位名叫的数学科学家，他在和早期工作的基础之上，发表了一篇题目是《神经网事件的表示法》的论文，利用称之为正则集合的数学符号来描述此模型，引入了正则表达式的概念。正则表达式被作为用来描述其称之为正则集的代数”的一种表达式，因而采用了正则表达式这个术语。\n\u3000\u3000" +
                "之后一段时间，人们发现可以将这一工作成果应用于其他方面。就把这一成果应用于计算搜索算法的一些早期研究，是 Unix的主要发明人，也就是大名鼎鼎的Unix之父。Unix之父将此符号系统引入编辑器QED，然后是Unix上的编辑器ed，并最终引入grep。在其著作（中文版译作：精通正则表达式，已出到第三版）中对此作了进一步阐述讲解，如果你希望更多了解正则表达式理论和历史，推荐你看看这本书。";

        for (int i = 0; i < 10; i++){
            View view = getLayoutInflater().inflate(R.layout.page_layout,null);
//            view.setBackgroundColor(0xff333333);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setTypeface(Typeface.MONOSPACE);
            paint.setTextSize(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
            TxtPage txtPage = (TxtPage) view.findViewById(R.id.book_pager_text);
            txtPage.setLayout(new LayoutBuilder(sb).setPaint(paint).setWidth(960)
                    .setSpacingMult(1.8f)
                    .build());
            txtPage.setBackgroundColor(0x00AA66);
//            txtPage.setTextSize(16);
//            txtPage.setTextColor(Color.BLACK);
//
//            txtPage.setText(sb.toString());
            TextView title = (TextView) view.findViewById(R.id.book_pager_title);
            title.setText("打发士大夫士大夫"+i);
            list.add(view);
        }

        mBookPager.setAdapter(new BookPagerAdapter());

    }


    ArrayList<View> list = new ArrayList<>();

    class BookPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(list.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return list.indexOf(object);
        }
    }

}
