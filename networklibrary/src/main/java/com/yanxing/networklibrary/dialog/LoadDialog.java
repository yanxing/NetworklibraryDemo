package com.yanxing.networklibrary.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanxing.networklibrary.R;

/**
 * 进度条
 * Created by 李双祥 on 2017/5/24.
 */
public class LoadDialog extends BaseDialog {

    public static final String ARGUMENT_KEY="tip";
    private TextView tipTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog_style);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.load_dialog, container);
        tipTxt=view.findViewById(R.id.progress_text);
        Bundle bundle=getArguments();
        if (bundle!=null){
            String tip=bundle.getString(ARGUMENT_KEY);
            if (tip!=null){
                tipTxt.setText(tip);
            }
        }
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        return view;
    }

    public void setTipTxt(String tip){
        tipTxt.setText(tip);
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount=0.47f;
        window.setAttributes(layoutParams);
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.add(this,tag);
        return transaction.commitAllowingStateLoss();
    }
}
