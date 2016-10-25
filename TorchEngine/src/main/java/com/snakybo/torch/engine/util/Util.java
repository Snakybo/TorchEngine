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

package com.snakybo.torch.engine.util;

import com.snakybo.torch.engine.debug.Debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Util
{
	private Util()
	{
		throw new AssertionError();
	}
	
	public static StackTraceElement[] getStackTrace()
	{
		return getStackTrace(false);
	}
	
	public static StackTraceElement[] getStackTrace(boolean includeInternal)
	{
		List<StackTraceElement> stackTrace = new ArrayList<>();
		Collections.addAll(stackTrace, Thread.currentThread().getStackTrace());
		
		// Remove internal elements
		if(!includeInternal)
		{
			Iterator<StackTraceElement> it = stackTrace.iterator();
			
			while(it.hasNext())
			{
				if(isInternalCall(it.next()))
				{
					it.remove();
				}
			}
		}
		
		// Limit depth to 10
		stackTrace = stackTrace.subList(1, Math.min(stackTrace.size(), 11));
		
		return stackTrace.toArray(new StackTraceElement[stackTrace.size()]);
	}
	
	public static boolean isInternalCall(StackTraceElement stackTraceElement)
	{
		// Internal Java elements
		if( stackTraceElement.getClassName().startsWith("sun.") ||
			stackTraceElement.getClassName().startsWith("java."))
		{
			return true;
		}
		
		// IDE entrance points
		if(stackTraceElement.getClassName().startsWith("com.intellij."))
		{
			return true;
		}
		
		// TorchEngine elements
		if(stackTraceElement.getClassName().startsWith("com.snakybo.torch."))
		{
			return true;
		}
		
		return false;
	}
}
