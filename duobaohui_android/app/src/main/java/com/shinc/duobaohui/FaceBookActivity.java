package com.shinc.duobaohui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shinc.duobaohui.base.BaseActivity;
import com.shinc.duobaohui.bean.FeedbackBean;
import com.shinc.duobaohui.customview.dialog.LoadingDialog;
import com.shinc.duobaohui.event.HttpFeedbackEvent;
import com.shinc.duobaohui.model.FeedbackModelInterface;
import com.shinc.duobaohui.model.impl.FeedbackModelImpl;

import de.greenrobot.event.EventBus;

/**
 * Created by liugaopo on 15/9/18.
 * 意见反馈
 */
public class FaceBookActivity extends BaseActivity {

    private final String TAG = FaceBookActivity.class.getCanonicalName();
    private ImageView imgBack;
    private EditText edtiWord;
    private TextView tvNumber;
    private Button btnSubmit;
    private FeedbackModelInterface feedbackModelInterface;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faceback_layout);
        getWindow().setBackgroundDrawable(null);
        EventBus.getDefault().register(this);
        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        feedbackModelInterface = new FeedbackModelImpl(this);
    }

    private void initListener() {
        edtiWord.addTextChangedListener(new EditChangedListener());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setEnabled(false);
                if (!TextUtils.isEmpty(edtiWord.getText().toString().trim())) {
                    loadingDialog.show();
                    String str = edtiWord.getText().toString();
                    feedbackModelInterface.getDateSubmit(str.replaceAll(" ", "%20"));
                } else {
                    Toast.makeText(FaceBookActivity.this, "请填写您想反馈的问题", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onEventMainThread(HttpFeedbackEvent feedbackEvent) {
        btnSubmit.setEnabled(true);
        loadingDialog.hideLoading();
        FeedbackBean feedbackBean = feedbackEvent.getFeedbackBean();
        if (feedbackBean != null) {
            Toast.makeText(FaceBookActivity.this, "提交" + feedbackBean.getMsg(), Toast.LENGTH_LONG).show();
            if (feedbackEvent.getFeedbackBean().getCode().equals("1")) {
                this.finish();
            }
        } else {
            Toast.makeText(FaceBookActivity.this, "提交失败，请重新提交", Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this, R.style.dialog);
        imgBack = (ImageView) findViewById(R.id.faceback_back);
        edtiWord = (EditText) findViewById(R.id.faceback_edit);
        tvNumber = (TextView) findViewById(R.id.facevback_tvNumber);
        btnSubmit = (Button) findViewById(R.id.faceback_btn_submit);
    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 299;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            if (DEBUG)
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            if (DEBUG)
            //            tvNumber.setText("还能输入" + (charMaxNum - s.length()) + "字符");
            tvNumber.setText(s.length() + "/" + charMaxNum);
            if (count != 0) {
                btnSubmit.setEnabled(true);
            }

        }

        boolean b = true;

        @Override
        public void afterTextChanged(Editable s) {
//            if (DEBUG)
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            editStart = edtiWord.getSelectionStart();
            editEnd = edtiWord.getSelectionEnd();
            if (temp.length() > charMaxNum & b) {
                b = false;
                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();

                s.delete(editStart - (temp.length() - charMaxNum), editEnd);
                int tempSelection = editStart;
                edtiWord.setText(s);
                edtiWord.setSelection(tempSelection);
            } else {
                b = true;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
