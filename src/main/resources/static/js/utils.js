function fetchSpendingData(startDate, endDate) {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', `api/spending/full?createdAtAfter=${startDate}&createdAtBefore=${endDate}`, true); // true для асинхронного вызова

        xhr.onload = function () {
            if (xhr.status === 200) {
                resolve(JSON.parse(xhr.responseText));
            } else {
                console.error('Ошибка загрузки данных:', xhr.statusText);
                reject(new Error(`Ошибка: ${xhr.statusText}`)); // отклоняем промис при ошибке
            }
        };

        xhr.onerror = function () {
            console.error('Ошибка сети');
            reject(new Error('Ошибка сети')); // отклоняем промис при сетевой ошибке
        };

        xhr.send();
    });
}

function formatCurrency(amount) {
    return amount.toLocaleString('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}