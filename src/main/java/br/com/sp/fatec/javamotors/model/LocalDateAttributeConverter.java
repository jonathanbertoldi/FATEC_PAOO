package br.com.sp.fatec.javamotors.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date>{
	
	public Date convertToDatabaseColumn(LocalDate arg0) {
		return (arg0 == null ? null : Date.valueOf(arg0));
	}

	public LocalDate convertToEntityAttribute(Date arg0) {
		return (arg0 == null ? null : arg0.toLocalDate());
	}
}
