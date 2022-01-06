### JUnit5 모듈
+ JUnit Platform: 테스트를 실행해주는 런쳐 제공, TestEngine API제공
+ Jupiter: TestEngine API 구현체로 JUnit 5를 제공
+ Vintage: JUnit 4와 3을 지원하는 TestEngine 구현체

### 주요 단언 메서드
+ Assertions 클래스가 제공하는 주요 단언 메서드

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

+ Assetions가 제공하는 익셉션 발생 유무 검사 메서드

|메서드|설명|
|---|---|
|assertThrows(Class<T> expectedType, Executable executable)|executable을 실행한 결과로 지정한 타입의 익셉션이 발생하는지 검사한다.|
|assertDoesNotThrow(Executable executable)|executable을 실행한 결과로 익셉션이 발생하지 않는지 검사한다.|

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
