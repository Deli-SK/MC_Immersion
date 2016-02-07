package com.deli.common.system;

public interface IFunc<TIn, TOut> {
    TOut execute(TIn in);
}
