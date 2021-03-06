package com.home.traveller.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.traveller.R;
import com.home.traveller.domain.CameraImageSource;
import com.home.traveller.domain.GalleryImageSource;
import com.home.traveller.domain.ImageSource;
import com.home.traveller.domain.StorageImageSource;
import com.home.traveller.model.Card;

/**
 * Created by dmitry.kazakov on 10/3/2015.
 */
public class DetailsActivity extends AppCompatActivity {

    private static final String EXTRA_DETAILS = ".EXTRA_DETAILS";
    private static final String EXTRA_FILE_PATH = ".EXTRA_FILE_PATH";

    private static final int DATA_DETAILS = 0;
    private static final int DATA_GALLERY = 1;
    private static final int DATA_CAMERA = 2;

    public enum ImageSourceConst {
        DETAILS, CAMERA, CALLERY
    }

    public static void startActivity(Context context, Uri data, ImageSourceConst details) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, DetailsActivity.class));
        intent.putExtra(EXTRA_DETAILS, details.name());
        intent.putExtra(EXTRA_FILE_PATH, data);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Intent data, ImageSourceConst details) {
        Intent intent = data == null ? new Intent() : new Intent(data);
        intent.setComponent(new ComponentName(context, DetailsActivity.class));
        intent.putExtra(EXTRA_DETAILS, details.name());
        context.startActivity(intent);
    }

    private DetailsViewBinder detailsViewBinder;
    private CardController cardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        cardController = new CardController();
        detailsViewBinder = new DetailsViewBinder(
                findViewById(R.id.container),
                cardController
        );

        Uri uri = Uri.parse("/storage/emulated/0/Android/data/com.home.traveller/files/Pictures/.nomedia/Traveller_1450614001805-1462859326.png");
        detailsViewBinder.loadImage(uri);

//        Launcher launcher = new Launcher();
//        launcher.process(getIntent());
    }

    public void onFabClick(View view) {
        // save it
        detailsViewBinder.updateModel();
        finish();
    }

    private class Launcher implements ImageSource.Listener {

        private ImageSource source;

        public void process(Intent intent) {
            // prepare url
            Bundle bundle = intent.getExtras();
            Uri data = bundle.getParcelable(EXTRA_FILE_PATH);
//            String data = intent.getExtras().getString(EXTRA_FILE_PATH);//intent.getData();
            Card card = new Card();
            card.setPath(data == null ? "" : data.toString());
            // define source
            String imageSourcess =  intent.getStringExtra(EXTRA_DETAILS);
            ImageSourceConst imageSource = ImageSourceConst.valueOf(imageSourcess);
            if (imageSource.equals(ImageSourceConst.DETAILS)) {
                source = new StorageImageSource(DetailsActivity.this, this, card);
            } else if (imageSource.equals(ImageSourceConst.CALLERY)) {
                Uri fileDescriptor = intent.getParcelableExtra(EXTRA_FILE_PATH);
                card.setPath(fileDescriptor.getPath());
                source = new GalleryImageSource(DetailsActivity.this, this, card);
            } else if (imageSource.equals(ImageSourceConst.CAMERA)) {
                source = new CameraImageSource(DetailsActivity.this, this);
            } else {
                throw new IllegalArgumentException("Unsupported image source type");
            }

            source.perform();
        }

        @Override
        public void onUrlPrepared(Card card) {
            detailsViewBinder.updateUI(card);
        }
    }
}
