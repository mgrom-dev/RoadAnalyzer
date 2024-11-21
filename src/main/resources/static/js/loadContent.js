function loadContent(page) {
    return new Promise((resolve, reject) => {
        const contentDiv = document.getElementById("content");
        const xhr = new XMLHttpRequest();

        xhr.open("GET", page, true); // true for async call

        xhr.onload = function () {
            if (xhr.status === 200) {
                contentDiv.innerHTML = xhr.responseText; // load response to content block
                resolve(xhr.responseText); // then promiss with response text
            } else {
                const errorMessage = `Ошибка загрузки: ${xhr.statusText}`;
                console.error(errorMessage);
                contentDiv.innerHTML = `<p>${errorMessage}</p>`;
                reject(new Error(errorMessage));
            }
        };

        xhr.onerror = function () {
            const networkErrorMessage = 'Ошибка сети';
            console.error(networkErrorMessage);
            reject(new Error(networkErrorMessage));
        };

        xhr.send();
    });
}

// content Tabs
$("#overviewTab").click(e => loadContent('overview').then(text => loadOverviewData()));
$("#expensesTab").click(e => loadContent('expenses').then(text => loadExpensesData()));

// load the default block
$("#overviewTab").click();