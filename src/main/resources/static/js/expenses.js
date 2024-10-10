function expensesLoad() {
    console.log("expenses load");
    const currentYear = new Date().getFullYear();
    const startOfYear = `${currentYear}-01-01`;
    const endOfYear = `${currentYear}-12-31`;

    // get data spendings by current year
    fetchSpendingData(startOfYear, endOfYear)
        .then(data => {
            const expensesList = document.getElementById('expensesList');

            data.forEach(expense => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${expense.date}</td>
                    <td>${expense.partDescription}</td>
                    <td>${expense.description ? expense.description : '—'}</td>
                    <td>${expense.count}</td>
                    <td>${expense.amount.toFixed(2)}</td>
                `;

                expensesList.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}
