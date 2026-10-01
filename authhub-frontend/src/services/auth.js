export async function refreshAccessToken() {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        return false;
    }

    const response = await fetch("http://localhost:8088/auth/refresh", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            refreshToken: refreshToken,
        }),
    });

    if (!response.ok) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        return false;
    }

    const data = await response.json();

    localStorage.setItem("accessToken", data.accessToken);
    localStorage.setItem("refreshToken", data.refreshToken);

    return true;
}

export async function logout() {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        return;
    }

    await fetch("http://localhost:8088/logout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            refreshToken: refreshToken,
        }),
    });

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
}