package com.snakybo.sengine.object;

import java.util.HashSet;
import java.util.Set;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.snakybo.sengine.debug.Logger;

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
		children = new HashSet<Transform>();
		parentMatrix = new Matrix4f();
		
		position = new Vector3f();
		rotation = new Quaternionf();
		scale = new Vector3f();
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
	 * @param direction - The direction to translate in
	 */
	public final void translate(Vector3f direction)
	{
		position.add(direction);
	}
	
	/**
	 * Rotate the transform
	 * @param axis - The axis to rotate on
	 * @param angle - The angle to add to the rotation
	 */
	public final void rotate(Vector3f axis, double angle)
	{
		rotate(axis, (float)angle);
	}
	
	/**
	 * Rotate the transform
	 * @param axis - The axis to rotate on
	 * @param angle - The angle to add to the rotation
	 */
	public final void rotate(Vector3f axis, float angle)
	{
		rotation.rotateAxis(angle, axis);
	}
	
	/**
	 * Look at a position
	 * @param position - The position in world space to look at
	 */
	public final void lookAt(Vector3f position)
	{
		lookAt(position, new Vector3f(0, 1, 0));
	}
	
	/**
	 * Look at a position
	 * @param position - The position in world space to look at
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
	 * @param parent - The new parent
	 */
	public final void setParent(Transform parent)
	{
		if(parent == this || children.contains(parent))
		{
			Logger.logWarning("You cannot parent the transform to itself, or it's children", this);
			return;
		}
		
		if(this.parent != null)
		{
			this.parent.children.remove(this);
		}
		
		this.parent = parent;
		this.parent.children.add(this);
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
		Matrix4f result = getParentMatrix();		
		result.translate(position);
		result.rotate(rotation);
		result.scale(scale);
		
		return result;
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
		return getParentMatrix().transformPosition(position);
	}
	
	/**
	 * @return The rotation of the transform in world space (parent.rotation + rotation)
	 */
	public final Quaternionf getRotation()
	{
		Quaternionf parentRotation = parent == null ? new Quaternionf() : new Quaternionf(parent.getRotation());
		return parentRotation.mul(rotation);	
	}
	
	/**
	 * @return The position of the transform in local space
	 */
	public final Vector3f getLocalPosition()
	{
		return position;
	}
	
	/**
	 * @return The rotation of the transform in local space
	 */
	public final Quaternionf getLocalRotation()
	{
		return rotation;
	}
	
	/**
	 * @return The scale of the transform
	 */
	public final Vector3f getLocalScale()
	{
		return scale;
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
	private final Matrix4f getParentMatrix()
	{
		parentMatrix = parent == null ? parentMatrix.identity() : parentMatrix.set(parent.getTransformation());
		return parentMatrix;
	}
}
