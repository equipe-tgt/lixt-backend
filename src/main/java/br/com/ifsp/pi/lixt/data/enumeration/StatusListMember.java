package br.com.ifsp.pi.lixt.data.enumeration;

import br.com.ifsp.pi.lixt.utils.enumerations.AbstractEnumConverter;
import br.com.ifsp.pi.lixt.utils.enumerations.EnumID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusListMember implements EnumID {

	WAITING(1),
	ACEPT(2),
	REJECT(3);
	
	private int id;
	
	public static class Converter extends AbstractEnumConverter<StatusListMember> {
		public Converter() {
			super(StatusListMember.values());
		}
	}
}
