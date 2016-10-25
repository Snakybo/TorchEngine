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

package com.snakybo.torch.engine.core;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ActorRegister
{
	private Set<Actor> newRegisters;
	private Set<Actor> registered;
	private Set<Actor> destroying;
	
	public ActorRegister()
	{
		newRegisters = new HashSet<>();
		registered = new HashSet<>();
		destroying = new HashSet<>();
	}
	
	public final void process()
	{
		registered.removeAll(destroying);
		
		newRegisters.clear();
		destroying.clear();
	}
	
	public final boolean add(Actor actor)
	{
		return newRegisters.add(actor) && registered.add(actor);
	}
	
	public final boolean remove(Actor actor)
	{
		if(registered.contains(actor))
		{
			return destroying.add(actor);
		}
		
		return false;
	}
	
	public final boolean isBeingDestroyed(Actor actor)
	{
		return destroying.contains(actor);
	}
	
	public final Iterable<Actor> getAll()
	{
		return registered;
	}
	
	public final Iterable<Actor> getAllToDestroy()
	{
		return destroying;
	}
	
	public final Iterable<Actor> getNewRegisters()
	{
		return newRegisters;
	}
	
	public final int getSize()
	{
		return registered.size();
	}
}
