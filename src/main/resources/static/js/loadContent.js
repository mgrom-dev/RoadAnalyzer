function loadContent(page) {
    const contentDiv = document.getElementById("content");

    // Создаем новый XMLHttpRequest
    const xhr = new XMLHttpRequest();

    // Определяем, что делать при успешной загрузке
    xhr.onload = function () {
        if (xhr.status === 200) {
            contentDiv.innerHTML = xhr.responseText; // Загружаем ответ в блок content

            // Найти все теги <script> в загруженном содержимом
            const scripts = contentDiv.getElementsByTagName('script');
            for (let i = 0; i < scripts.length; i++) {
                const newScript = document.createElement('script');
                newScript.text = scripts[i].innerHTML; // Копируем текст скрипта
                document.body.appendChild(newScript); // Добавляем скрипт в тело документа
            }
        } else {
            contentDiv.innerHTML = `<p>Ошибка загрузки: ${xhr.statusText}</p>`;
        }
    };

    // Открываем GET-запрос к нужной странице
    xhr.open("GET", page);

    // Отправляем запрос
    xhr.send();
}

// Загрузка страницы по умолчанию при первой загрузке
document.addEventListener("DOMContentLoaded", function () {
    loadContent('overview'); // Загружаем "Обзор расходов" по умолчанию
});