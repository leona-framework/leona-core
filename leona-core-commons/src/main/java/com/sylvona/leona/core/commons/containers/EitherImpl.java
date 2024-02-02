package com.sylvona.leona.core.commons.containers;

record EitherImpl<Left, Right>(Left left, Right right) implements Either<Left, Right> {
}
