package de.inovex.academy.android;

import java.util.List;

public class TestBase {

	protected static void printList(@SuppressWarnings("rawtypes") List l) {
		for (Object o : l) {
			System.out.println(o);
		}
	}
}
