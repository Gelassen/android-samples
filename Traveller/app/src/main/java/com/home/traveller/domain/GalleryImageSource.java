package com.home.traveller.domain;

import android.content.Context;

import com.home.traveller.model.Card;
import com.home.traveller.ui.CardController;

/**
 * Created by dmitry.kazakov on 10/25/2015.
 */
public class GalleryImageSource implements ImageSource {

    private Context context;
    private ImageSource.Listener listener;

    private Card card;

    public GalleryImageSource(Context context, Listener listener, Card card) {
        this.context = context;
        this.listener = listener;
        this.card = card;
    }

    @Override
    public void perform() {
        // make the copy of image
        CardController cardController = new CardController();
        cardController.updateImagePath(card.getPath());
//        cardController.createNew(context, listener);
        if (listener != null) listener.onUrlPrepared(card);
    }

}
