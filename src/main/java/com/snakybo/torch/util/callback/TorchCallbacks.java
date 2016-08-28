// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.torch.util.callback;

import com.snakybo.torch.object.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * All available callbacks are in this class, from here you can subscribe, or un-subscribe from a callback.
 * </p>
 *
 * <p>
 * To subscribe to a callback, you will need either an anonymous class, or a lambda.
 * You will also have to store the callback in a variable, if you don't store it in a variable,
 * you will be unable to remove the callback.
 * </p>
 *
 * <p>
 * <strong>Lambda example:</strong>
 *
 * <pre>
 * ICharPressedCallback charPressedCallback;
 *
 * charPressedCallback = (c) -> onCharPressed(c);
 *
 * private void onCharPressed(char c)
 * {
 *     Logger.log("Character " + c + " has been pressed");
 * }
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Anonymous class example:</strong>
 *
 * <pre>
 * ICharPressedCallback charPressedCallback;
 *
 * charPressedCallback = new ICharPressedCallback()
 * {
 *  {@code @Override}
 *   public void onCharPressed(char c)
 *   {
 *       Logger.log("Character " + c + " has been pressed");
 *   }
 * };
 * </pre>
 * </p>
 *
 * <p>
 * <strong>Subscribing and un-subscribing:</strong>
 *
 * <pre>
 * TorchCallbacks.onCharPressed.addListener(charPressedCallback);
 * TorchCallbacks.onCharPressed.removeListener(charPressedCallback);
 * </pre>
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class TorchCallbacks
{
	public static final class Callback<T>
	{
		private Set<T> callbacks;
		
		private Callback()
		{
			callbacks = new HashSet<>();
		}
		
		/**
		 * <p>
		 * Add a listener to the callback, the {@code callback} will receive events
		 * for as long as it remains subscribed.
		 * </p>
		 *
		 * <p>
		 * <strong>Note:</strong> If the {@code callback} is a method in a {@link Component},
		 * it will receive callbacks even after the {@code Component} has been destroyed.
		 * Make sure to un-subscribe from the callback.
		 * </p>
		 *
		 * @param callback The callback method.
		 */
		public final void addListener(T callback)
		{
			callbacks.add(callback);
		}
		
		/**
		 * <p>
		 * Remove a listener from the callback.
		 * </p>
		 *
		 * @param callback The callback method.
		 */
		public final void removeListener(T callback)
		{
			callbacks.remove(callback);
		}
		
		/**
		 * <p>
		 * Remove all listeners from the callback.
		 * </p>
		 */
		public final void removeAllListeners()
		{
			callbacks.clear();
		}
		
		public final Iterable<T> getCallbacks()
		{
			return callbacks;
		}
	}
	
	/**
	 * <p>
	 * The character pressed callback, this event will be fired when any character has been pressed while the
	 * window has focus.
	 * </p>
	 */
	public static final Callback<ICharPressedCallback> onCharPressed = new Callback<>();
	
	/**
	 * <p>
	 * The cursor enter/exit callback, this event will be fired when the cursor enters or exits the
	 * window.
	 * </p>
	 */
	public static final Callback<ICursorEnterCallback> onCursorEnter = new Callback<>();
	
	/**
	 * <p>
	 * The window focus callback, this event will be fired when the window either gains, or loses focus.
	 * </p>
	 */
	public static final Callback<IWindowFocusCallback> onWindowFocus = new Callback<>();
	
	/**
	 * <p>
	 * The window iconify/restore callback, this event will be fired when the window has been iconified, or restored.
	 * </p>
	 */
	public static final Callback<IWindowIconifyCallback> onWindowIconify = new Callback<>();
	
	private TorchCallbacks()
	{
		throw new AssertionError();
	}
}
