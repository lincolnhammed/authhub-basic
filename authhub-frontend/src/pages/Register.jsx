import {useState} from "react";
import {useNavigate} from "react-router-dom";

function Register() {
    const [nome, setNome] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");
        setSuccess("");
        const response = await fetch("http://localhost:8088/users", {
            method: "POST",
            headers: {"Content-Type": "application/json",},
            body: JSON.stringify({nome: nome, email: email, password: password,}),
        });
        if (!response.ok) {
            const data = await response.json();
            setError(data.message);
            return;
        }
        setSuccess("Conta criada com sucesso!");
        setTimeout(() => {
            navigate("/login");
        }, 1000);
    }

    return (<div className="register-container">
        <div className="register-card"><h1>Criar conta</h1> {error && (
            <p className="register-error"> {error} </p>)} {success && (<p className="success-message"> {success} </p>)}
            <form onSubmit={handleSubmit}>
                <div className="register-form-group">
                    <label>Nome</label>
                    <input type="text" value={nome}
                           onChange={(event) => setNome(event.target.value)}/>
                </div>
                <div className="register-form-group">
                    <label>Email</label>
                    <input type="email" value={email}
                           onChange={(event) => setEmail(event.target.value)}/>
                </div>
                <div className="register-form-group">
                    <label>Senha</label>
                    <input type="password" value={password}
                           onChange={(event) => setPassword(event.target.value)}/>
                </div>
                <button type="submit" className="register-button"> Criar conta</button>
            </form>
            <p className="register-link"> Já tem uma conta?{" "}
                <button type="button" onClick={() => navigate("/login")}> Entrar</button>
            </p>
        </div>
    </div>);
}
export default Register;