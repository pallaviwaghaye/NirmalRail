package com.webakruti.nirmalrail.pageindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.webakruti.nirmalrail.R;


public class PageControl extends LinearLayout implements View.OnClickListener, PageIndicator {
    private static float DEFAULT_INDICATOR_SIZE = 2.0F;
    private static float DEFAULT_INDICATOR_DISTANCE = 4.0F;
    private static int INDICATOR_SHAPE_CIRCLE = 0;
    private static int INDICATOR_SHAPE_RECTANGLE = 1;
    private int mNumOfViews;
    private ViewPager mViewPager;
    private float mIndicatorSize;
    private float mCurrentIndicatorSize;
    private float mIndicatorDistance;
    private int mIndicatorShape;
    private int mColorCurrentDefault;
    private int mColorCurrentPressed;
    private int mColorNormalDefault;
    private int mColorNormalPressed;
    private boolean mIndicatorsClickable;
    private boolean mIndicatorsEnabled;
    private ViewPager.OnPageChangeListener mListener;
    private int mScrollState;
    private String normalIndicator, selectedIndicator;

    public final void setPosition(int position) {
        if (0 <= position ? position < this.mNumOfViews : false) {
            ViewPager var10000 = this.mViewPager;
            if (this.mViewPager != null) {
                var10000.setCurrentItem(position);
            }
            this.invalidate();
        }
    }

    public final void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.updateNumOfViews();
        mViewPager.setOnPageChangeListener(this);
    }

    public void setClickable(boolean indicatorsClickable) {
        this.mIndicatorsClickable = indicatorsClickable;
        int i = 0;
        int var3 = this.getChildCount() - 1;
        if (i <= var3) {
            while (true) {
                View var10000 = this.getChildAt(i);
                if (var10000 != null) {
                    var10000.setClickable(indicatorsClickable);
                }

                if (i == var3) {
                    break;
                }
                ++i;
            }
        }
    }

    public void setEnabled(boolean indicatorsEnabled) {
        this.mIndicatorsEnabled = indicatorsEnabled;
        int i = 0;
        int var3 = this.getChildCount() - 1;
        if (i <= var3) {
            while (true) {
                View var10000 = this.getChildAt(i);
                if (var10000 != null) {
                    var10000.setEnabled(indicatorsEnabled);
                }

                if (i == var3) {
                    break;
                }

                ++i;
            }
        }

    }

    private final void updateNumOfViews() {
        int var8;
        label62:
        {
            ViewPager var10001 = this.mViewPager;
            if (this.mViewPager != null) {
                PagerAdapter var7 = var10001.getAdapter();
                if (var7 != null) {
                    var8 = var7.getCount();
                    break label62;
                }
            }

            var8 = 0;
        }

        this.mNumOfViews = var8;
        this.removeAllViews();
        int i = 0;
        int var2 = this.mNumOfViews - 1;
        if (i <= var2) {
            while (true) {
                Button b = new Button(this.getContext());
                this.setIndicatorBackground(b, i == (this.mViewPager != null ? this.mViewPager.getCurrentItem() : 0));
                boolean isCurrent = i == (this.mViewPager != null ? this.mViewPager.getCurrentItem() : 0);
                LayoutParams lp = isCurrent ? new LayoutParams((int) this.mCurrentIndicatorSize, (int) this.mCurrentIndicatorSize) : new LayoutParams((int) this.mIndicatorSize, (int) this.mIndicatorSize);
                int margin;
                if (isCurrent) {
                    margin = (int) ((this.mIndicatorDistance - (this.mCurrentIndicatorSize - this.mIndicatorSize)) / (float) 2);
                } else {
                    margin = (int) (this.mIndicatorDistance / (float) 2);
                }

                if (this.getOrientation() == HORIZONTAL) {
                    ((MarginLayoutParams) lp).leftMargin = margin;
                    ((MarginLayoutParams) lp).rightMargin = margin;
                } else {
                    ((MarginLayoutParams) lp).topMargin = margin;
                    ((MarginLayoutParams) lp).bottomMargin = margin;
                }

                b.setTag(Integer.valueOf(i));
                b.setOnClickListener((OnClickListener) this);
                this.addView((View) b, (android.view.ViewGroup.LayoutParams) lp);
                if (i == var2) {
                    break;
                }

                ++i;
            }
        }

        this.setClickable(this.mIndicatorsClickable);
        this.setEnabled(this.mIndicatorsEnabled);
        this.requestLayout();
    }

    private final void setIndicatorBackground(Button b, boolean isCurrent) {
        ShapeDrawable drawableDefault = new ShapeDrawable();
        drawableDefault.setShape(this.getIndicatorShape());
        Paint var10000 = drawableDefault.getPaint();
        if (var10000 != null) {
            var10000.setColor(isCurrent ? this.mColorCurrentDefault : this.mColorNormalDefault);
        }

        ShapeDrawable drawablePressed = new ShapeDrawable();
        drawablePressed.setShape(this.getIndicatorShape());
        var10000 = drawablePressed.getPaint();
        if (var10000 != null) {
            var10000.setColor(isCurrent ? this.mColorCurrentPressed : this.mColorNormalPressed);
        }

        StateListDrawable sld = new StateListDrawable();
        int[] statesPressed = new int[]{android.R.attr.state_pressed};
        sld.addState(statesPressed, (Drawable) drawablePressed);
        int[] statesDefault = new int[]{-android.R.attr.state_pressed};
        sld.addState(statesDefault, (Drawable) drawableDefault);
        if (Build.VERSION.SDK_INT < 16) {
            b.setBackgroundDrawable((Drawable) sld);
        } else {
            b.setBackground((Drawable) sld);
        }

    }

    private final Shape getIndicatorShape() {
        int var1 = this.mIndicatorShape;
        return var1 == Companion.getINDICATOR_SHAPE_RECTANGLE() ? (Shape) (new RectShape()) : (Shape) (new OvalShape());
    }

    public void onClick(View view) {
        if (view instanceof Button) {
            if (((Button) view).getTag() instanceof Integer) {
                Object var10000 = ((Button) view).getTag();
                if (var10000 == null) {
                    //throw new TypeCastException("kotlin.Any! cannot be cast to kotlin.Int");
                }

                int position = ((Number) ((Integer) var10000)).intValue();
                this.setPosition(position);
            }

        }
    }

    public static final class Companion {
        public static final float getDEFAULT_INDICATOR_SIZE() {
            return PageControl.DEFAULT_INDICATOR_SIZE;
        }

        public static final float getDEFAULT_INDICATOR_DISTANCE() {
            return DEFAULT_INDICATOR_DISTANCE;
        }

        public static final int getINDICATOR_SHAPE_CIRCLE() {
            return PageControl.INDICATOR_SHAPE_CIRCLE;
        }

        public static final int getINDICATOR_SHAPE_RECTANGLE() {
            return PageControl.INDICATOR_SHAPE_RECTANGLE;
        }

        private Companion() {
        }
    }


    public PageControl(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.mNumOfViews = 3;
        this.mViewPager = (ViewPager) null;
        this.mIndicatorSize = (float) 0;
        this.mIndicatorDistance = (float) 0;
        this.mCurrentIndicatorSize = (float) 0;
        this.mIndicatorShape = Companion.getINDICATOR_SHAPE_CIRCLE();
        this.mColorCurrentDefault = ContextCompat.getColor(context, R.color.yellow);
        this.mColorCurrentPressed = ContextCompat.getColor(context, R.color.yellow);
        ;
        this.mColorNormalDefault = ContextCompat.getColor(context, R.color.white);
        ;
        this.mColorNormalPressed = ContextCompat.getColor(context, R.color.yellow);
        ;
        this.mIndicatorsClickable = true;
        this.mIndicatorsEnabled = true;
        if (this.getOrientation() != LinearLayout.VERTICAL) {
            this.setOrientation(LinearLayout.HORIZONTAL);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PageControl, 0, 0);

        try {
            normalIndicator = array.getString(R.styleable.PageControl_normalIndicator);
            selectedIndicator = array.getString(R.styleable.PageControl_selectedIndicator);
        } catch (Exception e) {
            array.recycle();
        }
        this.setGravity(Gravity.CENTER);
        Resources r = this.getResources();
        if (r != null) {

            float indicatorSizeDefault = Companion.getDEFAULT_INDICATOR_SIZE() * r.getDisplayMetrics().density;
            float indicatorSize = indicatorSizeDefault;
            if (indicatorSize <= (float) 0) {
                indicatorSize = indicatorSizeDefault;
            }

            this.mIndicatorSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorSize, r.getDisplayMetrics());
            float indicatorDistanceDefault = Companion.getDEFAULT_INDICATOR_DISTANCE() * r.getDisplayMetrics().density;
            float indicatorDistance = indicatorDistanceDefault;
            if (indicatorDistance <= (float) 0) {
                indicatorDistance = indicatorDistanceDefault;
            }

            this.mIndicatorDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorDistance, r.getDisplayMetrics());
            float currentIndicatorSize = indicatorSize;
            if (currentIndicatorSize <= (float) 0) {
                currentIndicatorSize = indicatorSize;
            }

            this.mCurrentIndicatorSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, currentIndicatorSize, r.getDisplayMetrics());
            this.mIndicatorShape = Companion.getINDICATOR_SHAPE_CIRCLE();
            if (this.mIndicatorShape >= Companion.getINDICATOR_SHAPE_CIRCLE() ? Companion.getINDICATOR_SHAPE_RECTANGLE() < this.mIndicatorShape : true) {
                this.mIndicatorShape = Companion.getINDICATOR_SHAPE_CIRCLE();
            }

            if (normalIndicator != null) {
                this.mColorNormalDefault = Color.parseColor(normalIndicator);

            }

            if (selectedIndicator != null) {
                this.mColorCurrentDefault = Color.parseColor(selectedIndicator);
                this.mColorCurrentPressed = Color.parseColor(selectedIndicator);
                this.mColorNormalPressed = Color.parseColor(selectedIndicator);
            }


        }

    }

    public void setNormalIndicatorColor() {

    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {

    }

    @Override
    public void setCurrentItem(int item) {

    }


    @Override
    public void notifyDataSetChanged() {

    }


    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        PageControl.this.updateNumOfViews();
        PageControl.this.setPosition(position);
        invalidate();
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

}
