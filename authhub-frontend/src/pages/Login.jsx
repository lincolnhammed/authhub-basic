import {useState} from "react";
import {useNavigate} from "react-router-dom";
import "../App.css";

function Login() {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    async function handleSubmit(event) {
        event.preventDefault();

        setError("");

        const response = await fetch("http://localhost:8088/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        if (!response.ok) {
            const message = await response.text();

            setError(message);

            return;
        }

        const data = await response.json();

        localStorage.setItem("accessToken", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);

        navigate("/");

        console.log(data);
    }

    async function acessarAreaUser() {
        const token = localStorage.getItem("accessToken");

        const response = await fetch("http://localhost:8088/user", {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        const data = await response.text();

        console.log(data);
    }

    return (
        <div className="login-container">
            <div className="login-card">

                <h1>AuthHub</h1>

                <p>Entre na sua conta</p>
                {error && (
                    <p className="error-message">
                        {error}
                    </p>
                )}
                <form onSubmit={handleSubmit}>

                    <div className="form-group">
                        <label>Email</label>

                        <input
                            type="email"
                            value={email}
                            onChange={(event) => setEmail(event.target.value)}
                            placeholder="seu@email.com"
                        />
                    </div>

                    <div className="form-group">
                        <label>Senha</label>

                        <input
                            type="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            placeholder="Sua senha"
                        />
                    </div>

                    <button
                        type="submit"
                        className="login-button"
                    >
                        Entrar
                    </button>

                </form>
                <p className="register-link">
                    Ainda não tem uma conta?{" "}
                    <button
                        type="button"
                        onClick={() => navigate("/register")}
                    >
                        Criar conta
                    </button>
                </p>
            </div>
        </div>
    );
}

export default Login;