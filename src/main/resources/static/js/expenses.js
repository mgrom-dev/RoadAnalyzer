function loadExpensesData() {
    bindActionsExpenses();
    const currentYear = new Date().getFullYear();
    const startOfYear = `${currentYear}-01-01`;
    const endOfYear = `${currentYear}-12-31`;

    // get data spendings by current year
    fetchSpendingData(startOfYear, endOfYear)
        .then(data => {
            const expensesList = $('#expensesList');
            expensesList.empty();

            data.forEach(expense => {
                const row = $(`
                    <tr class="align-middle">
                        <td>${formatDate(expense.date)}</td>
                        <td>${expense.partDescription}${expense.description ? ` (${expense.description})` : ''}</td>
                        <td>${formatNumber(expense.count, 3)}</td>
                        <td>${formatCurrency(expense.amount)}</td>
                    </tr>
                `);

                row.on('click', () => {
                    openEditModal(expense);
                });

                expensesList.append(row);
            });
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

function bindActionsExpenses() {
    $('#amount').on('keydown', function (event) {
        const allowedKeys = [
            'Backspace', 'Tab', 'Enter', 'Escape', 'ArrowLeft', 'ArrowRight', 'ArrowUp', 'ArrowDown',
            'Control', 'Shift', 'Meta' // Разрешаем модификаторы
        ];

        // Разрешаем специальные клавиши
        if (allowedKeys.includes(event.key)) {
            return;
        }

        // Проверяем, является ли введённый символ цифрой или разделителем
        const isNumber = /^[0-9]$/.test(event.key);
        const isDecimalSeparator = event.key === '.' || event.key === ','; // Точка или запятая

        if (!isNumber && !isDecimalSeparator) {
            event.preventDefault(); // Запрещаем ввод символов, кроме цифр и разделителей
        }
    });

    $('#amount').on('blur', function () {
        const value = $(this).val();
        // Преобразуем введенное значение в число с плавающей запятой
        const formattedValue = parseFloat(value.replace(',', '.'));
        $(this).val(isNaN(formattedValue) ? '' : formattedValue.toFixed(2));
    });

    $('#saveChangesBtn').on('click', function() {
        const partDescription = document.getElementById('partDescription').value;
        const count = document.getElementById('count').value;
        const amount = document.getElementById('amount').value;
    
        if (!partDescription || !count || !amount) {
            alert("Пожалуйста, заполните все обязательные поля.");
            return;
        }
    
        // Логика сохранения изменений
    });

    $('#deleteExpenseBtn').on('click', function() {
        const partAndServiceId = document.getElementById('partAndServiceId').value;
    
        if (!partAndServiceId) {
            alert("Не удалось удалить. Идентификатор расхода не найден.");
            return;
        }
    
        // Логика удаления расхода
        // Например, отправка запроса на сервер для удаления элемента
        console.log(`Удаление расхода с ID: ${partAndServiceId}`);
    });
}

function openEditModal(expense) {
    $('#partAndServiceId').val(expense.partAndServiceId);
    $('#expenseDate').val(formatDate(expense.date));
    $('#partDescription').val(expense.partDescription);
    $('#description').val(expense.description || '');
    $('#count').val(expense.count);
    $('#amount').val(expense.amount);

    // Открываем модальное окно
    $('#editExpenseModal').modal('show');
}