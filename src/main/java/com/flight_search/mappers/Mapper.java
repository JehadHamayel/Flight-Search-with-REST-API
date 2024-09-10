package com.flight_search.mappers;

public interface Mapper <A,B>{

    B mapTo(A a);

    A mapFrom(B b);

}
