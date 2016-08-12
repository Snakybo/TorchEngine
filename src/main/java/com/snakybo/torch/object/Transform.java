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

package com.snakybo.torch.object;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Transform
{
	GameObject gameObject;
	
	private Set<Transform> children;
	
	private Matrix4f parentMatrix;
	private Transform parent;
	
	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	/**
	 * Create a new {@link Transform}
	 */
	public Transform()
	{
		children = new HashSet<>();
		parentMatrix = new Matrix4f();
		
		position = new Vector3f();
		rotation = new Quaternionf();
		scale = new Vector3f(1, 1, 1);
	}
	
	@Override
	public String toString()
	{
		String position = "Position(" + this.position.x +", " + this.position.y + ", " + this.position.z + ")";
		String rotation = "Rotation(" + this.rotation.x +", " + this.rotation.y + ", " + this.rotation.z + ", " + this.rotation.w + ")";
		String scale = "Scale(" + this.scale.x + ", " + this.scale.y + ", " + this.scale.z + ")";
		
		return gameObject + ": " + position + " " + rotation + " " + scale;
	}
	
	/**
	 * Translate the transform
	 * @param direction The direction to translate in
	 */
	public final void translate(Vector3f direction)
	{
		position.add(direction);
	}
	
	/**
	 * Rotate the transform
	 * @param axis The axis to rotate on
	 * @param angle The angle to add to the rotation
	 */
	public final void rotate(Vector3f axis, double angle)
	{
		rotate(axis, (float)angle);
	}
	
	/**
	 * Rotate the transform
	 * @param axis The axis to rotate on
	 * @param angle The angle to add to the rotation
	 */
	public final void rotate(Vector3f axis, float angle)
	{
		rotation.rotateAxis(angle, axis);
	}
	
	/**
	 * Look at a position
	 * @param position The position in world space to look at
	 */
	public final void lookAt(Vector3f position)
	{
		lookAt(position, new Vector3f(0, 1, 0));
	}
	
	/**
	 * Look at a position
	 * @param position The position in world space to look at
	 * @param up The up direction
	 */
	public final void lookAt(Vector3f position, Vector3f up)
	{
		Vector3f direction = new Vector3f(position);
		direction.sub(position);
		direction.normalize();
		
		rotation.lookRotate(direction, up);
	}
	
	/**
	 * Set the parent of the {@link Transform}
	 * @param parent The new parent
	 */
	public final void setParent(Transform parent)
	{
		if(parent == this || children.contains(parent))
		{
			throw new UnsupportedOperationException("You cannot parent the transform to itself, or it's children");
		}
		
		if(gameObject.scene != parent.gameObject.scene)
		{
			throw new UnsupportedOperationException("Currently it is not possible to parent to an object in another scene");
		}
		
		if(this.parent != null)
		{
			this.parent.children.remove(this);
		}
		
		this.parent = parent;
		this.parent.children.add(this);
	}
	
	public final void setLocalPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public final void setLocalRotation(Quaternionf rotation)
	{
		this.rotation = rotation;
	}
	
	public final void setLocalScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	/**
	 * @return All children of the {@link Transform}
	 */
	public final Iterable<Transform> getChildren()
	{
		return children;
	}
	
	/**
	 * @return The transformation matrix of the transform
	 */
	public final Matrix4f getTransformation()
	{
		return getParentMatrix().translate(position).rotate(rotation).scale(scale);
	}
	
	/**
	 * @return The attached {@link GameObject}
	 */
	public final GameObject getGameObject()
	{
		return gameObject;
	}
	
	/**
	 * @return The root {@link Transform}, if this transform is the root transform, it will return this transform
	 */
	public final Transform getRoot()
	{
		Transform root = this;
		
		while(root.getParent() != null)
		{
			root = root.getParent();
		}
		
		return root;
	}
	
	/**
	 * @return The parent {@link Transform}
	 */
	public final Transform getParent()
	{
		return parent;
	}
	
	/**
	 * @return The position of the transform in world space (parent.position + position)
	 */
	public final Vector3f getPosition()
	{
		return getParentMatrix().transformPosition(new Vector3f(position));
	}
	
	/**
	 * @return The rotation of the transform in world space (parent.rotation + rotation)
	 */
	public final Quaternionf getRotation()
	{
		Quaternionf parentRotation = parent == null ? new Quaternionf() : parent.getRotation();
		return parentRotation.mul(new Quaternionf(rotation));
	}
	
	/**
	 * @return The position of the transform in local space
	 */
	public final Vector3f getLocalPosition()
	{
		return new Vector3f(position);
	}
	
	/**
	 * @return The rotation of the transform in local space
	 */
	public final Quaternionf getLocalRotation()
	{
		return new Quaternionf(rotation);
	}
	
	/**
	 * @return The scale of the transform
	 */
	public final Vector3f getLocalScale()
	{
		return new Vector3f(scale);
	}
	
	/**
	 * @return The direction that is forward for this {@link Transform}
	 */
	public final Vector3f getForward()
	{
		return new Vector3f(0, 0, 1).rotate(rotation);
	}
	
	/**
	 * @return The direction that is backward for this {@link Transform}
	 */
	public final Vector3f getBack()
	{
		return new Vector3f(0, 0, -1).rotate(rotation);
	}
	
	/**
	 * @return The direction that is left for this {@link Transform}
	 */
	public final Vector3f getLeft()
	{
		return new Vector3f(-1, 0, 0).rotate(rotation);
	}
	
	/**
	 * @return The direction that is right for this {@link Transform}
	 */
	public final Vector3f getRight()
	{
		return new Vector3f(1, 0, 0).rotate(rotation);
	}
	
	/**
	 * @return The direction that is up for this {@link Transform}
	 */
	public final Vector3f getUp()
	{
		return new Vector3f(0, 1, 0).rotate(rotation);
	}
	
	/**
	 * @return The direction that is down for this {@link Transform}
	 */
	public final Vector3f getDown()
	{
		return new Vector3f(0, -1, 0).rotate(rotation);
	}
	
	/**
	 * @return The amount of children the {@link Transform} has
	 */
	public final int getNumChildren()
	{
		return children.size();
	}
	
	/**
	 * @return The parent's {@link #getTransformation()} matrix
	 */
	private Matrix4f getParentMatrix()
	{
		parentMatrix = parent == null ? parentMatrix.identity() : parentMatrix.set(parent.getTransformation());
		return parentMatrix;
	}
}
