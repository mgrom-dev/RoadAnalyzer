function toggleAuthForm() {
    const authForm = document.getElementById("authForm");
    if (authForm.style.display === "none" || authForm.style.display === "") {
        authForm.style.display = "block";
    } else {
        authForm.style.display = "none";
    }
}

document.addEventListener("click", function (event) {
    const authForm = document.getElementById("authForm");
    const targetElement = event.target;

    // check click out of form and buttons
    if (!authForm.contains(targetElement)) {
        authForm.style.display = "none";
    }
});

document.getElementById("showAuthForm").addEventListener("click", function (event) {
    toggleAuthForm();
    // stop events DOM
    event.stopPropagation();
});