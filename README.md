Как пользоваться приложением:
1. Откройте проект через Intellij Idea (или любой другой)
2. Если вы откроете файл Main.java (он находится по пути src/main/java/com/example/kpo_big_dz) и ничего не горит красным, например, импорты из библиотеки javafx. То можно запустить проект. Иначе зайдите в File -> Project Structure -> Modules -> Dependencies -> + -> JARs or Directories и добавляете 2 .jar файла из директорий: resources/controlsfx/controlsfx-11.1.0.jar и resources/sqlite3/sqlite-jdbc-3.45.1.0.jar
3. Можно запускать проект (желательно установить шрифт, который находится в папке fonts)


Окно с авторизацией можно всегда оставлять открытым. Когда такого логина нет в бд, то пользователю предлагается зарегистрироваться как обычный покупатель или админ (отметить галочкой). А дальше там по интерфейсу все понятно будет.

В дз были использованы шаблоны проектирования: Наблюдатель и Одиночка. Использовался "Наблюдатель", чтобы отслеживать изменения в бд, например админ добавил новый товар и нужно оповестить меню всех покупателей, а те в свою очередь обновились. Использовалась одиночка, чтобы был единственный объект (например, список каких нибудь временных данных) тупо чтобы было удобнее.

Экран регистрации
<img src="screenshots/log%20reg%20menu.png" alt="Экран регистрации" width="500"/>

Экран ввода пароля и выбора роли
<img src="screenshots/password%20enter.png" alt="Экран ввода пароля и выбора роли" width="500"/>

Админская панель
<img src="screenshots/admin%20panel.png" alt="Экран ввода пароля и выбора роли" width="500"/>

Админская панель и клиентская (они синхронизируются в реальном времени при добавлении блюд, заказа и тд)
<img src="screenshots/admin%20and%20client%20panel.png" alt="Экран ввода пароля и выбора роли" width="500"/>