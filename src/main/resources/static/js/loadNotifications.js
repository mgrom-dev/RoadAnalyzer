document.addEventListener("DOMContentLoaded", function () {
    fetch('notifications')
        .then(response => response.text())
        .then(data => {
            document.getElementById('notifications-container').innerHTML = data;
        })
        .catch(error => console.error('Ошибка при загрузке уведомлений:', error));
});