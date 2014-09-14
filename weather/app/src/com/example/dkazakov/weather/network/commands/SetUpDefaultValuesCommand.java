package com.example.dkazakov.weather.network.commands;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;

import com.example.dkazakov.weather.network.Command;
import com.example.dkazakov.weather.storage.Contract;
import com.example.dkazakov.weather.utils.PredefinedValues;


public class SetUpDefaultValuesCommand extends Command {


    @Override
    public void execute(Context context, ResultReceiver receiver) {
        super.execute(context, receiver);
        // TODO insert 4 cities into the database and upload weather for them
        ContentResolver cr = context.getContentResolver();
        Uri cityUri= Contract.contentUri(Contract.Cities.class);
        cr.insert(cityUri, PredefinedValues.getDublinAsValues());
        cr.insert(cityUri, PredefinedValues.getLondonAsValues());
        cr.insert(cityUri, PredefinedValues.getNewYorkAsValues());
        cr.insert(cityUri, PredefinedValues.getBarcelonaAsValues());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // no op
    }

    public final static Parcelable.Creator<SetUpDefaultValuesCommand> CREATOR = new Parcelable.Creator<SetUpDefaultValuesCommand>() {

        @Override
        public SetUpDefaultValuesCommand[] newArray(int size) {
            return new SetUpDefaultValuesCommand[size];
        }

        @Override
        public SetUpDefaultValuesCommand createFromParcel(Parcel source) {
            return new SetUpDefaultValuesCommand();
        }
    };
}
