document.addEventListener("DOMContentLoaded", function () {
    fetch('actions')
        .then(response => response.text())
        .then(data => {
            document.getElementById('actions').innerHTML = data;
            $("#addExpense").on('click', function () {
                if ($('#editExpenseModal').length === 0) {
                    loadContent('expenses').then(text => { loadExpensesData(); addNewExpense(); });
                } else {
                    addNewExpense();
                }
            });
        })
        .catch(error => console.error('Ошибка при загрузке уведомлений:', error));
});

function addNewExpense() {
    const expense = {
        date: new Date().toISOString().split('T')[0],
        partDescription: '',
        description: '',
        count: 1,
        amount: 0
    };

    const newRow = $(`
        <tr class="align-middle">
            <td>${formatDate(expense.date)}</td>
            <td>${expense.partDescription}${expense.description ? ` (${expense.description})` : ''}</td>
            <td>${formatNumber(expense.count, 3)}</td>
            <td>${formatCurrency(expense.amount)}</td>
        </tr>
    `);

    $('#expensesList').prepend(newRow);

    openEditModal(expense, newRow);
}