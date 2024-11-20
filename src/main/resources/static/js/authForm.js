function toggleAuthForm() {
    const authForm = document.getElementById("authForm");
    if (authForm.style.display === "none" || authForm.style.display === "") {
        authForm.style.display = "block";
    } else {
        authForm.style.display = "none";
    }
}

function toggleSettingsForm() {
    const settForm = document.getElementById("settingsForm");
    if (settForm.style.display === "none" || settForm.style.display === "") {
        settForm.style.display = "block";
    } else {
        settForm.style.display = "none";
    }
}

document.addEventListener("click", function (event) {
    const authForm = document.getElementById("authForm");
    const settForm = document.getElementById("settingsForm");
    const targetElement = event.target;

    // check click out of form and buttons
    if (!authForm.contains(targetElement)) {
        authForm.style.display = "none";
    }
    if (!settForm.contains(targetElement)) {
        settForm.style.display = "none";
    }
});


$("#showAuthForm").click(function (event) {
    toggleAuthForm();
    // stop events DOM
    event.stopPropagation();
});

$("#showSettingsButton").click(function (event) {
    toggleSettingsForm();
    event.stopPropagation();
});

$('#loginForm').on('submit', function (event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    $.post('/auth', $(this).serialize())
        .done(function (data) {
            if (data.redirect) {
                window.location.href = data.redirect;
            }
        })
        .fail(function (data) {
            $('#message').css("color", "red");
            $('#message').text(data?.responseJSON?.message || 'Ошибка при авторизации.');
        });
});

$('#registrationForm').on('submit', function (event) {
    event.preventDefault();

    var formData = $(this).serialize();

    $.post('/auth', formData)
        .done(function (data) {
            if (data.redirect) {
                window.location.href = data.redirect;
            } else {
                $('#registrationForm button').hide();
                $('#confirmationCodeContainer').show();
                $('#message').css("color", "blue");
                $('#message').text('Код отправлен на вашу почту.');
            }
        })
        .fail(function (data) {
            $('#message').text(data?.responseJSON?.message || 'Ошибка при отправке данных.');
        });
});

$('#confirmButton').on('click', function () {
    var code = $('#confirmationCode').val();

    $.post('/verify', { confirmationCode: code })
        .done(function (data) {
            $('#message').text('Код подтвержден успешно!');
        })
        .fail(function (data) {
            $('#message').text(data?.responseJSON?.message || 'Ошибка при подтверждении кода.');
        });
});