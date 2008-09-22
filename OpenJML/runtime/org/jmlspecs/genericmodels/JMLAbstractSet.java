package org.jmlspecs.genericmodels;

import org.jmlspecs.lang.*;
import org.jmlspecs.annotations.*;

@Pure
public abstract class JMLAbstractSet<E> implements JMLSetType<E> {

    // Element equality is tested by the elem_equal method, implemented in 
    // each derived class
    
    protected abstract @NonNull JMLListNode<E> the_list();
    
    //**************************** Observers **********************************

    /** Is the argument ".equals" to one of the
     *  objects in theSet.
     */
    /*@ also
      @   public normal_behavior
      @     requires elem != null;
      @     ensures (* \result <==>
      @       elem is ".equals" to one of the objects in theSet *);
      @ also
      @   public normal_behavior
      @     requires elem == null;
      @     ensures (* \result <==> null is one of the objects in theSet *);
      @ also
      @   public normal_behavior
      @     requires isEmpty();
      @     ensures ! \result ;
      @*/    
    public boolean has(@Nullable Object elem ) {
        return the_list() != null && the_list().has(elem);
    }  

    /** Return a string representation of this object.
     */
    /*@ also
      @   public normal_behavior
      @     ensures (* \result is a string representation of this *);
      @*/    
    public @NonNull String toString() {
        String newStr = new String("{");
        @Nullable JMLListNode<E> setWalker = the_list();
        if (setWalker != null) {
            newStr = newStr + setWalker.val;
            setWalker = setWalker.next();
        }
        while (setWalker != null) {
            newStr = newStr + ", " + setWalker.val;
            setWalker = setWalker.next();
        }   
        newStr = newStr + "}";
        return newStr;
    }



}