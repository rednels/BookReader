package com.vcokey.xs8reader;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.vcokey.xs8reader.reader.widget.TxtPage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mBookPager;

    StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookPager = (ViewPager) findViewById(R.id.reader_book_pager);
        for (int i = 0; i< 20; i++){
            sb.append("")
                    .append("阿斯蒂發你是誰蒂芬阿斯顿f阿斯蒂芬asdfsdfa阿斯蒂芬阿斯顿f阿斯蒂芬阿斯蒂芬阿斯顿f")
                    .append("\n");
        }

        for (int i = 0; i < 10; i++){
            View view = getLayoutInflater().inflate(R.layout.page_layout,null);
//            view.setBackgroundColor(0xff333333);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setTextSize(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
            TxtPage txtPage = (TxtPage) view.findViewById(R.id.book_pager_text);
            txtPage.setLayout(new LayoutBuilder(sb.toString()).setPaint(paint).setWidth(640)
                    .setSpacingMult(1.5f)
                    .build());
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
