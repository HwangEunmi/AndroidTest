package com.theory.emhwang.androidtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.theory.emhwang.androidtest.unit.CoffeeOrder;

public class CoffeeOrderTest {

    private static final float PRICE_TEST = 5.0f;

    private CoffeeOrder mOrder;

    @Before
    public void setUp() {
        mOrder = new CoffeeOrder(PRICE_TEST);
    }

    @Test
    public void orderIsNotNull() {
        // Null 일 경우 Exception 발생
        assertNotNull(mOrder);
    }

    // 커피 갯수를 증가시켜서 주문한다.
    @Test
    public void orderPlusCoffee() {
        // 시나리오 : 초기 커피 갯수는 0
        //            1개 증가시켰을때 1이 아니면 Error,
        //            25개로 셋팅하고 1개 증가시켰을때 26이 아니면 Error.
        mOrder.plusCoffeeCount();
        // 두 값이 같지 않다면 Error 발생
        assertEquals(1, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        mOrder.plusCoffeeCount();
        assertEquals(26, mOrder.getCoffeeCount());
    }

    // 커피 갯수를 감소시켜서 주문한다.
    @Test
    public void orderMinusCoffee() {
        // 시나리오 : 초기 커피 갯수는 0
        //            1개 감소시켰을때 0이 아니면 Error,
        //            25개로 셋팅하고 1개 감소시켰을때 24가 아니면 Error.
        mOrder.minusCoffeeCount();
        assertEquals(0, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        mOrder.minusCoffeeCount();
        assertEquals(24, mOrder.getCoffeeCount());
    }

    // 커피 총 금액을 구한다.
    @Test
    public void orderTotalPrice() {
        // 시나리오 : 초기 커피 총 금액은 0.0
        //            커피를 25개로 셋팅한 후, 총 금액을 계산했을때의 값을 비교한다.
        assertEquals(0.0f, mOrder.getTotalPrice());

        mOrder.setCoffeeCount(25);
        assertEquals(PRICE_TEST * 25, mOrder.getTotalPrice());
    }

    // 커피 갯수를 구한다.
    @Test
    public void orderSetCoffeeCount() {
        // 시나리오 : 초기 커피 갯수는 0
        //            커피 갯수를 계산할때 음수는 무시하는지를 확인한다.
        mOrder.setCoffeeCount(-1);
        assertEquals(0, mOrder.getCoffeeCount());

        mOrder.setCoffeeCount(25);
        assertEquals(25, mOrder.getCoffeeCount());
    }

}
