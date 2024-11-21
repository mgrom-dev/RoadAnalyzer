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

            data.sort((a, b) => new Date(b.date) - new Date(a.date));
            data.forEach(expense => expensesList.append(createOrUpdateRow(expense)));
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

function createOrUpdateRow(expense, existingRow = null) {
    const newRow = $(`
        <tr class="align-middle">
            <td>${formatDate(expense.date)}</td>
            <td>${expense.partDescription}${expense.description ? ` (${expense.description})` : ''}</td>
            <td>${formatNumber(expense.count, 3)}</td>
            <td>${formatCurrency(expense.amount)}</td>
        </tr>
    `);

    newRow.on('click', () => openEditModal(expense, newRow));

    if (existingRow) existingRow.replaceWith(newRow);

    return newRow;
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

    $(document).on('click', function (event) {
        const partDescriptionInput = $('#partDescription');
        const suggestionsContainer = $('#suggestions');
        if (!partDescriptionInput.is(event.target) && !suggestionsContainer.is(event.target) && !suggestionsContainer.has(event.target).length) {
            suggestionsContainer.hide(); // Скрываем подсказки
        }
    });
}

function openEditModal(expense, row) {
    $('#expenseId').val(expense.id);
    $('#partAndServiceId').val(expense.partAndServiceId);
    $('#partType').val(expense.partType);
    $('#partTypeDescription').val(expense.partTypeDescription);
    $('#expenseDate').val(formatDate(expense.date));
    $('#partDescription').val(expense.partDescription);
    $('#description').val(expense.description || '');
    $('#count').val(expense.count);
    $('#amount').val(expense.amount);

    $('#deleteExpenseBtn').off('click').on('click', () => deleteExpense(expense, row));
    $('#saveChangesBtn').off('click').on('click', () => saveExpense(expense, row));

    let types = [];
    let parts = [];

    $.ajax({
        url: `/api/expense_type`,
        type: 'GET',
        success: function (data) {
            types = data;
        }
    });

    $.ajax({
        url: `/api/part_and_service`,
        type: 'GET',
        success: function (data) {
            parts = data;
        }
    });

    $('#partDescription').on('input', function () {
        const inputValue = $(this).val().toLowerCase(); // Используем $(this) для доступа к полю ввода
        const suggestionsContainer = $('#suggestions');

        // Очищаем предыдущие подсказки
        suggestionsContainer.empty();

        // clear hidden input default
        $('#partType').val('');
        $('#partTypeDescription').val('');
        $('#partAndServiceId').val('');

        if (inputValue) {
            // Фильтруем массив данных
            const filteredData = parts.filter(item => item.description.toLowerCase().includes(inputValue));

            suggestionsContainer[0].style.display = filteredData.length > 0 ? 'block' : 'none';

            // Создаем и отображаем подсказки
            filteredData.forEach(item => {
                const suggestionItem = $('<div class="suggestion-item"></div>').text(item.description);

                // Обработчик клика по подсказке
                suggestionItem.on('click', function () {
                    const type = types.find(t => t.id == item.type);
                    $('#partAndServiceId').val(item.id);
                    $('#partDescription').val(item.description); // Устанавливаем значение поля ввода
                    $('#partType').val(type.id);
                    $('#partTypeDescription').val(type.description);
                    suggestionsContainer.empty(); // Очищаем подсказки
                    suggestionsContainer[0].style.display = 'none';
                });

                suggestionsContainer.append(suggestionItem);
            });
        } else {
            suggestionsContainer[0].style.display = 'none';
        }
    });

    $('#editExpenseModal').modal('show');
}

function deleteExpense(expense, row) {
    const partAndServiceId = expense.partAndServiceId;

    if (!partAndServiceId) {
        console.log("Не удалось удалить. Идентификатор расхода не найден.");
        return;
    }

    $.ajax({
        url: `/api/spending/${partAndServiceId}`,
        type: 'DELETE',
        success: function () {
            row.remove();
        },
        error: function (xhr) {
            console.log("Ошибка при удалении расхода: " + xhr.responseText);
        }
    }).always(function () {
        $('#editExpenseModal').modal('hide');
    });
}

function saveExpense(expense, row) {
    const partDescription = document.getElementById('partDescription').value;
    const count = document.getElementById('count').value;
    const amount = document.getElementById('amount').value;

    if (!partDescription || !count || !amount) {
        alert("Пожалуйста, заполните все обязательные поля.");
        return;
    }

    // Logic save changes
    const spending = {
        id: expense.id,
        date: formatDateToLocalDate($('#expenseDate').val()),
        partAndServiceId: parseInt($('#partAndServiceId').val()) || null,
        description: $('#description').val(),
        count: parseFloat(count) || 0,
        amount: parseFloat(amount) || 0,
        partDescription: partDescription,
        partType: parseInt($('#partType').val()) || null,
        partTypeDescription: $('#partTypeDescription').val()
    };

    $.ajax({
        url: `/api/spending`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(spending),
        success: function (data) {
            expense = spending;
            if ("id" in data) expense.id = data.id;
            createOrUpdateRow(expense, row);
        },
        error: function (data) {
            console.error("Error occurred while saving spending:", data.message);
        }
    });

    $('#editExpenseModal').modal('hide');
}