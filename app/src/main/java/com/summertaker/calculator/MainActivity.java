package com.summertaker.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String TAG;

    private EditText etValue;
    private EditText etDivider;
    private EditText etResult;

    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAG = getClass().getSimpleName();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        //formatter.applyPattern("##,###,###");
        formatter.applyPattern("##,###,###.##");

        // 기본값
        etValue = findViewById(R.id.etValue);
        addNumberFormatListener(etValue, false);
        //etValue.requestFocus();

        // 나누기
        etDivider = findViewById(R.id.etDivider);
        addNumberFormatListener(etDivider, true);
        etDivider.requestFocus();

        // 결과
        etResult = findViewById(R.id.etResult);
        addNumberFormatListener(etResult, false);

        // Clear
        Button btClear = findViewById(R.id.btnClear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDivider.setText("");
                etResult.setText("");
                etDivider.requestFocus();
            }
        });

        // Show Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void addNumberFormatListener(final EditText editText, final boolean calc) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String dividerText = editable.toString();
                if (!dividerText.isEmpty()) {
                    editText.removeTextChangedListener(this);
                    try {
                        float divider = Float.parseFloat(dividerText.replaceAll(",", ""));
                        String valueText = etValue.getText().toString();
                        if (calc && !valueText.isEmpty()) {
                            float value = Float.parseFloat(valueText.replaceAll(",", ""));
                            String resultText = String.valueOf(value / divider);
                            etResult.setText(resultText);
                        }

                        String formatted = formatter.format(divider);
                        editText.setText(formatted);
                        editText.setSelection(editText.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }
                    editText.addTextChangedListener(this);
                } else {
                    if (calc) {
                        etResult.setText("");
                    }
                }
            }
        });
    }
}
