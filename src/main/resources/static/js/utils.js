async function fetchExpenses() {
    const response = await fetch('/api/spending');
    const expenses = await response.json();
    return expenses;
}

function fetchExpensesSync() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/spending', false); // Установите false для синхронного запроса
    xhr.send();

    if (xhr.status === 200) {
        return JSON.parse(xhr.responseText); // Возвращаем распарсенные данные
    } else {
        console.error('Ошибка загрузки данных:', xhr.statusText);
        return []; // Возвращаем пустой массив в случае ошибки
    }
}