package in.co.itlabs.sis.business.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;
import org.sql2o.quirks.NoQuirks;
import org.sql2o.quirks.Quirks;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

@Service
public class DatabaseService {

	private final Sql2o sql2o;

	public DatabaseService(@Value("${mysql.url}") String url, @Value("${mysql.user}") String user,
			@Value("${mysql.password}") String password) throws SQLException {

//		sql2o = new Sql2o(url, user, password);
		final MysqlDataSource  dataSource = new MysqlConnectionPoolDataSource();
		dataSource.setUrl(url);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		
		final Quirks quirks = new NoQuirks(){
		    {
		        converters.put(LocalDate.class, new LocalDateConverter());
		    }
		};
		
		sql2o = new Sql2o(dataSource, quirks);
	}

	public Sql2o getSql2o() {
		return sql2o;
	}

	private class LocalDateConverter implements Converter<LocalDate> {
		@Override
		public LocalDate convert(final Object val) throws ConverterException {
			if (val instanceof java.sql.Date) {
				return ((java.sql.Date) val).toLocalDate();
			} else {
				return null;
			}
		}

		@Override
		public Object toDatabaseParam(final LocalDate val) {
			if (val == null) {
				return null;
			} else {
				return new java.sql.Date(val.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
			}
		}
	}

}
