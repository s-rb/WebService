package executor;

import java.sql.ResultSet;
import java.sql.SQLException;

// Generic ResultHandler для получения в ответ на запрос к БД объект ожидаемого класса (класс передаем в параметре)
public interface ResultHandler<T> {
	T handle(ResultSet resultSet) throws SQLException;
}
