package com.home.traveller.ui;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.home.traveller.R;
import com.home.traveller.model.Card;

/**
 * Created by dmitry.kazakov on 10/4/2015.
 */
public class DetailsViewBinder {
    private ImageView imageView;
    private ImageView favView;
    private EditText description;

    private CardController cardController;

    public DetailsViewBinder(View view) {
        imageView = (ImageView) view.findViewById(R.id.details_image);
        favView = (ImageView) view.findViewById(R.id.details_fav);
        description = (EditText) view.findViewById(R.id.details_description_text);
        favView.setOnClickListener(favListener);

        cardController = new CardController();
    }

    public void updateUI(Card card) {
        cardController.setCard(card);

        description.setText(card.getDesc());
        updateFavorite(!TextUtils.isEmpty(card.getTag()) && !Card.Tag.valueOf(card.getTag()).equals(Card.Tag.UNSELECTED));
        loadImage(Uri.parse(card.getPath()));
    }

    public void updatePath(String path) {
        cardController.updateImagePath(path);
    }

    public void loadImage(Uri uri) {
        Glide.with(imageView.getContext())
                .load(uri)
                .centerCrop()
                .into(imageView);
    }

    public void updateModel() {
        cardController.updateComment(description.getText().toString());
        cardController.updateModel(imageView.getContext());
    }

    public void updateFavorite(boolean selected) {
        Drawable drawable = favView.getDrawable();
        final int select = 1;
        final int unselect = 0;
        drawable.setLevel(selected ? select : unselect);
    }

    private final View.OnClickListener favListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable drawable = favView.getDrawable();
            final int select = 1;
            boolean currentState = drawable.getLevel() == select;

            cardController.updateComment(description.getText().toString());
            cardController.updateFavouriteState(!currentState);
            cardController.updateModel(favView.getContext());
        }
    };
}
