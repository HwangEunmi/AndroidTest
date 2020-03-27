package com.theory.emhwang.androidtest;

import com.theory.emhwang.androidtest.unit.Calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void addTest() {
        final int result = mCalculator.add(14, 10); // 14+10 => 24
        assertEquals(25, result); // 기대하는 값 : 25, 계산값 : 24
    }

    @Test
    public void minusTest() {
        final int result = mCalculator.minus(15, 10); // 15-10 => 5
        assertEquals(5, result); // 기대하는 값 : 5, 계산값 : 5
    }
}

// @Before : @Test 를 시작하기 전, 즉 사전에 진행해야 할 사전 정의에 해당된다.
//           @Test 가 시작되기 전 항상 호출하게 된다.
// @Test   : @Before 가 완료되면 실제 코드 테스트를 진행하게 된다.
// 다른 어노테이션 참고 URL : http://junit.sourceforge.net/javadoc/org/junit/package-summary.html
