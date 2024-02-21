package uk.gov.defra.stw.mapping.toipaffs.common;

import uk.gov.defra.stw.mapping.toipaffs.exceptions.NotificationMapperException;

@FunctionalInterface
public interface Mapper<T, R> {

  R map(T data) throws NotificationMapperException;
}
