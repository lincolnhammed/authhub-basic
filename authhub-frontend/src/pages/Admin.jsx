import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../services/api";

function Admin() {

    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    useEffect(() => {

        async function loadAdmin() {

            const response = await apiFetch(
                "http://localhost:8088/admin"
            );

            if (response.status === 403) {
                navigate("/");
                return;
            }

            const data = await response.text();

            setMessage(data);
        }

        loadAdmin();

    }, [navigate]);

    return (
        <div>
            <h1>Painel Administrativo</h1>

            <p>{message}</p>
        </div>
    );
}

export default Admin;

