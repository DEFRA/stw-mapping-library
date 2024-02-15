package uk.gov.defra.tracesx.mapper.staticmappers;

public interface StaticDataMapper<T> {
  void apply(T data);
}
