package com.sylvona.leona.core.commons.containers;

record EitherImpl<OK, ERR extends Throwable>(OK result, ERR error) implements Either<OK, ERR> {
}
