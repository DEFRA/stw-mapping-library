package uk.gov.defra.stw.mapping.totrade.common;

@FunctionalInterface
public interface Mapper<T, R> {

  R map(T data);
}
