import React from "react";
import ReactDOM from "react-dom/client";
import NewGameForm from "./components/pages/NewGamePage";
import { AuthProvider } from "./components/authentication/AuthProvider";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <NewGameForm />
    </AuthProvider>
  </React.StrictMode>
);
