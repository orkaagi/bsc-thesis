import React from "react";
import ReactDOM from "react-dom/client";
import HelpBody from "./components/pages/HelpPage";
import { AuthProvider } from "./components/authentication/AuthProvider";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <HelpBody />
    </AuthProvider>
  </React.StrictMode>
);
