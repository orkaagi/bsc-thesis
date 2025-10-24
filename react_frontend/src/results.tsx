import React from "react";
import ReactDOM from "react-dom/client";
import ProfileData from "./components/pages/ResultsPage";
import { AuthProvider } from "./components/authentication/AuthProvider";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <AuthProvider>
      <ProfileData />
    </AuthProvider>
  </React.StrictMode>
);
