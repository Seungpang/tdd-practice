### JUnit5 모듈
+ JUnit Platform: 테스트를 실행해주는 런쳐 제공, TestEngine API제공
+ Jupiter: TestEngine API 구현체로 JUnit 5를 제공
+ Vintage: JUnit 4와 3을 지원하는 TestEngine 구현체

### 주요 단언 메서드
+ org.junit.jupiter.api.Assertions 클래스가 제공하는 주요 단언 메서드

|메서드|설명|
|----|---|
|assertEquals(expected, actual)| 실제 값(actual)이 기대하는 값(expected)과 같은지 검사한다.|
|assertNotEquals(unexpected, actual)| 실제 값(actual)이 특정 값(unexpected)과 같지 않은지 검사한다.|
|assertSame(Object expected, Object actual)|두 객체가 동일한 객체인지 검사한다.|
|assertNotSame(Object unexpected, Object actual)|두 객체가 동일하지 않은 객체인지 검사한다.|
|assertTrue(boolean condition)|값이 true인지 검사한다.|
|assertFalse(Object condition)|값이 false인지 검사한다.|
|assertNull(Object actual)|값이 null인지 검사한다.|
|assertNotNull(Object actual)|값이 null이 아닌지 검사한다.|
|fail()|테스트를 실패 처리한다.|

+ org.junit.jupiter.api.Assetions가 제공하는 익셉션 발생 유무 검사 메서드

|메서드|설명|
|---|---|
|assertThrows(Class<T> expectedType, Executable executable)|executable을 실행한 결과로 지정한 타입의 익셉션이 발생하는지 검사한다.|
|assertDoesNotThrow(Executable executable)|executable을 실행한 결과로 익셉션이 발생하지 않는지 검사한다.|

### 조건에 따른 테스트
+ @EnabledOnOs, @DisabledOnOs
  + OS 열거 타입을 사용해서 테스트를 실행하거나 실행하지 않을 운영체제 조건을 지정한다. 
+ @EnabledOnJre, @DisabledOnJre
  + JRE 버전을 열거 타입을 사용해서 테스트를 실행하거나 실행하지 않을 JRE 조건을 지정한다. 
+ @EnabledIfSystemProperty, @DisabledIfSystemProperty
  + 시스템 프로퍼티 값을 비교하여 테스트 실행 여부를 결정한다.
  + @EnabledIfSystemProperty애노테이션의 named속성은 시스템 프로퍼티의 이름을 지정하고 Matches 속성에는 값의 일치 여부를 검사할 때 사용할 정규 표현식을 지정한다.
+ @EnabledIfEnvironmentVariable, @DisabledIfEnvironmentVariable
  +named속성과 matches속성을 사용한다. 차이점은 named속성에 환경변수 이름을 사용한다는 것이다. 

### 태깅과 필터링
+ @Tag
  + 클래스와 테스트 메서드에 적용할 수 있다.

```java
@Tag("integration")
public class TagTest {

  @Tag("very-slow")
  @Test
  void verySlow() {
    int result = someVerySlowOp();
    Assertions.assertEquals(result, 0);
  }
}
```
태크를 테스트 대상으로 선택하는 예
```groovy
test {
    useJUnitPlatform {
        includeTags 'integration'
        excludeTags 'slow | very-slow'
    }
}
```
### 중첩 구성
@Nested 애노테이션을 사용하면 중첩 클래스에 테스트 메서드를 추가할 수 있다.
```java
public class Outer {
	@BeforeEach
	void outerBefore() {
		System.out.println("outerBefore 메서드 실행");
	}
	
	@Test
	void outer() {
		System.out.println("outer 메서드 실행");
	}

	@AfterEach
	void outerAfter() {
		System.out.println("outerAfter 메서드 실행");
	}

	@Nested
	class NestedA {
		@BeforeEach void nestedBefore() {
			System.out.println("nestedBefore 메서드 실행");
		}

		@Test
		void nested1(){
			System.out.println("nested1 메서드 실행");
		}

		@AfterEach void nestedAfter() {
			System.out.println("nestedAfter 메서드 실행");
		}
	}
}
```

````shell
outerBefore 메서드 실행
outer 메서드 실행
outerAfter 메서드 실행
outerBefore 메서드 실행
nestedBefore 메서드 실행
nested1 메서드 실행
nestedAfter 메서드 실행
outerAfter 메서드 실행
````

### @Timeout 애노테이션을 이용한 테스트 실행 시간 검증
+ @Timeout
  + JUnit 5.5버전부터 지원하는 애노테이션으로 @Timeout 애노테이션을 사용하면 테스트가 일정 시간 내에 실행되는지 검증할 수 있다.

```java
@Test
@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
void sleep40Mills() throws InterrupedException {
	Thread.sleep(40);
}
```

### 테스트 라이프사이클
1. 테스트 메서드를 포함한 객체 생성
2. (존재하면) @BeforeEach 애노테이션이 붙은 메서드 실행
3. @Test 애노테이션이 붙은 메서드 실행
4. (존재하면) @AfterEach 애노테이션이 붙은 메서드 실행

+ @BeforeEach: 테스트를 실행하는데 필요한 준비 작업을 할 때 사용한다.</br>
  example) 테스트에서 사용할 임시 파일을 생성, 테스트 메서드에서 사용할 객체를 생성
+ @AfterEach 테스트를 실행한 후에 정리할 것이 있을 때 사용한다.</br>
  example) 테스트에서 사용한 임시 파일을 삭제해야 할 때

+ @BeforeAll: 클래스의 모든 테스트 메서드를 실행하기 전에 한 번 실행된다. (정적메서드)
+ @AfterAll: 클래스의 모든 테스트 메서드를 실행한 뒤에 실행된다.(정적메서드)

### 테스트 메서드 간 실행 순서 의존과 필드 공유하지 않기
각 테스트 메서드는 독립적으로 동작해야 한다. 테스트 메서드 간에 의존이 생기면 이는 테스트 코드의 유지보수를 어렵게 만든다. </br>
테스트 코드 역시 코드이므로 유지보수가 중요하다. 테스트 코드를 작성할 때에는 이 점에 유념해야 한다.

### @DisplayName, @Disabled
+ @DisplayName: 테스트에 표시 이름을 붙일 수 있다.
+ @Disabled: 클래스나 메서드는 테스트 실행 대상에서 제외한다. </br>
아직 테스트 코드가 완성되지 않았거나 잠시 동안 테스트를 실행하지 말아야 할때 사용한다.

### 대역
테스트를 작성하다 보면 외부 요인이 필요한 시점이 있다. 외부 요인이 테스트에 관여하는 주요 예는 아래와 같다.

+ 테스트 대상에서 파일 시스템을 사용
+ 테스트 대상에서 DB로부터 데이터를 조회하거나 데이터를 추가
+ 테스트 대상에서 외부의 HTTP 서버와 통신

테스트 대상에서 의존하는 요인 때문에 테스트가 어려울 때는 대역을 써서 테스트를 진행할 수 있다.</br>
난이도가 높은 액션이 필요할 때 배우를 대신해서 연기하는 스턴트맨처럼 테스트에서는 외부 요인으로 인해 테스트가 어려울 때 외부 요인을 대신하는 대역이 외부 요인을 대신해서 테스트에 참여한다.


대역에는 종류는 스텁, 가짜, 스파이, 모의 객체가 존재하는데 각 대역 종류마다 쓰임새가 다르다

|대역 종류| 설명 |
|------|------|
|스텁(Stud)| 구현을 단순한 것으로 대체한다. 테스트에 맞게 단순히 원하는 동작을 수행한다. StubCardNumberValidator가 스텁 대역에 해당한다.|
|가짜(Fake)| 제품에는 적합하지 않지만, 실제 동작하는 구현을 제공한다. DB 대신에 메모리를 이용해서 구현한 MEMORYAUtoDebitInfoRepository가 가짜 대역에 해당된다.|
|스파이(Spy)| 호출된 내역을 기록한다. 기록한 내용은 테스트 결과를 검증할 때 사용한다. 스텁이기도 하다. |
|모의(Mock)| 기대한 대로 상호작용하는지 행위를 검증한다. 기대한 대로 동작하지 않으면 익셉션을 발생할 수 있다. 모의 객체는 스텁이자 스파이도 된다. |


### Mockito
Mockito는 모의 객체 생성, 검증, 스텁을 지원하는 프레임워크이다.

### AssertJ
### 기본 검증 메서드
+ isEqualTo(값): 값과 같은지 검증한다.
+ isNotEqualTo(값): 값과 같지 않은지 검증한다.
+ isNull(): null인지 검증한다.
+ isNotNull(): null이 아닌지 검증한다.
+ isIn(값 목록): 값 목록에 포함되어 있는지 검증한다.
+ isNotIn(값 목록): 값 목록에 포함되어 있지 않은지 검증한다.

+ isLessThan(값): 값보다 작은지 검증한다.
+ isLessThanOrEqualTo(값): 값보다 작거나 같은지 검증한다.
+ isGreaterThan(값): 값보다 큰지 검증한다.
+ isGreaterThanOrEqualTo(값): 값보다 크거나 같은지 검증한다.
+ isBetween(값1, 값2): 값1과 값2 사이에 포함되는지 검증한다.

+ isTrue(): 값이 True인지 검증한다.
+ isFalse(): 값이 False인지 검증한다.
