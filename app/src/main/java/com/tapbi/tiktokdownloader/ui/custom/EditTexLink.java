package com.tapbi.tiktokdownloader.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.tapbi.tiktokdownloader.R;

public class EditTexLink extends androidx.appcompat.widget.AppCompatEditText {

    private Drawable clearDrawable;

    public EditTexLink(Context context) {
        super(context);
        initView();
    }

    public EditTexLink(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EditTexLink(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initView() {
        clearDrawable = getResources().getDrawable(R.drawable.ic_close);
        //searchDrawable = getResources().getDrawable(R.drawable.ic_search_fragment);

        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(null, null,
                visible ? clearDrawable : null, null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = clearDrawable;
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }


        }
        return super.onTouchEvent(event);
    }
}
