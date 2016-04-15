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

package com.snakybo.sengine.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class StringUtils
{
	private StringUtils()
	{
		throw new AssertionError();
	}
	
	/**
	 * Remove empty strings from a collection
	 * @param data The data
	 * @return The data with empty entries removed
	 */
	public static String[] removeEmptyStrings(Collection<String> data)
	{
		List<String> result = new ArrayList<String>();
		
		for(String element : data)
		{
			if(element == null || element.isEmpty())
			{
				continue;
			}
			
			result.add(element);
		}
		
		return result.toArray(new String[result.size()]);
	}
}
