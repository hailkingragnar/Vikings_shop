function previewImage(event) {
    const preview = document.getElementById("preview");
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
            preview.classList.remove("hidden");
        };
        reader.readAsDataURL(file);
    }
}

function previewImageForProfile(event) {
    const reader = new FileReader();
    reader.onload = function () {
        const preview = document.getElementById('image-preview');
        console.log(preview.src);
        preview.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}

document.addEventListener("DOMContentLoaded", () => {
    const editButton = document.getElementById("editButton");
    const saveChangesContainer = document.getElementById("saveChangesContainer");
    const formInputs = document.querySelectorAll("input:not([type='file']):not([type='email'])");

    // Enable form fields and toggle buttons
    editButton.addEventListener("click", () => {
        // Enable all form inputs
        formInputs.forEach(input => input.disabled = false);

        // Show Save Changes button and hide Edit Profile button
        saveChangesContainer.classList.remove("hidden");
        editButton.style.display = "none";
    });

    // On form submit, save changes and toggle buttons
    saveChangesContainer.addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent actual form submission

        // Disable all form inputs
        formInputs.forEach(input => input.disabled = true);

        // Hide Save Changes button and show Edit Profile button
        saveChangesContainer.classList.add("hidden");
        editButton.style.display = "inline-flex";
    });
});

    function addToCart(button) {

        let productId = button.getAttribute('data-product-id');
        fetch('/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'productId=' + encodeURIComponent(productId),
        })
            .then(response => response.text())
            .then(data => {
                alert(data); // Show the response (e.g., "Item added to cart!")
            })
            .catch(error => console.error('Error:', error));
    }