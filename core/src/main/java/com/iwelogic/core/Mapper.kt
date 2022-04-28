package com.iwelogic.core

interface Mapper<I, O> {
    fun map(input: I): O
    fun reverseMap(input: O): I
}