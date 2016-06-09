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

package com.snakybo.torch.module;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Module
{
	private static Set<IModule<?>> modules = new HashSet<IModule<?>>();
	
	private Module()
	{
		throw new AssertionError();
	}
	
	static void registerModule(IModule<?> module)
	{
		if(getModule(module.getModuleType()) != null)
		{
			throw new RuntimeException("Unable to add module of type: " + module.getModuleType());
		}
		
		modules.add(module);
	}
	
	public static <T> T getModule(Class<T> type)
	{
		for(IModule<?> module : modules)
		{
			if(module.getModuleType().equals(type))
			{
				return type.cast(module);
			}
		}
		
		return null;
	}
}
