package com.udacity.joke;

import org.junit.Test;

public class JokerTest {
    @Test
    public void getJoke() {
        Joker joker = new Joker();
        assert !joker.getJoke().isEmpty();
    }
}
