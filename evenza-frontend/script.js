const EVENT_API = "http://localhost:8081/api/events";
const REGISTRATION_API = "http://localhost:8082/api/registrations";
const PAYMENT_API = "http://localhost:8083/api/payments";
const NOTIFICATION_API = "http://localhost:8084/api/notifications";
const USER_API = "http://localhost:8081/api/users";


// Load events on home page
function loadEvents() {
    fetch(EVENT_API)
        .then(response => response.json())
        .then(events => {
            const list = document.getElementById("eventList");
            list.innerHTML = "";

            events.forEach(event => {
                list.innerHTML += `
                    <div class="event-card">
                        <h3>${event.title}</h3>
                        <p><strong>Description:</strong> ${event.description}</p>
                        <p><strong>Event ID:</strong> ${event.id}</p>
                        <p><strong>Date:</strong> ${event.date}</p>
                        <p><strong>Venue:</strong> ${event.venue}</p>
                        <p><strong>Capacity:</strong> ${event.capacity}</p>
                    </div>
                `;
            });
        })
        .catch(error => console.log("Error loading events:", error));
}

// Load events on admin page
function loadAdminEvents() {
    fetch(EVENT_API)
        .then(response => response.json())
        .then(events => {
            const list = document.getElementById("adminEventList");
            list.innerHTML = "";

            events.forEach(event => {
                list.innerHTML += `
                    <div class="event-card">
                        <h3>${event.title}</h3>
                        <p>${event.description}</p>
                        <p><strong>Date:</strong> ${event.date}</p>
                        <p><strong>Venue:</strong> ${event.venue}</p>
                        <p><strong>Capacity:</strong> ${event.capacity}</p>
                    </div>
                `;
            });
        })
        .catch(error => console.log("Error loading events:", error));
}

// Create new event (Admin page)
function createEvent() {
    const event = {
        title: document.getElementById("title").value,
        description: document.getElementById("description").value,
        date: document.getElementById("date").value,
        venue: document.getElementById("venue").value,
        capacity: document.getElementById("capacity").value,
         price: document.getElementById("price").value  
    };

    fetch(EVENT_API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(event)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("message").innerText = "Event created successfully!";
        document.getElementById("eventForm").reset();
        loadAdminEvents();
    })
    .catch(error => {
        document.getElementById("message").innerText = "Error creating event.";
        console.log(error);
    });
}

// Register for an event (Register page)
function registerForEvent() {
    const registration = {
        userId: document.getElementById("userId").value,
        eventId: document.getElementById("eventId").value,
        status: "CONFIRMED"
    };
    const userEmail = document.getElementById("userEmail").value;

    fetch(REGISTRATION_API + "?email=" + userEmail, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(registration)
    })
    .then(response => response.json())
    .then(data => {
       document.getElementById("message").innerText = "Registered successfully!";
       window.currentEventId = document.getElementById("eventId").value;
       window.currentRegistrationId = data.id;
       document.getElementById("payNowBtn").style.display = "block";
       document.getElementById("payNowBtn").style.background = "#2e7d32";
       document.getElementById("registerForm").reset();
    })
    .catch(error => {
        document.getElementById("message").innerText = "Error registering.";
        console.log(error);
    });

}
function payNow() {
    const eventId = window.currentEventId;

    fetch(EVENT_API + "/" + eventId)
        .then(response => response.json())
        .then(event => {
            const amount = event.price;

            fetch(PAYMENT_API + "/create-order", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    amount: amount,
                    registrationId: window.currentRegistrationId
                })
            })
            .then(response => response.json())
            .then(order => {
                const options = {
                    key: "rzp_test_T6ZjLtgAvir8y4",
                    amount: order.amount,
                    currency: order.currency,
                    name: "Evenza",
                    description: "Payment for Event ID: " + eventId,
                    order_id: order.orderId,
                    handler: function (response) {
                        verifyPayment(response);
                    },
                    theme: { color: "#4b2e83" }
                };

                const rzp = new Razorpay(options);
                rzp.open();
            })
            .catch(error => {
                document.getElementById("message").innerText = "Error creating order.";
                console.log(error);
            });
        })
        .catch(error => {
            document.getElementById("message").innerText = "Error fetching event price.";
            console.log(error);
        });
    }

// Login function
function loginUser() {
    const credentials = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    fetch(USER_API + "/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credentials)
    })

 .then(response => response.json())
    .then(data => {
        if (data && data.id) {
            document.getElementById("message").innerText = "Login successful! Welcome " + data.name;
            localStorage.setItem("user", JSON.stringify(data));
        } else {
            document.getElementById("message").innerText = "Invalid email or password.";
        }
    })
       .catch(error => {
        document.getElementById("message").innerText = "Error logging in.";
        console.log(error);
    });
}

// Signup function
function signupUser() {
    const newUser = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        role: "user"
    };

    fetch(USER_API + "/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newUser)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("message").innerText = "Account created successfully! Please login.";
        document.getElementById("signupForm").reset();
    })
    .catch(error => {
        document.getElementById("message").innerText = "Error creating account.";
        console.log(error);
    });
}

// Search function
function searchEvents() {
    const title = document.getElementById("searchInput").value;
    
    fetch(EVENT_API + "/search?title=" + title)
        .then(response => response.json())
        .then(events => {
            const list = document.getElementById("eventList");
            list.innerHTML = "";

                   events.forEach(event => {
                list.innerHTML += `
                    <div class="event-card">
                        <h3>${event.title}</h3>
                        <p>${event.description}</p>
                        <p><strong>Date:</strong> ${event.date}</p>
                        <p><strong>Venue:</strong> ${event.venue}</p>
                        <p><strong>Capacity:</strong> ${event.capacity}</p>
                    </div>
                `;
            });

                        if (events.length === 0) {
                list.innerHTML = "<p>No events found.</p>";
            }
        })
        .catch(error => console.log("Error searching events:", error));
}


function loadDashboard() {
    fetch(EVENT_API + "/count")
        .then(res => res.json())
        .then(count => {
            document.getElementById("eventCount").innerText = count;
        });

    fetch(REGISTRATION_API + "/count")
        .then(res => res.json())
        .then(count => {
            document.getElementById("registrationCount").innerText = count;
        });

    fetch(PAYMENT_API + "/count")
        .then(res => res.json())
        .then(count => {
            document.getElementById("paymentCount").innerText = count;
        });

    fetch(NOTIFICATION_API + "/count")
        .then(res => res.json())
        .then(count => {
            document.getElementById("notificationCount").innerText = count;


            new Chart(document.getElementById("statsChart"), {
                type: 'bar',
                data: {
                    labels: ['Events', 'Registrations', 'Payments', 'Notifications'],
                    datasets: [{
                        label: 'Count',
                        data: [
                            document.getElementById("eventCount").innerText,
                            document.getElementById("registrationCount").innerText,
                            document.getElementById("paymentCount").innerText,
                            document.getElementById("notificationCount").innerText
                        ],
                        backgroundColor: ['#4b2e83', '#6a3fb5', '#8b5fd6', '#a87ee8']
                    }]
                }
            });
        });
}