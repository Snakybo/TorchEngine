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

package com.snakybo.torch.input;

import com.snakybo.torch.input.mouse.MouseButton;

/**
 * @author Kevin
 * @since 1.0
 */
public interface IInput<T>
{
	/**
	 * Check whether or not <code>id</code> is currently being pressed.
	 * @param id The id to check.
	 * @return True if <code>id</code> is currently being pressed.
	 * @see Key
	 * @see MouseButton
	 */
	boolean isDown(T id);

	/**
	 * Check whether or not <code>id</code> is currently not being pressed.
	 * @param id The id to check.
	 * @return True if <code>id</code> is currently not being pressed.
	 * @see Key
	 * @see MouseButton
	 */
	boolean isUp(T id);
	
	/**
	 * Check whether or not <code>id</code> is being pressed.
	 * @param id The id to check.
	 * @return True the first frame the <code>id</code> is being pressed.
	 * @see Key
	 * @see MouseButton
	 */
	boolean onDown(T id);
	
	/**
	 * Check whether or not <code>id</code> is not being pressed.
	 * @param id The id to check.
	 * @return True the first frame the <code>id</code> has been released.
	 * @see Key
	 * @see MouseButton
	 */
	boolean onUp(T id);
}
