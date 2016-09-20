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

package com.snakybo.torch.event;

import com.snakybo.torch.object.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * All available events are in this class, from here you can subscribe, or un-subscribe from an event.
 * </p>
 *
 * <p>
 * To subscribe to an event, you will need either an anonymous class, or a lambda.
 * You will also have to store the listener in a variable, if you don't store it in a variable,
 * you will be unable to remove the listener.
 * </p>
 *
 * <p>
 * <strong>Lambda example:</strong>
 * </p>
 *
 * <pre>
 * ICharPressedEvent charPressedEvent;
 *
 *{@code charPressedEvent = (c) -> onCharPressed(c)};
 *
 * private void invoke(char c)
 * {
 *     Logger.log("Character " + c + " has been pressed");
 * }
 * </pre>
 *
 * <p>
 * <strong>Anonymous class example:</strong>
 * </p>
 *
 * <pre>
 * ICharPressedEvent charPressedEvent;
 *
 * charPressedEvent = new ICharPressedEvent()
 * {
 *  {@code @Override}
 *   public void invoke(char c)
 *   {
 *       Logger.log("Character " + c + " has been pressed");
 *   }
 * };
 * </pre>
 *
 * <p>
 * <strong>Subscribing and un-subscribing:</strong>
 * </p>
 *
 * <pre>
 * Events.onCharPressed.addListener(charPressedEvent);
 * Events.onCharPressed.removeListener(charPressedEvent);
 * </pre>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Events
{
	/**
	 * <p>
	 * Event handler.
	 * </p>
	 *
	 * <p>
	 * Keeps track of registered events.
	 * </p>
	 *
	 * @param <T> The event type.
	 */
	public static final class Event<T>
	{
		private Set<T> listeners;
		
		private Event()
		{
			listeners = new HashSet<>();
		}
		
		/**
		 * <p>
		 * Add a listener to the event, the {@code listener} will receive events
		 * for as long as it remains subscribed.
		 * </p>
		 *
		 * <p>
		 * <strong>Note:</strong> If the {@code listener} is a method in a {@link Component},
		 * it will receive events even after the {@code Component} has been destroyed.
		 * <strong>Make sure to un-subscribe from the event.</strong>
		 * </p>
		 *
		 * @param listener The listener method.
		 */
		public final void addListener(T listener)
		{
			listeners.add(listener);
		}
		
		/**
		 * <p>
		 * Remove a listener from the event.
		 * </p>
		 *
		 * @param listener The listener method.
		 */
		public final void removeListener(T listener)
		{
			listeners.remove(listener);
		}
		
		/**
		 * <p>
		 * Remove all listeners from the event.
		 * </p>
		 */
		public final void removeAllListeners()
		{
			listeners.clear();
		}
		
		public final Iterable<T> getListeners()
		{
			return listeners;
		}
	}
	
	/**
	 * <p>
	 * The character pressed event, this event will be fired when any character has been pressed while the
	 * window has focus.
	 * </p>
	 */
	public static final Event<ICharPressedEvent> onCharPressed = new Event<>();
	
	/**
	 * <p>
	 * The cursor enter/exit event, this event will be fired when the cursor enters or exits the
	 * window.
	 * </p>
	 */
	public static final Event<ICursorEnterEvent> onCursorEnter = new Event<>();
	
	/**
	 * <p>
	 * The window focus event, this event will be fired when the window either gains, or loses focus.
	 * </p>
	 */
	public static final Event<IWindowFocusEvent> onWindowFocus = new Event<>();
	
	/**
	 * <p>
	 * The window iconify/restore event, this event will be fired when the window has been iconified, or restored.
	 * </p>
	 */
	public static final Event<IWindowIconifyEvent> onWindowIconify = new Event<>();
	
	/**
	 * <p>
	 * The window resized event, this event will be fired when the window has been resized.
	 * </p>
	 */
	public static final Event<IWindowResizeEvent> onWindowResize = new Event<>();
	
	private Events()
	{
		throw new AssertionError();
	}
}
