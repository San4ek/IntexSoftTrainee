# Онлайн-магазин обуви

## Руководство по установке и запуску системы

- ### C помощью Docker

Для запуска системы с помощью Docker необходимо предварительно установить Docker на физическую машину и после выполнить
команду
`docker-compose up` в консоли из директории, в которой будет
находиться [docker-compose файл](/docker/docker-compose.yml) после
клонирования.

- ### На физической машине

Для запуска системы на физической машине необходимо предварительно установить на нее и настроить Java 17, RabbitMQ,
PostgreSQL и Redis. После их установки нужно поочередно запускать каждый из сервисов. Порядок запуска:

1. Email
2. Auth
3. User/Stock/Cart (их внутренний порядок не важен)
4. Gateway

## Руководство по запуску тестов

Запуск тестов происходит в test-контейнерах, поэтому необходима предварительна установка Docker на физическую машину.
Далее тесты запускаются как и обычные тесты.

## Рекомендации по корректной настройке

> [!WARNING]
> Перед запуском на физической машине необходимо проверить корректность указания URL в @FeignClient классах (на данный
> момент они настроены под Docker).

> [!WARNING]
> Также необходимо проверить application.yml(-.properties) файлы всех сервисов и поменять конфигурацию в них под
> ваши локальные настройки.

> [!WARNING]
> Приватные настройки (т.е. пароли и логины, коды авторизации и т.п.) были вынесены в отдельные файлы для каждого
> сервиса и не хранятся на GitHub'е, поэтому также необходимости удобным для вас образом внедрить их в проект для
> корректной работы системы (через переменные окружения, системные переменный и т.д.).