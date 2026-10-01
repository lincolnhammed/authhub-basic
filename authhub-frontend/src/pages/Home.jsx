
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../services/api";
import { logout } from "../services/auth";

function Home() {
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        async function loadUserArea() {
            const response = await apiFetch(
                "http://localhost:8088/user"
            );

            const data = await response.text();

            setMessage(data);
        }

        loadUserArea();
    }, []);

    async function handleLogout() {
        await logout();

        navigate("/login");
    }

    return (
        <div>
            <h1>AuthHub</h1>

            <h2>Área protegida</h2>

            <p>{message}</p>

            <button onClick={handleLogout}>
                Sair
            </button>
        </div>
    );
}

export default Home;

