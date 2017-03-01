package com.walle.circleassetview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 环形资产分布View
 */

public class CircleAssetView extends View {

    //总资产颜色
    private int mMoneyTextColor = 0xFF0D0C0C;
    //总资产字体大小
    private float mMoneyTextSize = 17;
    //总资产提示文本颜色
    private int mMoneyTextHintColor = 0xFFADA8A9;
    //总资产提示文本字体大小
    private float mMoneyTextHintSize = 15;
    //总资产文本
    private String mMoneyText = "";
    //总资产提示文本
    private String mMoneyTextHint = "";
    //百分比 0~355
    private int mDegree = 0;
    //未覆盖inside颜色
    private int mUnreachInsideColor = 0xFFF5AF77;
    //未覆盖outside颜色
    private int mUnreachOutsideColor = 0xFFFC8424;
    //已覆盖 inside 颜色
    private int mReachInsideColor = 0xFFC0F7DF;
    //已覆盖 outside 颜色
    private int mReachOutsideColor = 0xFF20C77D;
    //半径
    private float mRadius = 62;
    //inside radius;
    private float mInsideRadius = 13;
    //outside radius
    private float mOutsideRadius = 17;
    //已覆盖inside paint
    private Paint mReachInsidePaint;
    //已覆盖outside paint
    private Paint mReachOutsidePaint;
    //未覆盖 inside Paint
    private Paint mUnreachInsidePaint;
    //未覆盖 outside Paint
    private Paint mUnreachOutsidePaint;
    //已覆盖 inside RectF
    private RectF mReachInsideRectF = new RectF(0, 0, 0, 0);
    //已覆盖 outside RectF;
    private RectF mReachOutsideRectF = new RectF(0, 0, 0, 0);
    //未覆盖 inside RectF
    private RectF mUnreachInsideRectF = new RectF(0, 0, 0, 0);
    //未覆盖 outside RectF;
    private RectF mUnreachOutsideRectF = new RectF(0, 0, 0, 0);
    //总金额 Paint
    private Paint mTextPaint;
    //总金额提示 Paint
    private Paint mTextHintPaint;
    //最大角度
    private int mMaxDegree = 360;
    //最小角度
    private int mMinDegree = 0;
    //是否绘制文本
    private boolean ifDrawText = true;
    //文本是否显示
    private static final int TEXT_VISIBLE = 0;

    //文本之间的间距
    private float mTextSpace = 2;

    /**
     * 进度条文本和文本外边框是否显示的枚举
     */
    public enum AssetTextVisibility {
        Visible , Invisible
    }


    public CircleAssetView(Context context) {
        this(context, null);
    }

    public CircleAssetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleAssetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleAssetView, defStyleAttr, 0);
        mMoneyTextColor = array.getColor(R.styleable.CircleAssetView_moneyTextColor, mMoneyTextColor);
        mMoneyTextHintColor = array.getColor(R.styleable.CircleAssetView_moneyTextHintColor, mMoneyTextHintColor);
        mMoneyTextSize = array.getDimension(R.styleable.CircleAssetView_moneyTextSize, mMoneyTextSize);
        mMoneyTextHintSize = array.getDimension(R.styleable.CircleAssetView_moneyTextHintSize, mMoneyTextHintSize);
        mDegree = array.getInteger(R.styleable.CircleAssetView_degree, mDegree);
        mMoneyText = array.getString(R.styleable.CircleAssetView_moneyText);
        mMoneyTextHint = array.getString(R.styleable.CircleAssetView_moneyTextHint);
        mRadius = array.getDimension(R.styleable.CircleAssetView_radius, mRadius);
        mInsideRadius = array.getDimension(R.styleable.CircleAssetView_insideRadius, mInsideRadius);
        mOutsideRadius = array.getDimension(R.styleable.CircleAssetView_outsideRadius, mOutsideRadius);
        mTextSpace = array.getDimension(R.styleable.CircleAssetView_textSpace,mTextSpace);
        int textVisible = array.getInt(R.styleable.CircleAssetView_text_visibility, TEXT_VISIBLE);
        if (textVisible != TEXT_VISIBLE) {
            ifDrawText = false;
        }
        array.recycle();
        initPaint();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int sizeMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(sizeMeasureSpec);
        int size = MeasureSpec.getSize(sizeMeasureSpec);
        if (mode == MeasureSpec.EXACTLY)
            result = size;
        else
            result = (int) Math.min(size, (mRadius + mInsideRadius + mOutsideRadius) * 2);
        return result;
    }

    private void initPaint() {
        //初始化已覆盖内部Paint
        mReachInsidePaint = new Paint();
        mReachInsidePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mReachInsidePaint.setStyle(Paint.Style.STROKE);
        mReachInsidePaint.setAntiAlias(true);
        //初始化已覆盖外部Paint
        mReachOutsidePaint = new Paint();
        mReachOutsidePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mReachOutsidePaint.setStyle(Paint.Style.STROKE);
        mReachOutsidePaint.setAntiAlias(true);
        //初始化未覆盖外部Paint
        mUnreachInsidePaint = new Paint();
        mUnreachInsidePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mUnreachInsidePaint.setStyle(Paint.Style.STROKE);
        mUnreachInsidePaint.setAntiAlias(true);
        //初始化未覆盖外部Paint
        mUnreachOutsidePaint = new Paint();
        mUnreachOutsidePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mUnreachOutsidePaint.setStyle(Paint.Style.STROKE);
        mUnreachOutsidePaint.setAntiAlias(true);
        //初始化总金额文本
        mTextPaint = new Paint();
        mTextPaint.setColor(mMoneyTextColor);
        mTextPaint.setTextSize(mMoneyTextSize);
        //初始化总金额提示文本
        mTextHintPaint = new Paint();
        mTextHintPaint.setColor(mMoneyTextHintColor);
        mTextHintPaint.setTextSize(mMoneyTextHintSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth();
        if (mDegree < mMinDegree || mDegree > mMaxDegree) {
            throw new IllegalArgumentException("degree must be 0 ~ 360");
        }
        if (mDegree != 0)
            //测量已覆盖
            calculateReachRect(center, canvas, mDegree);
        if (mDegree != 360)
            //测量未覆盖
            calculateUnreachRect(center, canvas, mDegree);
        if (ifDrawText){
            //绘制总金额文本
            int moneyLength = (int) mTextPaint.measureText(mMoneyText==null? "":mMoneyText);
            canvas.drawText(mMoneyText,center/2-moneyLength/2,center/2-mTextSpace/2-mMoneyTextSize/2,mTextPaint);
            //绘制总金额提示文本
            int moneyHintLength = (int) mTextHintPaint.measureText(mMoneyTextHint==null ?"":mMoneyTextHint);
            canvas.drawText(mMoneyTextHint,center/2-moneyHintLength/2,center/2+mTextSpace/2+mMoneyTextSize/2,mTextHintPaint);
        }
    }

    /**
     * 测量已覆盖
     *
     * @param center
     */
    private void calculateReachRect(int center, Canvas canvas, int mDegree) {
        if (mDegree != 360) {
            mDegree -= 1;
        }
        //已覆盖外侧
        mReachOutsidePaint.setStrokeWidth(mOutsideRadius);
        mReachOutsidePaint.setColor(mReachOutsideColor);
        mReachOutsideRectF.left = mOutsideRadius/2;
        mReachOutsideRectF.top = mReachOutsideRectF.left;
        mReachOutsideRectF.right = center - mOutsideRadius/2;
        mReachOutsideRectF.bottom = mReachOutsideRectF.right;
        canvas.drawArc(mReachOutsideRectF, -90, mDegree, false, mReachOutsidePaint);
        //已覆盖内侧
        mReachInsidePaint.setStrokeWidth(mInsideRadius);
        mReachInsidePaint.setColor(mReachInsideColor);
        mReachInsideRectF.left = mOutsideRadius + mInsideRadius/2;
        mReachInsideRectF.top = mReachInsideRectF.left;
        mReachInsideRectF.right = center - mOutsideRadius-mInsideRadius/2;
        mReachInsideRectF.bottom = mReachInsideRectF.right;
        canvas.drawArc(mReachInsideRectF, -90, mDegree, false, mReachInsidePaint);
    }

    /**
     * 测量未覆盖
     *
     * @param center
     * @param canvas
     */
    private void calculateUnreachRect(int center, Canvas canvas, int mDegree) {
        if (mDegree != 0) {
            mDegree = 360 - mDegree - 3;
        } else {
            mDegree = 360;
        }

        //未覆盖外侧
        mUnreachOutsidePaint.setStrokeWidth(mOutsideRadius);
        mUnreachOutsidePaint.setColor(mUnreachOutsideColor);
        mUnreachOutsideRectF.left = mOutsideRadius/2;
        mUnreachOutsideRectF.top = mUnreachOutsideRectF.left;
        mUnreachOutsideRectF.right = center - mOutsideRadius/2;
        mUnreachOutsideRectF.bottom = mUnreachOutsideRectF.right;
        canvas.drawArc(mUnreachOutsideRectF, mDegree == 0 ? -90 : -92, -mDegree, false, mUnreachOutsidePaint);


        //未覆盖内侧
        mUnreachInsidePaint.setStrokeWidth(mInsideRadius);
        mUnreachInsidePaint.setColor(mUnreachInsideColor);
        mUnreachInsideRectF.left = mOutsideRadius + mInsideRadius/2;
        mUnreachInsideRectF.top = mUnreachInsideRectF.left;
        mUnreachInsideRectF.right = center - mOutsideRadius - mInsideRadius/2;
        mUnreachInsideRectF.bottom = mUnreachInsideRectF.right;
        canvas.drawArc(mUnreachInsideRectF, mDegree == 0 ? -90 : -92, -mDegree, false, mUnreachInsidePaint);
    }

    /**
     * 设置文本之间的间距
     */
    public void setTextSpace(int textSpace){
        mTextSpace = textSpace;
        invalidate();
    }
    /**
     * 设置资产总金额文本颜色
     *
     * @param mMoneyTextColor
     */
    public void setMoneyTextColor(int mMoneyTextColor) {
        this.mMoneyTextColor = mMoneyTextColor;
        mTextPaint.setColor(mMoneyTextColor);
        invalidate();
    }

    /**
     * 设置资产总金额文本大小
     *
     * @param mMoneyTextSize
     */
    public void setMoneyTextSize(float mMoneyTextSize) {
        this.mMoneyTextSize = dp2px(mMoneyTextSize);
        mTextPaint.setTextSize(this.mMoneyTextSize);
        invalidate();
    }

    /**
     * 设置资产提示文本颜色
     *
     * @param mMoneyTextHintColor
     */
    public void setMoneyTextHintColor(int mMoneyTextHintColor) {
        this.mMoneyTextHintColor = mMoneyTextHintColor;
        mTextHintPaint.setColor(mMoneyTextHintColor);
        invalidate();
    }

    /**
     * 设置资产提示文本大小
     *
     * @param mMoneyTextHintSize
     */
    public void setMoneyTextHintSize(float mMoneyTextHintSize) {
        this.mMoneyTextHintSize = dp2px(mMoneyTextHintSize);
        mTextHintPaint.setTextSize(this.mMoneyTextHintSize);
        invalidate();
    }

    /**
     * 设置总资产文本
     *
     * @param mMoneyText
     */
    public void setMoneyText(String mMoneyText) {
        this.mMoneyText = mMoneyText;
        invalidate();
    }

    /**
     * 设置总资产文本提示
     *
     * @param mMoneyTextHint
     */
    public void setMoneyTextHint(String mMoneyTextHint) {
        this.mMoneyTextHint = mMoneyTextHint;
        invalidate();
    }

    /**
     * 设置角度
     *
     * @param degree
     */
    public void setDegree(int degree) {
        ValueAnimator anim = ValueAnimator.ofInt(degree);
        anim.setDuration(500);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDegree = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    /**
     * 设置未覆盖内侧颜色
     *
     * @param mUnreachInsideColor
     */
    public void setUnreachInsideColor(int mUnreachInsideColor) {
        this.mUnreachInsideColor = mUnreachInsideColor;
        mUnreachInsidePaint.setColor(mUnreachInsideColor);
        invalidate();
    }

    /**
     * 设置未覆盖外侧颜色
     *
     * @param mUnreachOutsideColor
     */
    public void setUnreachOutsideColor(int mUnreachOutsideColor) {
        this.mUnreachOutsideColor = mUnreachOutsideColor;
        mUnreachOutsidePaint.setColor(mUnreachOutsideColor);
        invalidate();
    }

    /**
     * 设置已覆盖内侧颜色
     *
     * @param mReachInsideColor
     */
    public void setReachInsideColor(int mReachInsideColor) {
        this.mReachInsideColor = mReachInsideColor;
        mReachInsidePaint.setColor(mReachInsideColor);
        invalidate();
    }

    /**
     * 设置已覆盖外侧颜色
     *
     * @param mReachOutsideColor
     */
    public void setReachOutsideColor(int mReachOutsideColor) {
        this.mReachOutsideColor = mReachOutsideColor;
        mReachOutsidePaint.setColor(mReachOutsideColor);
        invalidate();
    }

    /**
     * 设置半径
     *
     * @param mRadius
     */
    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
        invalidate();
    }

    /**
     * 设置内侧环形大小
     *
     * @param mInsideRadius
     */
    public void setInsideRadius(float mInsideRadius) {
        this.mInsideRadius = mInsideRadius;
        mReachInsidePaint.setStrokeWidth(mInsideRadius);
        mUnreachInsidePaint.setStrokeWidth(mInsideRadius);
        invalidate();
    }

    /**
     * 设置外侧环形大小
     *
     * @param mOutsideRadius
     */
    public void setOutsideRadius(float mOutsideRadius) {
        this.mOutsideRadius = mOutsideRadius;
        mReachOutsidePaint.setStrokeWidth(mOutsideRadius);
        mUnreachOutsidePaint.setStrokeWidth(mOutsideRadius);
        invalidate();
    }

    /**
     * 获取现在的旋转角度
     * @return 已覆盖区域的角度
     */
    public int getDegree() {
        return mDegree;
    }

    /**
     * 获取最小角度
     * @return
     */
    public int getMinDegree() {
        return mMinDegree;
    }


    /**
     * 获取最大角度
     * @return
     */
    public int getMaxDegree() {
        return mMaxDegree;
    }

    public void setTextVisibility(AssetTextVisibility visibility){
        ifDrawText = visibility==AssetTextVisibility.Visible;
        invalidate();
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
