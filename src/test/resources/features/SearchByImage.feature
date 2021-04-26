# language: ru
Функция: Поиск по картинке
  Поиск по картинке среди результатов должен выдать текстовое описание того, что на ней изображено

  Структура сценария: Загрузка изображения и проверка результатов выдачи

    Дано Открыть главную Яндекса
    И Перейти в блок Картинки
    И Нажать на поиск по картинке
    Когда Загрузить картинку с предусловия "<image>"
    Тогда Убедиться, что в блоке Кажется, на картинке есть в одном из буллитов отобразилось слово "<text>"
    Примеры:
      | image         | text     |
      | truckcrane.jpg | автокран |