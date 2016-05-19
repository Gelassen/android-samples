package com.example.mh_ffmpeg_sample.ffmpeg;

/**
 * Created by Gelassen on 19.05.2016.
 */
public class FfmpegCommands {

    private FfmpegCommandBuilder ffmpegCommandBuilder;

    public FfmpegCommands() {
        this.ffmpegCommandBuilder = new FfmpegCommandBuilder();
    }
    public static final String[] COMPRESS2 = new String[] {
            "ffmpeg", "-y", "-i", "<source>",
            "-strict", "-s", "160x120", "-r", "25",
            "-vcodec", "mpeg4", "-b", "150k", "-ab",
            "48000", "-ac", "2", "-ar", "22050", "<destination>"
    };

    public String[] getCompressCommand(final String from, final String to) {
        ffmpegCommandBuilder.allowOverwrightFilesWithoutAsking();
        ffmpegCommandBuilder.setInputFile(from);
        ffmpegCommandBuilder.setStrict();
        ffmpegCommandBuilder.setScaleVideo("320x640");
//        ffmpegCommandBuilder.setFrameSize("160x120");
        ffmpegCommandBuilder.setFrameRate("25");
        ffmpegCommandBuilder.setCodec("mpeg4");
        ffmpegCommandBuilder.setB("150k");
        ffmpegCommandBuilder.setAB("48000");
        ffmpegCommandBuilder.setAudioChannels("2");
        ffmpegCommandBuilder.setSampleFrequency("22050");
        ffmpegCommandBuilder.setDestination(to);
        return ffmpegCommandBuilder.build();
    }

    public String[] getExtractAudioFromVideoCommand(final String from, final String to) {
        ffmpegCommandBuilder.allowOverwrightFilesWithoutAsking();
        ffmpegCommandBuilder.setInputFile(from);
        ffmpegCommandBuilder.setDestination(to);
        return ffmpegCommandBuilder.build();
    }
}
