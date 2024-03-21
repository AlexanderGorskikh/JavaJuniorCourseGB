package Lesson3.annotations;


import java.lang.reflect.Field;

public class SQLHandler {
    /**
     * Метод который возврощает строку для создания таблицы SQL
     *
     * @param type тип который должен быть помещён в базу данных
     * @return строка для запроса SQL
     */
    public <T> String createSQLTable(Class<T> type, boolean withParameter) {
        return type.getAnnotation(Table.class).table() +
                "(" +
                findID(type.getDeclaredFields(), withParameter) +
                findColumns(type.getDeclaredFields(), withParameter) +
                ")";
    }
    /**
     * В данном методе мы находим колонки через аннотированные @Column поля
     *
     * @param fields
     * @return Строка с полями и их типом
     */
    private String findColumns(Field[] fields, boolean withParameter) {
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            var annotation = f.getAnnotation(Column.class);
            if (annotation != null) {
                sb.append(", ");
                if (f.getType().equals(long.class) || f.getType().equals(int.class)) {
                    sb.append(annotation.column()).append(withParameter ? " BIGINT" : "");
                } else if (f.getType().equals(double.class) || f.getType().equals(float.class)) {
                    sb.append(annotation.column()).append(withParameter ? " DOUBLE PRECISION" : "");
                } else {
                    sb.append(annotation.column()).append(withParameter ? " VARCHAR(256)" : "");
                }
            }
        }
        return sb.toString();
    }
    /**
     * Метод для поиска первичного ключа
     *
     * @param fields аннотированные поля
     * @return строковое выражение первичного ключа SQl
     */
    private String findID(Field[] fields, boolean withParameter) {
        for (Field f : fields) {
            if (f.getAnnotation(Id.class) != null) {
                if (f.getAnnotation(Id.class).autoIncrement()) {
                    return "id " + (withParameter ? "BIGINT AUTO_INCREMENT" : "");
                } else {
                    return "id " + (withParameter ? "BIGINT" : "");
                }
            }
        }
        throw new RuntimeException("id field not found");
    }

}
