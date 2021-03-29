package com.github.milwolf.mmreader.core.fs9721lp3;

/**
 * @author MilWolf
 */
public interface IteratorWithException<E> {

    boolean hasNext();

    E next() throws Exception;
}
