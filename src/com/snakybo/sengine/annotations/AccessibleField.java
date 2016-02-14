package com.snakybo.sengine.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Add this annotation before a private, protected or internal field to expose it to the engine
 * @author Kevin
 * @since Feb 13, 2016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AccessibleField
{
}
