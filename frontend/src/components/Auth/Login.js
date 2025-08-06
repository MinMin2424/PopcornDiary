import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthService from "../../services/auth.service"

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();

        AuthService.login(email, password)
            .then(
                () => {
                    navigate('/');
                    window.location.reload();
                },
                error => {
                    setMessage('Login failed: ' + (error.response?.data?.message || 'Invalid credentials'));
                }
            );
    };

    return (
        <div>
            <h2>LOGIN</h2>
            <form onSubmit={handleLogin}>
                <label htmlFor='email'>EMAIL</label>
                <input
                    type='email'
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <label>PASSWORD</label>
                <input
                    type='password'
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">SUBMIT</button>

                {message && <div>{message}</div>}
            </form>
        </div>
    );
}

export default Login;