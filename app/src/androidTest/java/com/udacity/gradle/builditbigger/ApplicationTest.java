package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements JokeReceiver {
    public ApplicationTest() {
        super(Application.class);
    }

    CountDownLatch latch = null;
    String jokeString = null;

    public void testJokeTask(){

        latch = new CountDownLatch(1);
        new MainActivity.EndpointsAsyncTask().execute(this);

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
        //fail("Test Failure");
        assertEquals(jokeString == null, false);
        assertEquals(jokeString.isEmpty(), false);

    }

    @Override
    public void onReceiveJoke(String jokeString) {
        this.jokeString = jokeString;
        latch.countDown();
    }
}