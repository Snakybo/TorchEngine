package com.snakybo.sengine.audio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.Vector3f;

import com.snakybo.sengine.debug.Logger;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioManager
{
	static List<AudioChannel> audioChannels = new ArrayList<AudioChannel>();
	
	static
	{
		// TODO: Allow the user to save the amount of audio channels
		setNumAudioChannels(32);
	}
	
	private AudioManager()
	{
		throw new AssertionError();
	}
	
	/**
	 * Play a 2D audio clip
	 * @param audioClip - The audio clip to play
	 * @return The audio channel the clip is being played on, or null if none was available
	 */
	public static AudioChannel play(AudioClip audioClip)
	{
		AudioChannel audioChannel = getAvailableAudioChannel();
		
		if(audioChannel != null)
		{
			audioChannel.play(audioClip);
			return audioChannel;
		}
		
		Logger.logWarning("No free audio channel available", "AudioManager");
		return null;
	}
	
	/**
	 * Play a 3D audio clip
	 * @param audioClip - The audio clip to play
	 * @param point - The point in world-space to play the clip at
	 * @return The audio channel the clip is being played on, or null if none was available
	 */
	public static AudioChannel playAt(AudioClip audioClip, Vector3f point)
	{
		AudioChannel audioChannel = getAvailableAudioChannel();
		
		if(audioChannel != null)
		{
			audioChannel.playAt(audioClip, point);
			return audioChannel;
		}
		
		Logger.logWarning("No free audio channel available", "AudioManager");
		return null;
	}
	
	/**
	 * Stop all audio channels
	 */
	public static void stopAll()
	{
		for(AudioChannel audioChannel : audioChannels)
		{
			audioChannel.stop();
		}
	}
	
	/**
	 * Set the number of audio channels,
	 * a higher value increases the amount of sounds that can be played simultaneously,
	 * but has a higher cost
	 * @param amount - The amount of audio channels to use
	 */
	public static void setNumAudioChannels(int amount)
	{
		if(amount == audioChannels.size())
		{
			return;
		}
		
		Set<AudioChannel> channelsToRestart = new HashSet<AudioChannel>();
		
		// Stop all audio channels
		for(AudioChannel audioChannel : audioChannels)
		{
			if(audioChannel.isPlaying())
			{
				channelsToRestart.add(audioChannel);
				audioChannel.stop();
			}
		}
		
		// Add audio channels if needed
		if(amount > audioChannels.size())
		{
			int count = amount - audioChannels.size();
			
			for(int i = 0; i < count; i++)
			{
				audioChannels.add(new AudioChannel());
			}
		}
		// Remove audio channels if needed
		else if(amount < audioChannels.size())
		{
			int count = audioChannels.size() - amount;
			
			for(int i = audioChannels.size(); i > count; i--)
			{
				audioChannels.remove(i);
			}
		}
		
		// Restart all audio channels
		for(AudioChannel audioChannel : channelsToRestart)
		{
			if(audioChannels.contains(audioChannel))
			{
				audioChannel.play(audioChannel.audioClip);
			}
		}
	}
	
	/**
	 * @return An available audio channel, or null of none is available
	 */
	private static AudioChannel getAvailableAudioChannel()
	{
		for(AudioChannel audioChannel : audioChannels)
		{
			if(!audioChannel.isPlaying())
			{
				return audioChannel;
			}
		}
		
		return null;
	}
}
