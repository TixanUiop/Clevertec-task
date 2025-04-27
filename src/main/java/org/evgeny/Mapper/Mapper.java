package org.evgeny.Mapper;

public interface Mapper<F, T> {
    T map(F from);
}
