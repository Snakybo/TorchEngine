package com.snakybo.sengine.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_FALSE;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_PAUSED;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetSourcef;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcefv;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALUtil.checkALError;

import java.nio.FloatBuffer;
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
		audioClip = null;
		
		alSourceStop(source.get(0));		
		alSourcei(source.get(0), AL_BUFFER, 0);
		
		setLooping(false);
		setSourceVelocity(new Vector3f());
		setSourcePosition(new Vector3f());
		setPitch(1);
		setVolume(1);
	}
	
	/**
	 * Play an {@link AudioClip}
	 * @param audioClip - The {@link AudioClip} to play
	 */
	final void play(AudioClip audioClip)
	{
		stop();
		
		this.audioClip = audioClip;
		
		audioClip.bind(source.get(0));
		
		alSourcePlay(source.get(0));
		checkALError();
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
	 * @return Whether or not the current clip is set to loop
	 */
	public final boolean isLooping()
	{
		return alGetSourcei(source.get(0), AL_LOOPING) == AL_TRUE;
	}
	
	/**
	 * Set the velocity of the source
	 * @param velocity - The new velocity
	 */
	public final void setSourceVelocity(Vector3f velocity)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		
		alSourcefv(source.get(0), AL_VELOCITY, velocity.get(buffer));
		checkALError();
	}

	/**
	 * Set the position of the source in world space
	 * @param position - The new position
	 */
	public final void setSourcePosition(Vector3f position)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
		
		alSourcefv(source.get(0), AL_POSITION, position.get(buffer));
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
	 * Set the pitch of the audio channel
	 * @param pitch - The new pitch
	 */
	public final void setPitch(float pitch)
	{
		alSourcef(source.get(0), AL_PITCH, pitch);
		checkALError();
	}
	
	/**
	 * Set whether or not the current audio clip should loop
	 * @param loop - Whether or not to loop
	 */
	public final void setLooping(boolean loop)
	{
		alSourcei(source.get(0), AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
		checkALError();
	}

	/**
	 * @return The current {@link AudioClip}
	 */
	public final AudioClip getAudioClip()
	{
		return audioClip;
	}
	
	/**
	 * @return The current volume of the channel
	 */
	public final float getVolume()
	{
		return alGetSourcef(source.get(0), AL_GAIN);
	}
	
	/**
	 * @return The pitch of the channel
	 */
	public final float getPitch()
	{
		return alGetSourcef(source.get(0), AL_PITCH);
	}
}
