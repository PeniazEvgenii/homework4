# Patterns
1. **Builder**: `ConnectionProperty` - для параметров подключения к БД, `UserCreateDto`, `UserEntity`. Для остальных dto использовался lombok.
2. **Proxy** (ru/aston/hometask/connection): 
   - `ConnectionManagerProxy`: оборачивает реальный `ConnectionManager` и реализует пул соединений. 
   Выдаёт готовые `Connection`, и при вызове метода close() возварщает соединение в пул. Пул закрывается в `ContextListener` при закрытии приложения
   - `UserDaoProxy`: кэширует результаты чтения из БД: сначала поиск осуществляется в Map, при отсутствии пользователя обращается в бд.
3. **Decorator** (ru/aston/hometask/discount):
  `IUserDiscount` - интерфейс расчета скидки пользователя.`StandardUserDiscount` - конкретная реализация интерфейса.
  `BaseDiscountDecorator` - базовый декоратор, делегирующий вызов следующему декоратору. 
4. **Chain of Responsibility** (ru/aston/hometask/validator): 
   валидирует входной `UserCreateDto` через цепочку валидаторов, унаследованных от `BaseUserValidator`. 
   Каждый конкретный валидатор получает единый `ValidationContext`, добавляет в него свои ошибки, а затем передаёт его следующему.
   В итоге отвалидируются все поля пользователя и можно получить полный список ошибок.
5. **Adapter**  (ru/aston/hometask/adapter): 
   `FileResourcePartAdapter` - адаптер, который преобразует объект Part в `IFileResource`, 
   который необходим для параметров метода `upload(IFileResource resource)`.
6. **Strategy** (ru/aston/hometask/service/parser): 
   в `UploadService` выбор папрсера для чтения файлов осуществляется по расширению этого файла (`CSV`, `XLSX`, `JSON`) через `Map<String, IProductParser>`. 
   
   
Добавил dockerfile с tomcat и docker-compose для запуска приложения и бд