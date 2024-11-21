
function updateExpensesChart(expenses) {
    const monthlyExpenses = Array(12).fill(0); // Array for keep expenses by month

    expenses.forEach(expense => {
        const month = new Date(expense.date).getMonth(); // get month (0-11)
        monthlyExpenses[month] += expense.amount; // summ expenses by month
    });

    const data = {
        labels: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        datasets: [{
            label: 'Расходы на содержание автомобиля (₽)',
            data: monthlyExpenses,
            backgroundColor: 'rgba(75, 192, 192, 0.5)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
        }]
    };

    const config = {
        type: 'line',
        data: data,
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    title: { display: true, text: 'Сумма (₽)' }
                },
                x: {
                    title: { display: true, text: 'Месяцы' }
                }
            }
        }
    };

    // init chart
    new Chart(document.getElementById('expensesChart'), config);
}

function printTop3SpendingYear(expenses) {
    // groupBy partType
    const groupedSpendings = expenses.reduce((acc, spending) => {
        const { partType, partTypeDescription, amount } = spending;

        if (!acc[partType]) {
            acc[partType] = {
                partType,
                partTypeDescription,
                summ: 0
            };
        }

        acc[partType].summ += amount;
        return acc;
    }, {});

    // Sort array by desc summ
    const sortedSpendings = Object.values(groupedSpendings).sort((a, b) => b.summ - a.summ);

    // print top 3 spending
    const top3Spendings = $('#top3spendings');
    top3Spendings.html('');

    sortedSpendings.slice(0, 3).forEach(spending => {
        top3Spendings.append(`
                <div class="p-4 bg-neutral-200 rounded-md">
                    <h3 class="text-xl">${spending.partTypeDescription}</h3>
                    <p class="text-lg">${formatCurrency(spending.summ)}</p>
                </div>
            `);
    });
}

function loadOverviewData() {
    const currentYear = new Date().getFullYear();
    const startOfYear = `${currentYear}-01-01`;
    const endOfYear = `${currentYear}-12-31`;

    // get data spendings by current year
    fetchSpendingData(startOfYear, endOfYear)
        .then(data => {
            printTop3SpendingYear(data);
            updateExpensesChart(data);
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });

}