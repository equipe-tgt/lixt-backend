package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import org.locationtech.jts.io.ParseException;

import br.com.ifsp.pi.lixt.data.business.purchaseLocal.PurchaseLocal;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.utils.database.operations.GeolocalizationConvert;

public abstract class PurchaseLocalMapper {
	
	public static PurchaseLocal dtoToEntity(PurchaseLocalDto dto) {
		
		if(Objects.isNull(dto)) 
			return null;
		
		try {
			return PurchaseLocal.builder()
					.name(dto.getName())
					.location(GeolocalizationConvert.convertCoordsToPoint(dto.getLatitude(), dto.getLongitude()))
					.build();
		}
		catch(ParseException e) {
			return null;
		}

	}
	
	public static PurchaseLocalDto entityToDto(PurchaseLocal entity) {
		
		if(Objects.isNull(entity)) 
			return null;
		
		return PurchaseLocalDto.builder()
				.name(entity.getName())
				.latitude(entity.getLocation().getCoordinate().getX())
				.longitude(entity.getLocation().getCoordinate().getY())
				.build();
	}

}
