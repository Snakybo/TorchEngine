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

import com.snakybo.torch.engine.component.ActorComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ActorComponentUpdater
{
	private Set<ActorComponent> toUpdate;
	private Set<ActorComponent> toStart;
	private Set<ActorComponent> toStop;
	
	public ActorComponentUpdater()
	{
		toUpdate = new HashSet<>();
		toStart = new HashSet<>();
		toStop = new HashSet<>();
	}
	
	public final void update()
	{
		Set<ActorComponent> toUpdateCache = new HashSet<>(toUpdate);
		Set<ActorComponent> toStartCache = new HashSet<>(toStart);
		
		toStart.clear();
		
		toStartCache.forEach(ActorComponent::start);
		
		toUpdateCache.forEach(ActorComponent::update);
		
		Set<ActorComponent> toStopCache = new HashSet<>(toStop);
		
		toStop.clear();
		
		for(ActorComponent component : toStopCache)
		{
			component.stop();
			toUpdate.remove(component);
		}
	}
	
	public final void register(ActorComponent component)
	{
		toStart.add(component);
		toUpdate.add(component);
	}
	
	public final void unregister(ActorComponent component)
	{
		toStop.add(component);
	}
}
