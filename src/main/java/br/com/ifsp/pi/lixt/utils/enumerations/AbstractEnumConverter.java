package br.com.ifsp.pi.lixt.utils.enumerations;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AbstractEnumConverter<ENUM extends EnumID> implements AttributeConverter<ENUM, Integer> {
	
	private ENUM[] valores;
	
	public AbstractEnumConverter(ENUM[] valores) {
		this.valores = valores;
	}

	@Override
	public Integer convertToDatabaseColumn(ENUM attribute) {
		return attribute.getId();
	}

	@Override
	public ENUM convertToEntityAttribute(Integer id) {
		for(ENUM v : valores) {
			if(v.getId() == id)
				return v;
		}
		return null;
	}

}
