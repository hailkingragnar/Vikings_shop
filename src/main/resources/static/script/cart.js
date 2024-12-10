document.addEventListener('DOMContentLoaded', () => {
    // Get all the quantity increase and decrease buttons
    const decreaseButtons = document.querySelectorAll('.decrease-quantity');
    const increaseButtons = document.querySelectorAll('.increase-quantity');
    const quantitySpans = document.querySelectorAll('.quantity-span');
    const totalElements = document.querySelectorAll('.total-element');
    const priceElements = document.querySelectorAll('.product-price'); // For product price elements

    // Loop through the decrease buttons and add event listeners
    decreaseButtons.forEach((button, index) => {
        button.addEventListener('click', () => {
            let quantity = parseInt(quantitySpans[index].textContent);
            if (quantity > 1) {
                quantity--; // Decrease the quantity
                quantitySpans[index].textContent = String(quantity);
                updateCartTotal();  // Recalculate totals after quantity change
            }
        });
    });

    // Loop through the increase buttons and add event listeners
    increaseButtons.forEach((button, index) => {
        button.addEventListener('click', () => {
            let quantity = parseInt(quantitySpans[index].textContent);
            quantity++; // Increase the quantity
            quantitySpans[index].textContent = String(quantity);
            updateCartTotal();  // Recalculate totals after quantity change
        });
    });

    // Function to update the total price for the cart
    function updateCartTotal() {
        let subtotal = 0;
        console.log("Inside UpdateCartTotal")
        // Loop through all total elements and calculate the new subtotal
        totalElements.forEach((totalElement, index) => {
            const price = parseFloat(priceElements[index].textContent.replace('$', ''));
            console.log("Inside TotalElement")
            const quantity = parseInt(quantitySpans[index].textContent);
            totalElement.textContent = `$${(price * quantity).toFixed(2)}`;
            subtotal += parseFloat(totalElement.textContent.replace('$', ''));
        });

        // Update the cart subtotal element
        const subtotalElement = document.querySelector('.cart-subtotal');
        subtotalElement.textContent = `$${subtotal.toFixed(2)}`;
        console.log("Inside subtotalElement");
        // Add shipping and update the total price
        const shipping = 10;  // Example shipping cost
        const totalPriceElement = document.querySelector('.total-price');
        totalPriceElement.textContent = `$${(subtotal + shipping).toFixed(2)}`;  // Update total with shipping
    }

    // Remove item button functionality
    document.querySelectorAll('.remove-item-btn').forEach(button => {
        button.addEventListener('click', function () {
            const cartItemId = this.getAttribute('data-cart-item-id');

                // Get the cart item ID
            removeProductFromCart(cartItemId);  // Call function to remove the item
        });
    });

    function removeProductFromCart(cartItemId) {
        // Make an AJAX request to the backend to remove the product from the cart
        fetch(`/cart/delete/${cartItemId}`, {
            method: 'DELETE',  // HTTP method to send the delete request
            headers: {
                'Content-Type': 'application/json',  // Content type
            },
        })
            .then(response => response.json())  // Assuming the server returns JSON data with updated cart info
            .then(updatedCartData => {
                if (updatedCartData.success) {
                    // If delete is successful, remove the item from the UI
                    const cartItemElement = document.querySelector(`.cart-item[data-cart-item-id="${cartItemId}"]`);
                    if (cartItemElement) {
                        cartItemElement.remove();  // Remove the item from the DOM
                    }

                    // After removal, update the totals based on the updated cart data
                    updateCartTotal();


                    // Optionally update the UI with the new cart data
                    const subtotalElement = document.querySelector('.cart-subtotal');
                    subtotalElement.textContent = `$${updatedCartData.subtotal.toFixed(2)}`;
                    const totalPriceElement = document.querySelector('.total-price');
                    totalPriceElement.textContent = `$${(updatedCartData.subtotal + updatedCartData.shipping).toFixed(2)}`;

                } else {
                    console.error("Failed to remove product from cart");
                }
            })
            .catch(error => {
                console.error("Error removing product from cart:", error);
            });
    }

    const checkoutButtons = document.querySelectorAll("#checkout-btn");

    // Add click event listeners
    checkoutButtons.forEach(button => {
        button.addEventListener("click", function () {
            // Get the cartId from the data attribute
            const cartId = this.getAttribute("data-cart-id");
            console.log("Checkout triggered for cartId:", cartId);

            // Call your checkout logic
            checkout(cartId);

            function checkout(cartId) {
                const cartItems = document.querySelectorAll(".cart-item");

                // Capture updated quantities and send them to the backend before checkout
                const updatedQuantities = Array.from(cartItems).map(item => {
                    return {
                        cartItemId: item.getAttribute("data-cart-item-id"),
                        quantity: parseInt(item.querySelector(".quantity-span").textContent)

                    };
                });

                console.log("The Updated quantities :" + updatedQuantities);
                // Send updated quantities to the backend
                sendUpdatedQuantities(updatedQuantities,cartId);
            }

            // Function to send updated quantities to the backend
            function sendUpdatedQuantities(updatedQuantities,cartId) {
                fetch(`/cart/updatequantities/${cartId}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(updatedQuantities),
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = `/cart/checkout`;  // Redirect to checkout page
                        } else {
                            alert("Failed to update quantities. Please try again.");
                        }
                    })
                    .catch(error => {
                        console.error("Error:", error);
                        alert("An error occurred while updating quantities. Please try again.");
                    });
            }
        });
    });
});

