package com.wzhnsc.testswiperefreshrecyclerview.cards;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzhnsc.testswiperefreshrecyclerview.R;
import com.wzhnsc.testswiperefreshrecyclerview.beans.BannerInfoBean;
import com.wzhnsc.testswiperefreshrecyclerview.beans.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class AutoScrollViewCard extends BaseCard {
    private ViewPager mViewPager;
    private ImageView mImagePages[];

    // 轮播图模拟数据
    private List<BannerInfoBean> getTestData() {
        List<BannerInfoBean> listBannerInfoBean = new ArrayList<>();

        BannerInfoBean biData = new BannerInfoBean();
        biData.setStrTitle("这是第一页");
        biData.setStrBgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=bb99d6add2c8a786be2a4c0f5708c9c7/d50735fae6cd7b8900d74cd40c2442a7d9330e29.jpg");
        listBannerInfoBean.add(biData);

        BannerInfoBean biData2 = new BannerInfoBean();
        biData2.setStrTitle("这是第二页");
        biData2.setStrBgUrl("http://g.hiphotos.baidu.com/image/w%3D310/sign=7cbcd7da78f40ad115e4c1e2672e1151/eaf81a4c510fd9f9a1edb58b262dd42a2934a45e.jpg");
        listBannerInfoBean.add(biData2);

        BannerInfoBean biData3 = new BannerInfoBean();
        biData3.setStrTitle("这是第三页");
        biData3.setStrBgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=392ce7f779899e51788e3c1572a6d990/8718367adab44aed22a58aeeb11c8701a08bfbd4.jpg");
        listBannerInfoBean.add(biData3);

        BannerInfoBean biData4 = new BannerInfoBean();
        biData4.setStrTitle("这是第四页");
        biData4.setStrBgUrl("http://d.hiphotos.baidu.com/image/w%3D310/sign=54884c82b78f8c54e3d3c32e0a282dee/a686c9177f3e670932e4cf9338c79f3df9dc55f2.jpg");
        listBannerInfoBean.add(biData4);

        BannerInfoBean biData5 = new BannerInfoBean();
        biData5.setStrTitle("这是第五页");
        biData5.setStrBgUrl("http://e.hiphotos.baidu.com/image/w%3D310/sign=66270b4fe8c4b7453494b117fffd1e78/0bd162d9f2d3572c7dad11ba8913632762d0c30d.jpg");
        listBannerInfoBean.add(biData5);

        return listBannerInfoBean;
    }

    // 轮播banner的数据
    private List<BannerInfoBean> mBannerDataList;

    // 滑动的图片集合
    private List<ImageView> mImageViewList;

    public AutoScrollViewCard(Context context) {
        super(context);
    }

    public AutoScrollViewCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void createImageView(int Position) {
        ImageView imageView = new ImageView(mContext);
        // 保持原图大小，以原图的几何中心点和ImagView的几何中心点为基准，只绘制ImagView大小的图像
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(getContext())
             .load(mBannerDataList.get(Position).getStrBgUrl())
             .into(imageView);

        mImageViewList.add(imageView);
    }

    @Override
    protected int getResId() {
        return R.layout.layout_auto_scroll_view_card;
    }

    @Override
    protected void findViews() {
        mBannerDataList = getTestData();

        mImagePages = new ImageView[3];

        mImagePages[0] = new ImageView(mContext);
        mImagePages[0].setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImagePages[1] = new ImageView(mContext);
        mImagePages[1].setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImagePages[2] = new ImageView(mContext);
        mImagePages[2].setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImageViewList = new ArrayList<>();

        // 初始化图片资源
        for (int i = 0; i < mBannerDataList.size(); i++) {
            createImageView(i);
        }

        mViewPager = (ViewPager)findViewById(R.id.vp_banner);
        // 设置填充ViewPager页面的适配器
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return (1 == mBannerDataList.size()) ? 1 : Integer.MAX_VALUE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final int mapPos = position % mBannerDataList.size();

                Log.i("ViewPageAutoScroll", "ViewPageAdapter - instantiateItem -" +
                      " position = " + position +
                      " mapPos = "   + mapPos);

                ImageView iv = mImagePages[position % 3];

                if (null != iv.getParent()) {
                    ((ViewGroup)iv.getParent()).removeView(iv);
                }

                //iv.setImageDrawable(mImageViewList.get(mapPos).getDrawable());
                iv.setImageResource(R.drawable.icon_watch_num);

                container.addView(iv);

                return iv;
            }

            @Override
            public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
                Log.i("ViewPageAutoScroll", "ViewPageAdapter - destroyItem - arg1 = " + arg1);

                // 循环时不可以在这里释放重复利用的视图，
                // 因为左翻时是先instantiateItem创建新位置视图再在这里释放最右边的旧视图，就会出现空白页
                // 右翻时则是先释放，再创建
            /*
            11-21 18:43:41.910 19297-19297/com.wzhnsc.testviewpageautoscroll I/ViewPageAutoScroll: ViewPageAdapter - destroyItem - arg1 = 1073741826
            11-21 18:43:41.910 19297-19297/com.wzhnsc.testviewpageautoscroll I/ViewPageAutoScroll: ViewPageAdapter - instantiateItem - position = 1073741829 mapPos = 1
            11-21 18:45:45.180 19297-19297/com.wzhnsc.testviewpageautoscroll I/ViewPageAutoScroll: ViewPageAdapter - instantiateItem - position = 1073741826 mapPos = 0
            11-21 18:45:45.180 19297-19297/com.wzhnsc.testviewpageautoscroll I/ViewPageAutoScroll: ViewPageAdapter - destroyItem - arg1 = 1073741829
             */
//            ((ViewPager)arg0).removeView((View)arg2);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }

    @Override
    public void bindData(BaseBean baseBean) {
    }
}
