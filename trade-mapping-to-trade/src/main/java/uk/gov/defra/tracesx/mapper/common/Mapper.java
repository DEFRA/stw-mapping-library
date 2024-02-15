package uk.gov.defra.tracesx.mapper.common;

@FunctionalInterface
public interface Mapper<T, R> {

  R map(T data);
}
