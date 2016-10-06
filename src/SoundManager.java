import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager 
{	
	Clip bass;
	Clip bass2;
	Clip clap;
	Clip closed;
	Clip crash;
	Clip perc1;
	Clip perc2;
	Clip perc3;
	Clip shake;
	Clip snare;
	
	public SoundManager()
	{
		try
		{
			bass = open("bass.wav");
			bass2 = open("bass_2.wav");
			clap = open("clap.wav");
			closed = open("closed.wav");
			crash = open("crash.wav");
			perc1 = open("perc_1.wav");
			perc2 = open("perc_2.wav");
			perc3 = open("perc_3.wav");
			shake = open("shake.wav");
			snare = open("snare.wav");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void play(int id)
	{
		if (id == 1)
		{
			playClip(bass);
		}
		if (id == 2)
		{
			playClip(bass2);
		}
		if (id == 3)
		{
			playClip(clap);
		}
		if (id == 4)
		{
			playClip(closed);
		}
		if (id == 5)
		{
			playClip(crash);
		}
		if (id == 6)
		{
			playClip(perc1);
		}
		if (id == 7)
		{
			playClip(perc2);
		}
		if (id == 8)
		{
			playClip(perc3);
		}
		if (id == 9)
		{
			playClip(shake);
		}
		if (id == 0)
		{
			playClip(snare);
		}
	}
	
	private Clip open(String path)
	{
		Clip clip = null;
	    try 
	    {
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
	        DataLine.Info info = new DataLine.Info(Clip.class, inputStream.getFormat());
	        clip = (Clip)AudioSystem.getLine(info);
	        clip.open(inputStream);
	    } 
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    }
	    return clip;
	}
	
	private void playClip(Clip clip)
	{
		clip.stop();
		clip.setFramePosition(0);
		clip.loop(0);
        clip.start();
	}
}