# IS Coursework (Spring Boot + Thymeleaf)

## Запуск
```bash
mvn spring-boot:run
```

По умолчанию приложение поднимется на `http://localhost:8080`.

## Учётные записи (из `data.sql`)
Все пользователи имеют пароль **`password`**.

| Email | Роли |
| --- | --- |
| `vlad@gmail.com` | ADMIN |
| `bob@example.com` | USER, MODERATOR |
| `alice@example.com` | ADMIN |

## Основные URL
* `/dashboard` — дашборд пользователя.
* `/fridges` — список холодильников.
* `/products/my` — мои продукты.

## Админка / модератор
* `/admin` — главная админки (только ADMIN).
* `/admin/users` — управление пользователями и ролями.
* `/admin/fridges` — управление холодильниками.
* `/admin/fridges/{id}/zones` — управление зонами.
* `/admin/products` — модерация продуктов.
* `/admin/reports` — отчёты (FR‑8).
* `/admin/audit` — журнал действий (FR‑10).

* `/moderator` — главная модератора (ADMIN/MODERATOR).
* `/moderator/fridges` — управление холодильниками и зонами (FR‑3).
* `/moderator/reports` — отчёты (FR‑8).
