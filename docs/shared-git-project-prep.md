### X messenger - app client aprt


### Генерация ключей ssh
Ключи ssh необходимы для операций git по ssh (например, `git push`)
На стороне нового контрибьютора:
1. открыть PowerShell от имени Администратора
2. запуск ssh-agent, который хранит ваши ssh-ключи
```
Set-Service ssh-agent -StartupType Automatic
Start-Service ssh-agent
```
3. открыть обычный PowerShell
4. генерация пары ssh-ключей (public и private)
```
# заменить <win_user> на имя вашего юзера Windows и <my-key-name> на узнаваемое имя ключа
ssh-keygen -t ed25519 -f C:\Users\<win_user>\.ssh\<my-key-name>
```
5. добавление ключа в ssh-agent
```
ssh-add.exe C:\Users\<win_user>\.ssh\<my-key-name>
```
6. открыть файл `C:\Users\<win_user>\.ssh\config` (или создать, если нет) и добавить конфиг
```
Host github.com
  HostName github.com
  User <github_user>
  PreferredAuthentications publickey
  IdentityFile C:\Users\<win_user>\.ssh\<my-key-name>
```
7. передать владельцу репозитория публичный ключ (содержимое `C:\Users\<win_user>\.ssh\<my-key-name>.pub`)


### Добавление контрибьютора в репозиторий:
1. владельцу репозитория открыть проект на GitHub
2. перейти в Settings этого репозитория (не путать с глобальными Settings всего аккаунта)
3. открыть вкладку `Access` -> `Collaborators`
4. в форме `Manage access` нажать кнопку `Add People` - Search by username, full name, or email. дать ему write access
5. на email приглашенного придет письмо - надо его одобрить
6. во вкладке `Deploy keys` -> `Add deploy key`
7. вставить публичный ключ, полученный от нового участника команды (содержимое его `C:\Users\<win_user>\.ssh\<my-key-name>.pub`)

### Настройка репозитория у контрибьютора
Только после двух предыдущих этапов новый член команды сможет вносить правки в чужой репо
1. склонировать репозиторий по ssh
```
git clone git@github.com:<repo-owner>/<repo name>.git
```
2. минимальная локальная настройка репо
```
git config user.name "Your Name Here"
git config user.email "your@email.example"
```
3. ... разработка ...
6. git push

