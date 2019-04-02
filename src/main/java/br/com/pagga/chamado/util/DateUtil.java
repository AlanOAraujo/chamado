package br.com.pagga.chamado.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

	public static String format( Date date ) {

		return format(date, null);
	}

	public static String format( Date date, String pattern ) {

		if ( date == null )
			return "";

		if ( StringUtils.isBlank(pattern) )
			pattern = "dd/MM/yyyy";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		String result = sdf.format(date);

		return result;
	}
}
