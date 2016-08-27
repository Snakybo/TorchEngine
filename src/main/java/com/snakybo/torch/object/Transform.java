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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Holds transform data for {@link GameObject}s.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Transform implements Serializable
{
	GameObject gameObject;
	
	private Set<Transform> children;
	
	private Matrix4f parentMatrix;
	private Transform parent;
	
	private Vector3f position;
	private Quaternionf rotation;
	private Vector3f scale;
	
	/**
	 * Create a new {@code Transform}.
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
	public final String toString()
	{
		String position = "Position(" + this.position.x +", " + this.position.y + ", " + this.position.z + ")";
		String rotation = "Rotation(" + this.rotation.x +", " + this.rotation.y + ", " + this.rotation.z + ", " + this.rotation.w + ")";
		String scale = "Scale(" + this.scale.x + ", " + this.scale.y + ", " + this.scale.z + ")";
		
		return gameObject + ": " + position + " " + rotation + " " + scale;
	}
	
	/**
	 * <p>
	 * Translate the transform.
	 * </p>
	 *
	 * @param direction The direction to translate in.
	 */
	public final void translate(Vector3f direction)
	{
		position.add(direction);
	}
	
	/**
	 * <p>
	 * Rotate the transform.
	 * </p>
	 *
	 * @param axis The axis to rotate on.
	 * @param angle The angle to rotate by.
	 */
	public final void rotate(Vector3f axis, double angle)
	{
		rotate(axis, (float)angle);
	}
	
	/**
	 * <p>
	 * Rotate the transform.
	 * </p>
	 *
	 * @param axis The axis to rotate on.
	 * @param angle The angle to rotate by.
	 */
	public final void rotate(Vector3f axis, float angle)
	{
		rotation.rotateAxis(angle, axis);
	}
	
	/**
	 * <p>
	 * Look at a position.
	 * </p>
	 *
	 * @param position The position in world space to look at.
	 */
	public final void lookAt(Vector3f position)
	{
		lookAt(position, new Vector3f(0, 1, 0));
	}
	
	/**
	 * <p>
	 * Look at a position.
	 * </p>
	 *
	 * @param position The position in world space to look at.
	 * @param up The up direction.
	 */
	public final void lookAt(Vector3f position, Vector3f up)
	{
		Vector3f direction = new Vector3f(position);
		direction.sub(position);
		direction.normalize();
		
		rotation.lookRotate(direction, up);
	}
	
	/**
	 * <p>
	 * Set the parent of the {@code Transform}.
	 * </p>
	 *
	 * @param parent The new parent.
	 */
	public final void setParent(Transform parent)
	{
		if(parent == this || children.contains(parent))
		{
			throw new UnsupportedOperationException("You cannot parent the transform to itself, or it's children");
		}
		
		if(this.parent != null)
		{
			this.parent.children.remove(this);
		}
		
		this.parent = parent;
		this.parent.children.add(this);
	}
	
	/**
	 * <p>
	 * Set the local position of the {@code Transform}.
	 * </p>
	 *
	 * @param position The new position.
	 */
	public final void setLocalPosition(Vector3f position)
	{
		this.position = position;
	}
	
	/**
	 * <p>
	 * Set the local rotation of the {@code Transform}.
	 * </p>
	 *
	 * @param rotation The new rotation.
	 */
	public final void setLocalRotation(Quaternionf rotation)
	{
		this.rotation = rotation;
	}
	
	/**
	 * <p>
	 * Set the local scale of the {@code Transform}.
	 * </p>
	 *
	 * @param scale The new scale.
	 */
	public final void setLocalScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	/**
	 * <p>
	 * Get an array containing all children.
	 * </p>
	 *
	 * @return All children.
	 */
	public final Transform[] getChildren()
	{
		return children.toArray(new Transform[children.size()]);
	}
	
	/**
	 * <p>
	 * Get the transformation matrix.
	 * </p>
	 *
	 * @return The transformation matrix.
	 */
	public final Matrix4f getTransformation()
	{
		return getParentMatrix().translate(position).rotate(rotation).scale(scale);
	}
	
	/**
	 * <p>
	 * Get the attached {@code GameObject}.
	 * </p>
	 *
	 * @return The attached {@code GameObject}
	 */
	public final GameObject getGameObject()
	{
		return gameObject;
	}
	
	/**
	 * <p>
	 * Get the root {@code Transform}.
	 * </p>
	 *
	 * @return The root {@code Transform}, if this is the root {@code Transform}, it will return {@code this}.
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
	 * <p>
	 * Get this {@code Transform}'s parent.
	 * </p>
	 *
	 * @return The parent.
	 */
	public final Transform getParent()
	{
		return parent;
	}
	
	/**
	 * <p>
	 * Get the position in world-space (parent.position + position).
	 * </p>
	 *
	 * @return The position in world-space.
	 */
	public final Vector3f getPosition()
	{
		return getParentMatrix().transformPosition(new Vector3f(position));
	}
	
	/**
	 * <p>
	 * Get the rotation in world-space (parent.rotation + rotation).
	 * </p>
	 *
	 * @return The rotation world space.
	 */
	public final Quaternionf getRotation()
	{
		Quaternionf parentRotation = parent == null ? new Quaternionf() : parent.getRotation();
		return parentRotation.mul(new Quaternionf(rotation));
	}
	
	/**
	 * <p>
	 * Get the position in local-space.
	 * </p>
	 *
	 * @return The position in local-space.
	 */
	public final Vector3f getLocalPosition()
	{
		return new Vector3f(position);
	}
	
	/**
	 * <p>
	 * Get the rotation in local-space.
	 * </p>
	 *
	 * @return The rotation of the transform in local-space.
	 */
	public final Quaternionf getLocalRotation()
	{
		return new Quaternionf(rotation);
	}
	
	/**
	 * <p>
	 * Get the scale in local-space.
	 * </p>
	 *
	 * @return The scale in local-space.
	 */
	public final Vector3f getLocalScale()
	{
		return new Vector3f(scale);
	}
	
	/**
	 * <p>
	 * Get the forward direction.
	 * </p>
	 *
	 * @return The forward direction.
	 */
	public final Vector3f getForward()
	{
		return new Vector3f(0, 0, 1).rotate(rotation);
	}
	/**
	 * <p>
	 * Get the backward direction.
	 * </p>
	 *
	 * @return The backward direction.
	 */
	public final Vector3f getBack()
	{
		return new Vector3f(0, 0, -1).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the left direction.
	 * </p>
	 *
	 * @return The left direction.
	 */
	public final Vector3f getLeft()
	{
		return new Vector3f(-1, 0, 0).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the right direction.
	 * </p>
	 *
	 * @return The right direction.
	 */
	public final Vector3f getRight()
	{
		return new Vector3f(1, 0, 0).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the up direction.
	 * </p>
	 *
	 * @return The up direction.
	 */
	public final Vector3f getUp()
	{
		return new Vector3f(0, 1, 0).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the down direction.
	 * </p>
	 *
	 * @return The down direction.
	 */
	public final Vector3f getDown()
	{
		return new Vector3f(0, -1, 0).rotate(rotation);
	}
	
	/**
	 * <p>
	 * Get the number of children this {@code Transform} has.
	 * </p>
	 *
	 * @return The number of children.
	 */
	public final int getNumChildren()
	{
		return children.size();
	}
	
	private Matrix4f getParentMatrix()
	{
		parentMatrix = parent == null ? parentMatrix.identity() : parentMatrix.set(parent.getTransformation());
		return parentMatrix;
	}
}
