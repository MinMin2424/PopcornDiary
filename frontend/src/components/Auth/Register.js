import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthService from "../../services/auth.service";
import "../../css/Auth/Register.sass"

function Register() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [isSuccess, setIsSuccess] = useState(false);
    const navigate = useNavigate();

    const handleRegister = (e) => {
        e.preventDefault();

        AuthService.register(email, password)
            .then(
                () => {
                    setMessage('Registration successful! Please login.');
                    setIsSuccess(true);
                    setTimeout(() => navigate('/login'), 2000);
                },
                error => {
                    setMessage('Registration failed: ' + (error.response?.data?.message || 'Email already exists'));
                    setIsSuccess(false);
                }
            );
    };

    return (
        <div>
            <h2>REGISTER</h2>
            <form onSubmit={handleRegister}>
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

export default Register;