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

package com.snakybo.torch.text;

import com.snakybo.torch.asset.Asset;

public final class TextAsset extends Asset
{
	private String text;
	private byte[] bytes;
	
	/**
	 * Create a new text asset.
	 * @param text The text. 
	 * @param bytes The raw bytes.
	 */
	public TextAsset(String text, byte[] bytes)
	{
		this.text = text;
		this.bytes = bytes;
	}
	
	/**
	 * Get the text of the text asset.
	 * @return The text.
	 */
	public final String getText()
	{
		return text;
	}
	
	/**
	 * Get the raw bytes of the text asset.
	 * @return The raw bytes.
	 */
	public byte[] getBytes()
	{
		return bytes;
	}
}
