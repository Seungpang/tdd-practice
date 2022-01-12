package junit5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("integration")
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
