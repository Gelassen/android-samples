package com.home.traveller.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.home.traveller.App;
import com.home.traveller.domain.ImageSource;
import com.home.traveller.model.Card;
import com.home.traveller.storage.Contract;
import com.home.traveller.utils.FileManager;

import java.io.File;

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

    public void createNew(final Context context) {
        createNew(context, null);
    }

    public void createNew(final Context context, final ImageSource.Listener listener) {
        // TODO create new card
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final FileManager fileManager = new FileManager(context);
                final File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                if (directory != null
                        && fileManager.isExternalStorageWritable()
                        && fileManager.isEnoughMemory(card)) {

                    File file;
                    if (card.directPathAvailable()) {
                        file = fileManager.copyFile(
                                card.getPath(),
                                fileManager.addHiddenFolderToPath(directory)
                        );
                    } else {
                        file = fileManager.copyFile(
                                card.getFileDescriptor(),
                                fileManager.addHiddenFolderToPath(directory)
                        );
                    }

                    // update runtime model but not save it persistent model
                    Log.d(App.TAG, "Absolute path: " + file.getAbsolutePath());
                    card.setPath(file.getAbsolutePath());
                    if (listener != null) listener.onUrlPrepared(card);
                }
            }
        });
    }

    public void updateModel(Context context) {
        Log.d(App.TAG, "Card location: " + card.getPath());
        ContentResolver cr = context.getContentResolver();
        cr.insert(Contract.contentUri(Contract.CardsTable.class), card.asContentValues());
    }
}
