package com.sg2net.utilities.ListaCAP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegExp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("(.*)<td class=ivoce>CAP</td><td class=ival><b>(\\d+)-(\\d+)</b>(.*)");
		Matcher m = pattern.matcher("<tr><td class=ivoce>CAP</td><td class=ival><b>15121-15122</b> (<b><a href=\"../../pg/cap.php?Alessandria\" rel=\"nofollow\">cerca</a></b>)</td></tr>");
		System.out.println(m.matches());
		if (m.matches()) {
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			System.out.println(m.group(3));
		}

	}

}
