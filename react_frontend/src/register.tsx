import React from "react";
import ReactDOM from "react-dom/client";
import RegistrationForm from "./components/pages/RegistrationPage";
import { AuthProvider } from "./components/authentication/AuthProvider";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      {" "}
      <RegistrationForm />
    </AuthProvider>
  </React.StrictMode>
);
