/*
 *  Copyright 2012 Giovanni Cuccu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
