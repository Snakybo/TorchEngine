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
public final class ActorUpdater
{
	private Set<Actor> toSpawn;
	private Set<Actor> toDestroy;
	private Set<Actor> toUpdate;
	private Set<Actor> toStart;
	private Set<Actor> toStop;
	
	private ActorRegister actorRegister;
	
	public ActorUpdater(ActorRegister actorRegister)
	{
		this.actorRegister = actorRegister;
		
		toSpawn = new HashSet<>();
		toDestroy = new HashSet<>();
		toUpdate = new HashSet<>();
		toStart = new HashSet<>();
		toStop = new HashSet<>();
	}
	
	public final void update()
	{
		for(Actor actor : actorRegister.getNewRegisters())
		{
			toSpawn.add(actor);
			
			toStart.add(actor);
			toUpdate.add(actor);
		}
		
		Set<Actor> toSpawnCache = new HashSet<>(toSpawn);
		Set<Actor> toStartCache = new HashSet<>(toStart);
		Set<Actor> toUpdateCache = new HashSet<>(toUpdate);
		
		toSpawn.clear();
		toStart.clear();
		
		toSpawnCache.forEach(Actor::onSpawned);
		
		toStartCache.forEach(Actor::start);
		
		for(Actor actor : toUpdateCache)
		{
			actor.update();
			actor.getActorComponentUpdater().update();
		}
		
		for(Actor actor : actorRegister.getAllToDestroy())
		{
			toDestroy.add(actor);
			
			toStop.add(actor);
		}
		
		Set<Actor> toStopCache = new HashSet<>(toStop);
		Set<Actor> toDestroyCache = new HashSet<>(toDestroy);
		
		toStop.clear();
		toDestroy.clear();
		
		for(Actor actor : toStopCache)
		{
			actor.stop();
			toUpdate.remove(actor);
		}
		
		toDestroyCache.forEach(Actor::onDestroyed);
		
		actorRegister.process();
	}
}
