package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();

        // Создание таблицы User(ов)
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу
        userService.saveUser("John", "Coffey", (byte) 26);
        userService.saveUser("Carl", "Garner", (byte) 18);
        userService.saveUser("Zoya", "Rhodes", (byte) 43);
        userService.saveUser("Emily", "Villa", (byte) 38);

        // Получение всех User из базы и вывод в консоль
        System.out.println(userService.getAllUsers());

        // Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();
    }
}
