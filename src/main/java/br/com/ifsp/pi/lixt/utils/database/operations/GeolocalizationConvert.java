package br.com.ifsp.pi.lixt.utils.database.operations;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeolocalizationConvert {

	public static Point convertCoordsToPoint(Double latitude, Double longitude) throws ParseException {
		return (Point) convertCoordsToGeomtry("POINT(" + longitude + " " + latitude + ")");
	}

	private static Geometry convertCoordsToGeomtry(String coords) throws ParseException {
		return new WKTReader().read(coords);
	}
	
	public static boolean validateXAxis(Double longitude) {
		return longitude > -180 && longitude < 180;
	}
	
	public static boolean validateYAxis(Double latitude) {
		return latitude > -90 && latitude < 90;
	}
}
