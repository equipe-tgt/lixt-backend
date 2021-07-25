package br.com.ifsp.pi.lixt.mapper;

import java.util.Objects;

import org.locationtech.jts.io.ParseException;

import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
import br.com.ifsp.pi.lixt.dto.PurchaseLocalDto;
import br.com.ifsp.pi.lixt.utils.database.operations.GeolocalizationConvert;
import br.com.ifsp.pi.lixt.utils.exceptions.PreconditionFailedException;

public abstract class PurchaseLocalMapper {
	
	public static PurchaseLocal dtoToEntity(PurchaseLocalDto dto) throws PreconditionFailedException {
		
		if(Objects.isNull(dto)) 
			return null;
		
		try {
			return PurchaseLocal.builder()
					.id(dto.getId())
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
				.id(entity.getId())
				.name(entity.getName())
				.latitude(entity.getLocation().getCoordinate().getY())
				.longitude(entity.getLocation().getCoordinate().getX())
				.build();
	}

}
