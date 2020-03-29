
**프로젝트 소개**
-------

안드로이드에서의 테스트 종류는 크게 UI 테스트, 단위 테스트로 두가지입니다.
프로젝트의 규모가 커질수록, 개발하는 구성원이 많아질수록 안정성을 위해 테스트 코드 작성은 꼭 필요합니다.
그러므로 테스트 코드 작성 방법을 꼭 알아야 한다는 생각에 정리해보았습니다.

내용중에 테스트 코드 작성방법과 상관 없는 주제가 있을 수도 있습니다. 그런 내용은 제가 공부하면서 궁금한 점이 꼬리에 꼬리를 물었기때문에 같이 정리하였습니다.
그런 내용은 Skip 하셔도 됩니다!

프로젝트는 지속적으로 추가 될 예정입니다. 상단의 Star, Watching 버튼을 클릭하시면 구독 알림을 받으실 수 있습니다 :)


----------


**목차**
--

 - [프로젝트 소개](#프로젝트-소개)
 - [목차](#목차)
 - [전체 플로우](#전체-플로우)
  - [테스트를 하는 이유?](#테스트를-하는-이유?)
  - [Android 테스트 종류](#Android-테스트-종류)
  - [UI 테스트](#UI-테스트)
  - [단위 테스트](#단위-테스트)
    - [JUnit](#JUnit)

----------

**전체 플로우**
----------





**테스트를 하는 이유?**
------

테스트 코드 작성이 과연 '필요한가'에 대한 답은 사실 없다. 

정말 중요한건, '코드'가 정상적으로 동작하는지 '확신'을 할 수 있는지를 대다수가 공감해야 한다는 것이다. 

테스트를 진행하면 자신의 코드에 대한 안정성 검사와 자신감을 얻을 수 있다. 

그러므로 어느정도 규모가 되면 테스트 코드 작성을 하는 것이 좋다. 




**Android 테스트 종류**
------

**[1] UI 테스트**

사용자 인터랙션(버튼 클릭, 텍스트 입력 등) 을 평가한다.

관련 툴 : Espresso, UIAutomator, Robotium, Robolectric


**[2] 단위 테스트**

일반적으로 코드의 유닛 단위(메소드, 클래스, 컴포넌트)의 기능을 실행하는 방식이다.

흔히 개발하게 되면, 매 '기능 단위별'로 테스트 코드를 구성하게 되는데 이때 사용되는 테스트 코드가 단위 테스트이다. 

관련 툴 : JUnit, Mockito


**[3] Instrumentation Test**

실제 단말이나 에뮬레이터에서 실행되는 테스트이다. 안드로이드 환경에서 테스트하기 때문에 실제 Instrumentation API에 접근이 가능하다.

Instrumentation Test는 단위 테스트에 비해서 속도가 느린것이 단점이다.

실제로 앱을 빌드하고 실행시키는 과정이 매 테스트마다 포함되기 때문이다.


**UI 테스트**
------

// TODO : 추후에 올릴 예정 ...


**단위 테스트**
------

**JUnit**
------

단위 테스트중 JUnit의 사용 방법을 살펴본다.

일단 새 프로젝트를 만들면 기본적으로 아래와 같이 테스트에 필요한 파일들이 자동으로 셋팅되어 있다. 

![0](/image/0.PNG)

만약 자동으로 설정되어있지 않다면 추가해주면 된다.


이제 Android/Project 단계에서 앱의 파일들을 확인해보면 src/androidTest 파일과 src/Test 파일이 있는 것을 확인할 수 있다.

![1](/image/1.PNG)

androidTest 파일이 UI 테스트를 진행하는 파일들이 있는 곳이고

test 파일이 단위 테스트를 진행하는 파일들이 있는 곳이다. 


먼저 테스트 샘플코드인 ExampleUnitTest 클래스를 살펴본다.

![2](/image/2.PNG)

예제파일로 위와 같이 기본 설정 되어있다. 

assertEquals() 메소드는 기대값과 결과값의 변수를 파라미터로 받는다. (예제에선 기대값이 4, 결과값이 2+2 이므로 결과는 참)

![3](/image/3.PNG)

![4](/image/4.PNG)

이 테스트 파일을 실행해보면 ok 결과가 나온다.

참고로 테스트 파일을 실행할땐 테스트 파일의 오른쪽 버튼을 눌러서 'Run ExampleUnitTest' 를 눌러 실행하면 된다.



만약 실행결과가 참이라면 초록색으로 표시되고, 거짓이라면 붉은색으로 표시된다.

![5](/image/5.PNG)

![6](/image/6.PNG)

-----------------

이제 간단한 예제를 통해 더 자세히 살펴보자.

![9](/image/9.PNG)

간단한 연산을 할 수 있는 Calculator 클래스를 만들어준다.


```java
public class Calculator {

    public int add(final int a, final int b) {
        return a + b;
    }

    public int minus(final int a, final int b) {
        return a - b;
    }

}

```  

그리고 테스트 코드인 CalculatorTest 클래스도 만들어준다.


```java
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

```  

간단한 덧셈/뺄셈 연산을 할 수 있는 클래스이다. 

---------------
**주요 어노테이션**들을 살펴본다.

**1) @Before :** @Test를 시작하기 전 사전에 진행해야 할 동작에 해당된다. @Test가 시작되기 전 항상 호출되게 된다.

**2) @After :** @After는 모든 테스트가 종료되면 호출된다. 메모리에서 Resource를 Release 할 수 있다. 

**3) @Test :** @Before가 완료되면 실제 코드 테스트를 진행하게 된다. 

다른 어노테이션 참고 URL은 다음과 같다.

참고 : [http://junit.sourceforge.net/javadoc/org/junit/package-summary.html](http://junit.sourceforge.net/javadoc/org/junit/package-summary.html)

---------------

이제 **주요 메소드**를 살펴본다.

**1) assertEquals() :** 두 기본형/객체가 동일한지 확인한다.

**2) assertTrue(조건) :** 조건이 참인지 확인한다.

**3) assertFalse(조건) :** 조건이 거짓인지 확인한다.

**4) assertNotNull(object) :** 객체가 Null이 아닌지 확인한다.

**5) assertNull(object) :** 객체가 Null인지 확인한다.

**6) assertSame(expected, actual) :** 두 객체의 참조값이 같은지 확인한다.

**7) assertNotSame(unexpected, actual) :** 두 객체의 참조값이 같지 않은지 확인한다.

**8) assertArrayEquals(expectedArray, actualArray) :** 두 배열이 동일한지 아닌지를 확인한다.


------------------------------

이제 조금은 제대로된 연산 클래스를 살펴본다. 

![10](/image/10.PNG)


커피의 수량과 커피가격을 받아 총 금액을 계산하는 로직이다.

 
 MainActivity에선 증가/감소 버튼과 커피 수량, 총 금액을 나타내는 뷰를 셋팅하였다.
 
 ```java
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

```  

그다음 커피 연산을 하는 CoffeeOrder 클래스이다.

 
 ```java
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

```  

마지막으로 테스트 코드인 CoffeeOrderTest 클래스이다.

 
 ```java
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

```  
























