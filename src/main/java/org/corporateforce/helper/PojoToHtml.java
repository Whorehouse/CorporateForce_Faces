package org.corporateforce.helper;

import java.lang.reflect.Field;
import javax.persistence.Entity;

public class PojoToHtml {
	
	public static String dump(Object object) {
		return dump(object, 0);
	}

	public static String dump(Object object, int level) {
		Field[] fields = object.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		sb.append("<span style='color:red;'>"+object.getClass().getSimpleName()+"</span>");
		sb.append(" {<br/>");

		boolean firstRound = true;

		for (Field field : fields) {
			if (!firstRound) {
				sb.append(", <br/>");
			} else {
				firstRound = false;
			}
			for (int i = 0; i < level + 1; i++) {
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			field.setAccessible(true);
			try {
				final Object fieldObj = field.get(object);
				final String value;
				if (null == fieldObj) {
					value = "null";
				} else {
					if (fieldObj.getClass().isAnnotationPresent(Entity.class)) {
						value = "<br/>" + dump(fieldObj, level + 2);
					} else {
						value = fieldObj.toString();
					}
				}
				sb.append("<span style='color:blue;'>"+field.getName()+"</span>");
				sb.append("=").append("\'")
						.append(value).append("\'");
			} catch (IllegalAccessException ignore) {
				// this should never happen
			}

		}
		sb.append("<br/>");
		for (int i = 0; i < level; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		sb.append('}');
		return sb.toString();
	}
}
