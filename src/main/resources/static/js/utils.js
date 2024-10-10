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

function fetchSpendingData(startDate, endDate) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `api/spending/full?createdAtAfter=${startDate}&createdAtBefore=${endDate}`, false);
    xhr.send();

    if (xhr.status === 200) {
        return JSON.parse(xhr.responseText);
    } else {
        console.error('Ошибка загрузки данных:', xhr.statusText);
        return []; // return empty array
    }
}

function calculateSumByPartType(spendings, partType) {
    return spendings
        .filter(spending => spending.partType === partType)
        .reduce((acc, spending) => acc + spending.amount, 0);
}

function formatCurrency(amount) {
    return amount.toLocaleString('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}