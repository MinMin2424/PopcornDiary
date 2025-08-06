import logo from './logo.svg';
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import Layout from "./components/Layout";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register"
import AuthService from "./services/auth.service"
import './App.css';

function App() {
/*  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );*/

  const PrivateRoute = ({children}) => {
    const currentUser = AuthService.getCurrentUser();
    return currentUser ? children : <Navigate to="/login" />;
  };
  return (
      <Router>
        <Routes>
          <Route path="/" element={
            <PrivateRoute>
              <Layout />
            </PrivateRoute>
          } />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </Router>
  )
}

export default App;
