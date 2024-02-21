package com.sylvona.leona.core.commons.containers;

record EitherImpl<Left, Right extends Throwable>(Left left, Right right) implements Either<Left, Right> {
}
