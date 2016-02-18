package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_PAUSED;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetSourcef;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioChannel
{
	private final IntBuffer source;
	
	AudioClip audioClip;
	
	public AudioChannel()
	{
		source = BufferUtils.createIntBuffer(1);
		
		alGenSources(source);
		checkALError();
	}
	
	/**
	 * Pause the audio channel
	 */
	public final void pause()
	{
		alSourcePause(source.get(0));
	}
	
	/**
	 * Resume the audio channel
	 */
	public final void resume()
	{
		alSourcePlay(source.get(0));
	}
	
	/**
	 * Stop the audio channel
	 */
	public final void stop()
	{
		alSourceStop(source.get(0));
		
		alSourcei(source.get(0), AL_BUFFER, 0);
		
		setLoop(false);
	}
	
	/**
	 * Play a 2D audio clip
	 * @param audioClip - The audio clip to play
	 */
	final void play(AudioClip audioClip)
	{
		this.audioClip = audioClip;
		
		// Reset the audio source
		stop();
		
		alSourcei(source.get(0), AL_BUFFER, audioClip.getBufferId());
		checkALError();
		
		alSourcePlay(source.get(0));
		checkALError();
	}
	
	/**
	 * Play a 3D audio clip
	 * @param audioClip - The audio clip to play
	 * @param point - the point in world-space to play the clip at
	 */
	// TODO: Play audio in 3D space
	final void playAt(AudioClip audioClip, Vector3f point)
	{
		//this.audioClip = audioClip;
		
		// Reset the audio source
		//stop();
		
		play(audioClip);
	}
	
	/**
	 * Destroy the audio channel
	 */
	final void destroy()
	{
		alDeleteSources(source);
		checkALError();
	}
	
	/**
	 * @return Whether or not the audio channel is currently playing something
	 */
	public final boolean isPlaying()
	{
		return alGetSourcei(source.get(0), AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	/**
	 * @return Whether or not the audio channel has been paused
	 */
	public final boolean isPaused()
	{
		return alGetSourcei(source.get(0), AL_SOURCE_STATE) == AL_PAUSED;
	}
	
	/**
	 * @return Whether or not the audio channel is set to loop a clip
	 */
	public final boolean isLooping()
	{
		return alGetSourcei(source.get(0), AL_LOOPING) == AL_TRUE;
	}
	
	/**
	 * Set whether or not the current audio clip should loop
	 * @param loop - Whether or not to loop
	 */
	public final void setLoop(boolean loop)
	{
		alSourcei(source.get(0), AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
		checkALError();
	}
	
	/**
	 * Set the volume of the audio channel
	 * @param volume - The new volume
	 */
	public final void setVolume(float volume)
	{
		alSourcef(source.get(0), AL_GAIN, volume);
		checkALError();
	}
	
	/**
	 * @return The current volume of the channel
	 */
	public final float getVolume()
	{
		return alGetSourcef(source.get(0), AL_GAIN);
	}
}
