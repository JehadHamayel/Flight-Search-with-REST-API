package com.flight_search.mappers;

/**
 * A generic interface for mapping between two types.
 * This interface defines methods for converting from one type to another and vice versa.
 *
 * @param <A> the source type to be converted.
 * @param <B> the target type to be converted to and from.
 */
public interface Mapper<A, B> {

    /**
     * Converts from the source type {@code A} to the target type {@code B}.
     * @param a the source object to be converted.
     * @return the converted object of type {@code B}.
     */
    B mapTo(A a);

    /**
     * Converts from the target type {@code B} back to the source type {@code A}.
     * @param b the target object to be converted.
     * @return the converted object of type {@code A}.
     */
    A mapFrom(B b);

}
