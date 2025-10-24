import React from "react";
import ReactDOM from "react-dom/client";
import LoginForm from "./components/pages/LoginPage";
import { AuthProvider } from "./components/authentication/AuthProvider";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <LoginForm />
    </AuthProvider>
  </React.StrictMode>
);
