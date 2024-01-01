const notifications = document.querySelector(".notifications"),
    buttons = document.querySelectorAll(".buttons .btn")

const toastDetails = {
    timer: 5000,
    success: {
        icon: "fa-circle-check",
        text: "Hello World: This is a success toast.",
    },
    error: {
        icon: "fa-circle-xmark",
        text: "Hello World: This is an error toast.",
    },
    warning: {
        icon: "fa-triangle-exclamation",
        text: "Hello World: This is a warning toast.",
    },
    info: {
        icon: "fa-circle-info",
        text: "Hello World: This is an information toast.",
    },
    random: {
        icon: "fa-solid fa-star",
        text: "Hello World: This is a random toast.",
    },
}

const removeToast = (toast) => {
    toast.classList.add("hide")
    if (toast.timeoutId) clearTimeout(toast.timeoutId)
    setTimeout(() => toast.remove(), 500)
}

const createToast = (id, message) => {
    const { icon, text } = toastDetails[id]
    const toast = document.createElement("li")
    toast.className = `toast ${id}`
    toast.innerHTML = `<div class="column">
                         <i class="fa-solid ${icon}"></i>
                         <span>${message}</span>
                      </div>
                      <i class="fa-solid fa-xmark" onclick="removeToast(this.parentElement)"></i>`
    notifications.appendChild(toast)
    toast.timeoutId = setTimeout(() => removeToast(toast), toastDetails.timer)
}