package com.example.mh_ffmpeg_sample.ffmpeg;

import com.example.mh_ffmpeg_sample.Params;

import java.util.ArrayList;

/**
 * Created by Gelassen on 19.05.2016.
 */
public class FfmpegCommandBuilder {

    /**
     * -y       overwrite output files without asking
     * -i       input filename
     * -s       set frame size
     * -r       set frame rate
     * -vcodec  set the codec
     * -b
     * -ab
     * -ac      Set the number of audio channels
     * -ar      Set the audio sampling frequency
     *
     * */

    private boolean allowOverwrightFilesWithoutAsking;
    private String inputFile;
    private boolean allowStrict;
    private String frameSize;
    private String frameRate;
    private String codec;
    private String b;
    private String ab;
    private String audioChannels;
    private String sampleFrequency;
    private String destination;
    private String scale;

    private ArrayList<String> commandList = new ArrayList<>();

    public void allowOverwrightFilesWithoutAsking() {
        this.allowOverwrightFilesWithoutAsking = true;
        commandList.add(Params.OVERWRIGHT_OUTPUT_WITHOUT_ASKING);
    }

    public void setInputFile(final String input) {
        this.inputFile = input;
        commandList.add(Params.INPUT_FILE_NAME);
        commandList.add(input);
    }

    public void setStrict() {
        this.allowStrict = true;
        commandList.add(Params.STRICT);
    }

    public void setFrameSize(final String frameSize) {
        this.frameSize = frameSize;
        commandList.add(Params.FRAME_SIZE);
        commandList.add(frameSize);
    }

    public void setFrameRate(final String frameRate) {
        this.frameRate = frameRate;
        commandList.add(Params.FRAME_RATE);
        commandList.add(frameRate);
    }

    public void setCodec(final String codec) {
        this.codec = codec;
        commandList.add(Params.CODEC);
        commandList.add(codec);
    }

    public void setB(final String b) {
        this.b = b;
        commandList.add(Params.B);
        commandList.add(b);
    }

    public void setAB(final String ab) {
        this.ab = ab;
        commandList.add(Params.AB);
        commandList.add(frameSize);
    }

    public void setAudioChannels(final String audioChannels) {
        this.audioChannels = audioChannels;
        commandList.add(Params.NUMBER_OF_AUDIO_CHANNELS);
        commandList.add(audioChannels);
    }

    public void setSampleFrequency(final String sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
        commandList.add(Params.AUDIO_SAMPLING_FREQUENCY);
        commandList.add(sampleFrequency);
    }

    public void setDestination(final String destination) {
        this.destination = destination;
        commandList.add(destination);
    }

    public void setScaleVideo(final String scaleValue) {
        this.scale = scaleValue;
        commandList.add(Params.SCALE);
        commandList.add(scaleValue);
    }

    public String[] build() {
        String[] commandArray = new String[commandList.size()];
        commandList.toArray(commandArray);
        return commandArray;
    }

    private class Params {
        public static final String OVERWRIGHT_OUTPUT_WITHOUT_ASKING = "-y";
        public static final String INPUT_FILE_NAME = "-i";
        public static final String STRICT = "-strict";
        public static final String FRAME_SIZE = "-s";
        public static final String FRAME_RATE = "-r";
        public static final String CODEC = "-vcodec";
        public static final String B = "-b"; // TODO clarify what is it
        public static final String AB = "-ab"; // TODO clarify what is it
        public static final String NUMBER_OF_AUDIO_CHANNELS = "-ac";
        public static final String AUDIO_SAMPLING_FREQUENCY = "-ar";
        public static final String SCALE = "-vf";//"scale=";
    }
}
