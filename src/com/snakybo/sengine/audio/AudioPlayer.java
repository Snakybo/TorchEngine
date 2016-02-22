package com.snakybo.sengine.audio;

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
import static org.lwjgl.openal.AL10.alDopplerFactor;
import static org.lwjgl.openal.AL10.alDopplerVelocity;
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

import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.time.Time;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class AudioPlayer extends Component
{
	private final IntBuffer source;
	
	private Vector3f previousPosition;
	private AudioClip audioClip;
	
	private boolean autoPlay;
	
	public AudioPlayer(AudioClip audioClip)
	{
		this(audioClip, false);
	}
	
	public AudioPlayer(AudioClip audioClip, boolean looping)
	{
		this(audioClip, looping, true);
	}
	
	public AudioPlayer(AudioClip audioClip, boolean looping, boolean autoPlay)
	{
		this.source = BufferUtils.createIntBuffer(1);
		this.audioClip = audioClip;
		this.autoPlay = autoPlay;
		
		alGenSources(source);
		checkALError();
		
		audioClip.bind(source.get(0));		
		setLooping(looping);
	}
	
	@Override
	protected void start()
	{
		if(autoPlay)
		{
			play();
		}
		
		previousPosition = new Vector3f(getTransform().getPosition());
	}
	
	@Override
	protected void postUpdate()
	{
		getTransform().getPosition().z = 1;
		getTransform().getPosition().x += 0.03f;
		
		if(getTransform().getPosition().x > 3)
		{
			getTransform().getPosition().x = -3;
		}
		
		
		Vector3f currentPosition = getTransform().getPosition();
		Vector3f velocity = currentPosition.sub(previousPosition, new Vector3f()).div(Time.getDeltaTime());
		
		// Position
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(3);		
		alSourcefv(source.get(0), AL_POSITION, currentPosition.get(positionBuffer));
		checkALError();
		
		// Velocity
		FloatBuffer velocityBuffer = BufferUtils.createFloatBuffer(3);
		alSourcefv(source.get(0), AL_VELOCITY, velocity.get(velocityBuffer));
		checkALError();
		
		// Doppler
		if(AudioListener.isPresent())
		{
			// Double the frequency
			alDopplerFactor(2);
			checkALError();
			
			// Velocity is the speed of sound at sea level
			alDopplerVelocity(340.29f);
			checkALError();
		}
		
		previousPosition.set(currentPosition);
	}
	
	@Override
	protected final void destroy()
	{
		audioClip.bind(0);
		
		alDeleteSources(source);
		checkALError();
	}
	
	/**
	 * Pause playback
	 */
	public final void pause()
	{
		alSourcePause(source.get(0));
		checkALError();
	}
	
	/**
	 * Resume playback
	 */
	public final void resume()
	{
		alSourcePlay(source.get(0));
		checkALError();
	}
	
	/**
	 * Stop playback
	 */
	public final void stop()
	{
		alSourceStop(source.get(0));
		checkALError();
	}
	
	/**
	 * Start playback
	 */
	public final void play()
	{
		stop();
		
		alSourcePlay(source.get(0));
		checkALError();
	}
	
	/**
	 * Start playback
	 * @param audioClip - The {@link AudioClip} to play
	 */
	public final void play(AudioClip audioClip)
	{
		this.audioClip = audioClip;
		
		audioClip.bind(source.get(0));
		play();
	}
	
	/**
	 * @return Whether or not we're currently playing something
	 */
	public final boolean isPlaying()
	{
		return alGetSourcei(source.get(0), AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	/**
	 * @return Whether or not playback has been paused
	 */
	public final boolean isPaused()
	{
		return alGetSourcei(source.get(0), AL_SOURCE_STATE) == AL_PAUSED;
	}
	
	/**
	 * @return Whether or not we're set to loop
	 */
	public final boolean isLooping()
	{
		return alGetSourcei(source.get(0), AL_LOOPING) == AL_TRUE;
	}
	
	/**
	 * Set the volume
	 * @param volume - The new volume
	 */
	public final void setVolume(float volume)
	{
		alSourcef(source.get(0), AL_GAIN, volume);
		checkALError();
	}
	
	/**
	 * Set the pitch
	 * @param pitch - The new pitch
	 */
	public final void setPitch(float pitch)
	{
		alSourcef(source.get(0), AL_PITCH, pitch);
		checkALError();
	}
	
	/**
	 * Set whether or not we should loop playback
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
	 * @return The current volume
	 */
	public final float getVolume()
	{
		return alGetSourcef(source.get(0), AL_GAIN);
	}
	
	/**
	 * @return The current pitch
	 */
	public final float getPitch()
	{
		return alGetSourcef(source.get(0), AL_PITCH);
	}
}
