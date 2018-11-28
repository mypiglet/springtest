package log.log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

@Plugin(name = "MylogConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "mylog", "mylog" })
public final class MylogConverter extends LogEventPatternConverter {

	private static final MylogConverter INSTANCE = new MylogConverter();

	private MylogConverter() {
		this("mylog", "mylog"); // not sure why!
	}

	protected MylogConverter(String name, String style) {
		super(name, style);
	}

	public static MylogConverter newInstance(final String[] options) {
		return INSTANCE;
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		toAppendTo.append("链路编号");
	}

}
