package com.theory.emhwang.androidtest.unit;

public class CoffeeOrder {
    private float mCoffeePrice; // 커피 가격
    private int mCoffeeCount; // 커피 갯수
    private float mTotalPrice; // 총 금액

    public CoffeeOrder(final float coffeePrice) {
        this.mCoffeePrice = coffeePrice;
        mCoffeeCount = 0;
        mTotalPrice = 0;
    }

    // 커피 갯수 셋팅하기
    public void setCoffeeCount(final int count) {
        if (count >= 0) {
            mCoffeeCount = count;
            calculateTotalPrice();
        }
    }

    // 커피 갯수 리턴하기
    public int getCoffeeCount() {
        return mCoffeeCount;
    }

    // 커피 갯수 증가시키기
    public void plusCoffeeCount() {
        mCoffeeCount++;
        calculateTotalPrice();
    }

    // 커피 갯수 감소시키기
    public void minusCoffeeCount() {
        if (mCoffeeCount > 0) {
            mCoffeeCount--;
            calculateTotalPrice();
        }
    }

    // 총 금액 리턴하기
    public float getTotalPrice() {
        return mTotalPrice;
    }

    // 총 금액
    private void calculateTotalPrice() {
        mTotalPrice = mCoffeePrice * mCoffeeCount;
    }
}
