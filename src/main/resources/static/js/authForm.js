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

    $.ajax({
        url: '/auth',
        method: 'POST',
        data: $(this).serialize(),
        success: function (response) {
            window.location.href = "/";
        },
        error: function (xhr) {
            const errorResponse = JSON.parse(xhr.responseText);
            $('#message').text(errorResponse.error);
            $('#message').css("color", "red");
        }
    });
});