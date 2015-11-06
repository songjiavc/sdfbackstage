package com.sdf.manager.common.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.converters.NumberConverter;


public class BeanUtil {
	// Modify Begin:Modify by Tianye 使BeanUtils支持java.util.Date的拷贝
	static {
		ConvertUtils.register(new DateTimeConverter() {

			@Override
			protected Class getDefaultType() {
				return java.util.Date.class;
			}

			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else {
					return super.convert(type, value);
				}
			}

		}, Date.class);

		ConvertUtils.register(new DoubleConverter1() {

			@Override
			protected Class getDefaultType() {
				return Double.class;
			}

			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else {
					return super.convert(type, value);
				}
			}

		}, Double.class);
		ConvertUtils.register(new LongConverter1() {

			@Override
			protected Class getDefaultType() {
				return Long.class;
			}

			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else {
					return super.convert(type, value);
				}
			}

		}, Long.class);
		
		ConvertUtils.register(new ShortConverter1() {

			@Override
			protected Class getDefaultType() {
				return Short.class;
			}

			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else {
					return super.convert(type, value);
				}
			}

		}, Short.class);
		ConvertUtils.register(new SqlTimestampConverter1() {
			@Override
			protected Class getDefaultType() {
				return Timestamp.class;
			}

			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else {
					return super.convert(type, value);
				}
			}

		}, Timestamp.class);
	}

	// Modify End

	/**
	 * 将一个bean的属性复制到另一个bean中。
	 * 
	 * @param expectedBean
	 *            目标bean.
	 * @param sourceBean
	 *            源bean.
	 * @return 目标bean.
	 * @author jinyi
	 * @version 1.0 </pre> Created on :2010-08-10 15:56:46 LastModified:
	 *          History: </pre>
	 * @throws Exception 
	 */
	public static final Object copyBeanProperties(Object expectedBean, Object sourceBean) throws Exception {
		if (sourceBean != null) {
			try {
				BeanUtils.copyProperties(expectedBean, sourceBean);
				
			} catch (IllegalAccessException e) {
				throw new Exception();
			} catch (InvocationTargetException e) {
				throw new Exception();
			}
		}

		return expectedBean;
	}
	public static void main(String[] args) {
		// System.out.println("----->");
		// EcgAuditDTO ecgAuditDTO=new EcgAuditDTO();
		// ecgAuditDTO.setConfirm("1");
		// ecgAuditDTO.setStaTime(new Timestamp(new Date().getTime()));
		// PhrDailyTestEcgAudit entity = EcgMgrAssembler.toEntity(ecgAuditDTO);
		// System.out.println("----->11");

	}
}

class DoubleConverter1 extends NumberConverter {

	public DoubleConverter1() {
		super(true);
	}

	public DoubleConverter1(Object defaultValue) {
		super(true, defaultValue);
	}

	protected Class getDefaultType() {
		return java.lang.Double.class;
	}
}

class LongConverter1 extends NumberConverter {

	public LongConverter1() {
		super(true);
	}

	public LongConverter1(Object defaultValue) {
		super(true, defaultValue);
	}

	protected Class getDefaultType() {
		return java.lang.Long.class;
	}
}
class ShortConverter1 extends NumberConverter {

	public ShortConverter1() {
		super(true);
	}

	public ShortConverter1(Object defaultValue) {
		super(true, defaultValue);
	}

	protected Class getDefaultType() {
		return java.lang.Short.class;
	}
}
class SqlTimestampConverter1 extends DateTimeConverter
{
  public SqlTimestampConverter1()
   
  {
	  super(null);
  }

  public SqlTimestampConverter1(Object defaultValue)
  {
	  super(defaultValue);
  }

  protected Class getDefaultType()
  {
    return Timestamp.class;
  }

  protected DateFormat getFormat(Locale locale, TimeZone timeZone)
  {
    DateFormat format = null;
    if (locale == null)
      format = DateFormat.getDateTimeInstance(3, 3);
    else {
      format = DateFormat.getDateTimeInstance(3, 3, locale);
    }
    if (timeZone != null) {
      format.setTimeZone(timeZone);
    }
    return format;
  }
  
}
