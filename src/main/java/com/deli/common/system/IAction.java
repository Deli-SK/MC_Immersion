package com.deli.common.system;

public interface IAction<TIn> {
    void execute(TIn in);
}

