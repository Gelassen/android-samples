package com.coderbunker.javarxsample;


import com.coderbunker.javarxsample.dto.ICommon;
import com.coderbunker.javarxsample.model.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.observers.TestObserver;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

public class JavaRxTest {

    private TestSubscriber testSubscriber;
    private TestObserver testObserver;

    @Before
    public void setUp() throws Exception {
        testSubscriber = new TestSubscriber();
        testObserver = new TestObserver();
    }

    @Test
    public void testSubscription() throws Exception {
        // Observable<List<CityItem>> data = (Observable<List<CityItem>>)
        Model model = new Model();
        model.getModel()
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(testObserver);


        List<ICommon> list = (List<ICommon>) testObserver.getOnNextEvents().get(0);

        assertEquals(5, list.size());
        assertEquals(0, testObserver.getOnErrorEvents().size());
        assertEquals(1, testObserver.getOnCompletedEvents().size());
    }
}
