package com.example.interview.convertors;

import android.util.Pair;

/**
 * The intent of this class is encapsulate transformation of actual media params
 * to scaled media params to achieve 'center crop' effect
 *
 * Created by John on 10/5/2016.
 */
public class VideoDimensToScaledDimenConverter implements IConverter<Pair<Integer, Integer>, Pair<Float, Float>> {

    private Pair<Integer, Integer> input;
    private Pair<Integer, Integer> display;

    public VideoDimensToScaledDimenConverter(int displayWidth, int displayHeight) {
        display = new Pair<>(displayWidth, displayHeight);
    }

    public Pair<Integer, Integer> getDisplayDimensions() {
        return display;
    }

    /**
     * @param input First param is width, second - height
     * */
    @Override
    public void init(Pair<Integer, Integer> input) {
        this.input = input;
    }

    /**
     * @return result Pair where first param is scaleX and second param is scaleY
     * */
    @Override
    public Pair<Float, Float> convert() {
        if (input == null) return new Pair<>(1f,1f);

        float scaleX = 1f;
        float scaleY = 1f;
        if (input.first > display.first && input.second > display.second) {
            scaleX = (float) (input.first / display.first);
            scaleY = (float) (input.second / display.second);
        } else if (input.first < display.first && input.second < input.second) {
            scaleX = (float) (display.first / input.first);
            scaleY = (float) (display.second / input.second);
        } else if (input.first < display.first) {
            scaleY = (float) (display.first / input.first) / (float) (display.second / input.second);
        } else if (input.second < display.second) {
            scaleX = (float) (display.second / input.second) / (float) (display.first / input.first);
        }
        return new Pair<>(scaleX, scaleY);
    }
}
