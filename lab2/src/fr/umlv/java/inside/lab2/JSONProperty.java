package fr.umlv.java.inside.lab2;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Target permet de mettre des annotations sur les classes
 * Retention permet de voir
 * Documented : apparait ds la javadoc
 * */
@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.SOURCE )
@Documented
@interface JSONProperty {
	
	String value() default "";
	/*
 * @interface ClassPreamble 
 * {
   String author();
   String date();
   int currentRevision() default 1;
   String lastModified() default "N/A";
   String lastModifiedBy() default "N/A";
   // Note use of array
   String[] reviewers();
}
 * 
 * */
}
