/*
 * Copyright MalchroSoft - Aymeric MALCHROWICZ. All right reserved.
 * The source code that contains this comment is an intellectual property
 * of MalchroSoft [Aymeric MALCHROWICZ]. Use is subject to licence terms.
 */
package com.malchrosoft.sound;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Aymeric Malchrowicz
 */
public class WaveSoundPlayer
{
    private AudioFormat format;
    private byte[] samples;
    private Runnable soundRunnable;
    private Thread soundCurrentThread;

    public WaveSoundPlayer(String filename)
    {
        try
        {
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                new File(filename));
            this.format = stream.getFormat();
            this.buildSamples(stream);
            this.soundRunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    WaveSoundPlayer.this.play(new ByteArrayInputStream(
                        WaveSoundPlayer.this.getSamples()));
                }
            };
        } catch (UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void buildSamples(AudioInputStream stream)
    {
        int length = (int) (stream.getFrameLength() * format.getFrameSize());
        byte[] sSamples = new byte[length];
        DataInputStream in = new DataInputStream(stream);
        try
        {
            in.readFully(sSamples);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        this.samples = sSamples;
    }

    /**
     * Plays the sound in a Thread
     * @return true if the sound may be play, false otherwise.
     */
    public boolean play()
    {
        if (this.soundRunnable != null)
        {
            this.soundCurrentThread = new Thread(this.soundRunnable,
                this.getClass().getName() + " : play()");
            this.soundCurrentThread.start();
            return true;
        }
        else return false;
    }

    /**
     * Stops the sound if it is threaded
     * @return true if the sound may be stopped, false otherwise
     */
    public boolean stop()
    {
        if (this.soundCurrentThread != null &&
            this.soundCurrentThread.isAlive())
        {
            this.soundCurrentThread.interrupt();
            return true;
        }
        return false;
    }

    public void play(boolean inThread)
    {
        if (inThread)
        {
            this.play();
        }
        else
        {
            this.play(new ByteArrayInputStream(this.getSamples()));
        }
    }

    private void play(InputStream source)
    {
        // 100 ms buffer for real time change to the sound stream
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];
        SourceDataLine line;
        try
        {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
            line.start();

            int numBytesRead = 0;
            while (numBytesRead != -1)
            {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1)
                {
                    line.write(buffer, 0, numBytesRead);
                }
            }
            line.drain();
            line.close();
        } catch (LineUnavailableException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private byte[] getSamples()
    {
        return samples;
    }

    // TEST
    public static void main(String[] args)
    {
        WaveSoundPlayer player = new WaveSoundPlayer(
            WaveSoundPlayer.class.getResource("sound45.wav").getPath().replaceAll("%20", " "));
        player.play(false);
        player.play();
//        System.exit(0);
    }
}
