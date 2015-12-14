package com.kayya.underlinebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mustafakaya on 04/12/15.
 */
public class UnderlineButton extends View {

    final String TAG = "UnderlineButton";
    final String DefaultText = "Tamam";
    Paint textPainter;
    Paint linePainter;

    int defaultColor;
    int selectedColor;
    float textSize;
    String text;
    int textWidth;
    int textHeight;
    boolean shouldClick;

    public UnderlineButton(Context context) {
        super(context);
        textSize = getResources().getDimension(R.dimen.default_ub_text_size);
        text = DefaultText;
        defaultColor = getResources().getColor(R.color.default_ub_textColor);
        selectedColor = getResources().getColor(R.color.default_ub_selected_textColor);
        init();
    }

    public UnderlineButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttributeSet(context,attrs);
        init();
    }


    public UnderlineButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        resolveAttributeSet(context,attrs);
        init();
    }


    private void resolveAttributeSet(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnderlineButton);
        textSize = typedArray.getDimension(R.styleable.UnderlineButton_ub_textSize, getResources().getDimension(R.dimen.default_ub_text_size));
        text = typedArray.getString(R.styleable.UnderlineButton_ub_text);
        if (text == null || text.length() == 0){
            text = DefaultText ;
        }

        Log.i("DP Size",""+textSize);
        defaultColor = typedArray.getColor(R.styleable.UnderlineButton_ub_textColor, getResources().getColor(R.color.default_ub_textColor));
        selectedColor = typedArray.getColor(R.styleable.UnderlineButton_ub_textSelectedColor,  getResources().getColor(R.color.default_ub_selected_textColor));

        typedArray.recycle();
    }

    private void init() {
        Log.i(TAG, "" + textSize);
        textPainter = new Paint();
        textPainter.setTextSize(textSize);
        textPainter.setColor(defaultColor);
        textPainter.setAntiAlias(true);
        textPainter.setTextAlign(Paint.Align.LEFT);
        linePainter = new Paint();
        linePainter.setStrokeWidth(textSize/6);
        linePainter.setColor(defaultColor);
        linePainter.setStrokeCap(Paint.Cap.ROUND);
        Rect bounds = new Rect();
        textPainter.getTextBounds(text, 0, text.length(), bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();

        //setLayoutParams(new LinearLayout.LayoutParams(textWidth+40,textHeight+35));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawText(text, 15, textSize, textPainter);
        canvas.drawLine(10, textSize + 15, textWidth + 25, textSize + 15, linePainter);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }


    private int measureWidth(int measureSpec) {
        int preferred = textWidth + 40;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = textHeight + 27 + (int)textSize/3 ;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement = 0;

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                // This means the width of this view has been given.
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                // Take the minimum of the preferred size and what
                // we were told to be.
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                shouldClick= true;

            case MotionEvent.ACTION_POINTER_DOWN:
                textPainter.setColor(selectedColor);
                linePainter.setColor(selectedColor);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                shouldClick = false;
                break;
            case MotionEvent.ACTION_UP:
                if(shouldClick)
                    performClick();
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                textPainter.setColor(defaultColor);
                linePainter.setColor(defaultColor);
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }


}
