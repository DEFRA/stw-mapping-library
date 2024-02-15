package uk.gov.defra.tracesx.common;

import uk.gov.defra.tracesx.exceptions.NotificationMapperException;

@FunctionalInterface
public interface Mapper<T, R> {

  R map(T data) throws NotificationMapperException;
}
