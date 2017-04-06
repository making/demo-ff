package com.example.feature;

public interface Feature<T extends Feature> {
	boolean isEnabled();

	default void ifEnabled(Runnable runnable) {
		if (isEnabled()) {
			runnable.run();
		}
	}
}
