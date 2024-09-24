document.addEventListener("DOMContentLoaded", function () {
    fetch('actions')
        .then(response => response.text())
        .then(data => {
            document.getElementById('actions').innerHTML = data;
        })
        .catch(error => console.error('Ошибка при загрузке уведомлений:', error));
});