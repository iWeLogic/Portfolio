package com.iwelogic.core

interface Mapper<in I, out O> {
    fun map(input: I): O
}