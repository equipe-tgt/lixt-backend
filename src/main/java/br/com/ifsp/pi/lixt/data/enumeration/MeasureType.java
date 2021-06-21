package br.com.ifsp.pi.lixt.data.enumeration;

import br.com.ifsp.pi.lixt.utils.enumerations.AbstractEnumConverter;
import br.com.ifsp.pi.lixt.utils.enumerations.EnumID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MeasureType implements EnumID {

	L(1),
	ML(2),
	
	
	KG(3),
	G(4),
	MG(5),
	
	UNITY(6);
	
	private int id;
	
	public static class Converter extends AbstractEnumConverter<MeasureType> {
		public Converter() {
			super(MeasureType.values());
		}
	}
	
}
