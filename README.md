# skypro telegram bot

## 0 Описание
Курсовая работа “Бот в Telegram”

Подробное задание находится в [TASK.md](TASK.md)

## 1 Сконфигурировать приложения
Для инициализации проекта необходимо добавить в файл [application.properties](src/main/resources/application.properties) токен бота
```yaml
telegram.bot.token=
```
## 2 Подключение к базе данных 
 - Используется Postgres 
 - По умолчанию в файле [application.properties](src/main/resources/application.properties) выставлены такие настройки
```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/skypro_telegram_bot
spring.datasource.username=skypro_telegram_bot
spring.datasource.password=skypro_telegram_bot_pass
```