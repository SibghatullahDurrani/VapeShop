package com.sibghat.vape_shop.mappers;

public interface IMapper <A,B>{

    A mapTo(B b);
    B mapFrom(A a);

}
