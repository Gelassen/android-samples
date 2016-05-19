package com.example.mh_ffmpeg_sample;

/**
 * Created by Gelassen on 18.05.2016.
 */
public class Params {

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
    public static final class FFmpegCommands {


        public static final String VERSION = "ffmpeg -version";
        public static final String COMPRESS = "ffmpeg -y -i %s " +
                "-strict experimental " + "-s 160x120 -r 25 " +
                "-vcodec mpeg4 -b 150k -ab 48000 -ac 2 -ar 22050 " +
                "%s";

        public static final String[] COMPRESS2 = new String[] {
                "ffmpeg", "-y", "-i", "<source>",
                "-strict", "-s", "160x120", "-r", "25",
                "-vcodec", "mpeg4", "-b", "150k", "-ab",
                "48000", "-ac", "2", "-ar", "22050", "<destination>"
        };

        public static String[] getCompressCommand(final String from, final String to) {
            final int fromIdx = 3;
            final int toIdx = 19;
            COMPRESS2[fromIdx] = from;
            COMPRESS2[toIdx] = to;
            return COMPRESS2;
        }

    }
}
