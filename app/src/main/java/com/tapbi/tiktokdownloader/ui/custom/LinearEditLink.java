package com.tapbi.tiktokdownloader.ui.custom;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.interfaces.ICallBack;

public class LinearEditLink extends LinearLayout {

    private Context context;
    private EditText edtLink;
    private ImageView imgPaste;

    private ICallBack iCallBack;

    public LinearEditLink(Context context) {
        super(context);
    }

    public LinearEditLink(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearEditLink(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinearEditLink(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        initView();
        edtLink.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (iCallBack != null) {
                        iCallBack.EditLinkAciton(edtLink.getText().toString());
                    }

                    //hide key broad
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
                return false;
            }
        });

        edtLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgPaste.setVisibility(GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = edtLink.getText().toString();
            }
        });



    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_edt_link, this);

        edtLink = findViewById(R.id.edt_link);
        imgPaste = findViewById(R.id.img_paste);
    }


}
