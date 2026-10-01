import { refreshAccessToken } from "./auth";

export async function apiFetch(url, options = {}) {

    const accessToken = localStorage.getItem("accessToken");

    const response = await fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            Authorization: `Bearer ${accessToken}`,
        },
    });

    if (response.status !== 401) {
        return response;
    }

    const refreshed = await refreshAccessToken();

    if (!refreshed) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        window.location.href = "/login";

        return response;
    }

    const newAccessToken = localStorage.getItem("accessToken");

    return fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            Authorization: `Bearer ${newAccessToken}`,
        },
    });
}