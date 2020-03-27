package com.theory.emhwang.androidtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.theory.emhwang.androidtest.unit.CoffeeOrder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String COFFEE_COUNT = "coffee_count";

    private final static float DEFAULT_COFFEE_PRICE = 5.0f; // 기본 커피 가격

    private TextView mTvCoffeePrice;

    private TextView mTvTotalPrice;

    private TextView mTvCoffeeCount;

    private CoffeeOrder mOrder;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOrder = new CoffeeOrder(DEFAULT_COFFEE_PRICE);

        initView();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            // 커피 갯수 증가시키기
            case R.id.btn_plus:
                mOrder.plusCoffeeCount();
                updateCoffeeCount();
                updateTotalPrice();
                break;
            // 커피 갯수 감소시키기
            case R.id.btn_minus:
                mOrder.minusCoffeeCount();
                updateCoffeeCount();
                updateTotalPrice();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(COFFEE_COUNT, mOrder.getCoffeeCount());
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mOrder.setCoffeeCount(savedInstanceState.getInt(COFFEE_COUNT));
        updateCoffeeCount();
        updateTotalPrice();
    }

    private void initView() {
        mTvCoffeePrice = findViewById(R.id.tv_price);
        mTvTotalPrice = findViewById(R.id.tv_total_price);
        mTvCoffeeCount = findViewById(R.id.tv_count);

        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
    }

    // 커피 갯수 갱신하기
    private void updateCoffeeCount() {
        mTvCoffeeCount.setText(String.valueOf(mOrder.getCoffeeCount()));
    }

    // 커피 총 금액 갱신하기
    private void updateTotalPrice() {
        mTvTotalPrice.setText(String.format("총 금액: %.1f", mOrder.getTotalPrice()));
    }

}
