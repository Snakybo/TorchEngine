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

package com.snakybo.torch;

/**
 * <p>
 * Container class for various engine data, such as the current version number.
 * </p>
 *
 * @author Kevin Krol
 * @since 1.0
 */
public final class EngineInfo
{
	/**
	 * The major version of the engine.
	 */
	public static final int VERSION_MAJOR = 1;
	
	/**
	 * The minor version of the engine.
	 */
	public static final int VERSION_MINOR = 0;
	
	/**
	 * The patch version of the engine.
	 */
	public static final int VERSION_PATCH = 0;
	
	/**
	 * The version of the engine represented as a String, in the format:
	 * {@link #VERSION_MAJOR}.{@link #VERSION_MINOR}.{@link #VERSION_PATCH}.
	 */
	public static final String VERSION_STRING = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH;
}
