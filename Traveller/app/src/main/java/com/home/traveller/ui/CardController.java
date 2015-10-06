package com.home.traveller.ui;

import android.content.ContentResolver;
import android.content.Context;

import com.home.traveller.model.Card;
import com.home.traveller.storage.Contract;

/**
 * Created by dmitry.kazakov on 10/5/2015.
 */
public class CardController {

    private Card card;

    public CardController() {
        card = new Card();
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void updateComment(final String comment) {
        card.setDesc(comment);
    }

    public void updateImagePath(final String path) {
        card.setPath(path);
    }

    public void updateFavouriteState(boolean selected) {
        card.setTag(selected ? Card.Tag.SELECTED.toString() : Card.Tag.UNSELECTED.toString());
    }

    public void updateModel(Context context) {
        ContentResolver cr = context.getContentResolver();
        cr.insert(Contract.contentUri(Contract.CardsTable.class), card.asContentValues());
    }
}
