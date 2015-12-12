package com.home.traveller.domain;


import com.home.traveller.model.Card;

/**
 * Created by dmitry.kazakov on 10/25/2015.
 */
public interface ImageSource {
    void perform();

    interface Listener {
        void onUrlPrepared(final Card card);
    }

}
