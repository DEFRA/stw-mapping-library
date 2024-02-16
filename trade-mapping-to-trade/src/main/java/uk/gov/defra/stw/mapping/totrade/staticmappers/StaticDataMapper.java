package uk.gov.defra.stw.mapping.totrade.staticmappers;

public interface StaticDataMapper<T> {
  void apply(T data);
}
